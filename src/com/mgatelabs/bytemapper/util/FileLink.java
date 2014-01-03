/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mgatelabs.bytemapper.util;

import com.mgatelabs.bytemapper.support.io.streams.BoundedInputStream;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 *
 * @author MiniMegaton
 */
public class FileLink {
    private File sourceFile;
    private byte [] sourceBytes;
    private long offset, length;
    private boolean linked;
    private boolean compressed;
    
    public FileLink() {
        linked = false;
        sourceFile = null;
        compressed = false;
        sourceBytes = new byte [0];
        offset = 0;
        length = 0;
    }
    
    public FileLink linkToFile(File target) {
        sourceFile = target;
        sourceBytes = new byte [0];
        offset = 0;
        length = target.length();
        linked = true;
        this.compressed = false;
        return this;
    }
    
    public FileLink linkToFile(File target, long offset, long length, boolean compressed) {
        sourceFile = target;
        sourceBytes = new byte [0];
        this.offset = offset;
        this.length = length;
        this.compressed = compressed;
        linked = true;
        return this;
    }
    
    public FileLink linkToBlob(byte [] bytes) {
        sourceFile = null;
        linked = false;
        sourceBytes = bytes;
        offset = 0;
        length = bytes.length;
        this.compressed = false;
        return this;
    }

    public boolean isCompressed() {
        return compressed;
    }

    public InputStream getInputStream() throws Exception {
        if (linked) {
            InputStream is = new BoundedInputStream(new BufferedInputStream(new FileInputStream(sourceFile)), offset, length);
            if (compressed) {
                is = new GZIPInputStream(is);
            }
            return is;
        } else {
            return new ByteArrayInputStream(sourceBytes);
        }
    }
    
    public byte [] getBytes() throws Exception {
        if (linked) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream((int)getLength());
            InputStream is = getInputStream();
            int count;
            byte [] bytes = new byte[1024];
            while ((count = is.read(bytes)) > 0) {
                baos.write(bytes, 0, count);
            }
            return baos.toByteArray();
        } else {
            return sourceBytes;
        }
    }
    
    public byte [] getCompressedBytes() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream((int)getLength());
        GZIPOutputStream zip = new GZIPOutputStream(baos);
        InputStream is = getInputStream();
        int count;
        byte [] bytes = new byte[1024];
        while ((count = is.read(bytes)) > 0) {
            zip.write(bytes, 0, count);
        }
        zip.flush();
        zip.close();
        return baos.toByteArray();
    }
    
    public File getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(File sourceFile) {
        this.sourceFile = sourceFile;
    }

    public byte[] getSourceBytes() {
        return sourceBytes;
    }

    public void setSourceBytes(byte[] sourceBytes) {
        this.sourceBytes = sourceBytes;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public boolean isLinked() {
        return linked;
    }

    public void setLinked(boolean linked) {
        this.linked = linked;
    }
}

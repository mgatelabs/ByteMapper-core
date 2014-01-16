/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mgatelabs.bytemapper.support.io.streams;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author MiniMegaton
 */
public class LimitedInputStream extends BufferedInputStream {

    private long byteLimit, bytesRead, offset;
    private InputStream is;
    private boolean fileAccess;

    public LimitedInputStream(InputStream in, long offset, long limit) {
        this(in, offset, limit, true);
    }

    public LimitedInputStream(InputStream in, long offset, long limit, boolean fileAccess) {
        super(in);
        is = in;
        byteLimit = limit;
        bytesRead = 0;
        this.offset = offset;
        this.fileAccess = fileAccess;
    }

    public LimitedInputStream spawn(long limit) throws IOException {
        sanityCheck(limit);
        return new LimitedInputStream(this, getPosition(), limit, isFileAccess());
    }

    public void forward() throws IOException {
        if (bytesRead < byteLimit) {
            this.skip(byteLimit - bytesRead);
        }
    }

    private void sanity() throws IOException {
        if (bytesRead > byteLimit) {
            throw new IOException("Attempted to read past byte limit!");
        }
    }

    private void sanityCheck(long limit) throws IOException {
        if ((bytesRead + limit) > byteLimit) {
            throw new IOException("Attempted to read past byte limit!");
        }
    }

    @Override
    public synchronized long skip(long l) throws IOException {
        bytesRead += l;
        sanity();
        return is.skip(l); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int read(byte[] bytes) throws IOException {
        int byteCount = is.read(bytes);
        bytesRead += byteCount;
        sanity();
        return byteCount; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public synchronized int read(byte[] bytes, int i, int i1) throws IOException {
        int byteCount = is.read(bytes, i, i1);
        bytesRead += byteCount;
        sanity();
        return byteCount; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public synchronized int read() throws IOException {
        int value = is.read();
        if (value == -1) {
            throw new IOException("Ran out of stream");
        }
        bytesRead += 1;
        sanity();
        return value; //To change body of generated methods, choose Tools | Templates.
    }

    public long getOffset() {
        return offset;
    }

    public long getPosition() {
        return offset + bytesRead;
    }

    public boolean isFileAccess() {
        return fileAccess;
    }

    public LimitedInputStream setFileAccess(boolean fileAccess) {
        this.fileAccess = fileAccess;
        return this;
    }
}

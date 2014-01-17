/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mgatelabs.bytemapper.support.io.streams;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author MiniMegaton
 */
public class BoundedInputStream extends FilterInputStream {

    private long offset;
    private long position;
    private long length;

    public BoundedInputStream(InputStream is, long offset, long length) {
        super(is);
        try {
            this.skip(offset);
        } catch (IOException ex) {
            Logger.getLogger(BoundedInputStream.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.offset = offset;
        this.length = length;
        position = 0;
    }

    @Override
    public synchronized int read() throws IOException {
        if (position >= length) {
            return -1;
        }
        position++;
        return super.read();
    }

    @Override
    public int read(byte[] bytes) throws IOException {
        int read = 0;
        if (position > length) {
            read = 0;
        } else if (position + bytes.length > length) {
            read = super.read(bytes, 0, (int) (length - position));
        } else {
            read = super.read(bytes);
        }
        if (read > 0) {
            position += read;
            return read;
        }
        return -1;
    }

    @Override
    public synchronized int read(byte[] bytes, int i, int i1) throws IOException {
        int read;
        if (position > length) {
            read = -1;
        } else if (position + i1 > length) {
            read = super.read(bytes, i, (int) (length - position));
        } else {
            read = super.read(bytes, i, i1);
        }
        return read;
    }
}

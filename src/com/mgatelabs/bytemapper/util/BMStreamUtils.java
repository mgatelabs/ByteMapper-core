/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mgatelabs.bytemapper.util;

import com.mgatelabs.bytemapper.support.instances.IdentityInstance;
import com.mgatelabs.bytemapper.support.io.streams.LimitedInputStream;

import java.io.OutputStream;

/**
 *
 * @author MiniMegaton
 */
public abstract class BMStreamUtils {

    // Identity
    // [CUSTOM][SHRINK][NULL][*][#][#][#][#]

    public static int writeNullableIdentity(OutputStream bo) throws Exception {
        bo.write(0x20);
        return 1;
    }
    
    public static int writeNullableIdentity(OutputStream bo, IdentityInstance identity, boolean isNull) throws Exception {
        if (isNull) {
            bo.write(0x20);
            return 1;
        } else {
            byte[] bytes = BMStreamUtils.getIntegerBytes(identity.getIdentity());
            int byteCount = BMStreamUtils.getByteCount(bytes, 0);
            int customBit = identity.isCustom() ? 0 : 0x80;
            if (0x00 <= identity.getIdentity() && identity.getIdentity() <= 0x3F) {
                bo.write(((identity.getIdentity() & 0x3F) | customBit) | 0x40);
                return 1;
            } else {
                bo.write(((byteCount & 0x0F) | customBit));
                if (byteCount > 0) {
                    bo.write(bytes, bytes.length - byteCount, byteCount);
                }
                return 1 + byteCount;
            }
        }
    }
    
    public static IdentityInstance readNullableIdentity(LimitedInputStream lir) throws Exception {  
        final int metaData = lir.read();
        if ((metaData) == 0x20) {
            return null;
        } else {
            final boolean isCustom = !((metaData & 0x80) == 0x80);
            final boolean isShrunk = ((metaData & 0x40) == 0x40);
            if (isShrunk) {
                return new IdentityInstance((metaData & 0x3F), isCustom);
            }
            final int contentBytes = (metaData & 0x0F);
            int value = 0, index;
            for (index = 0; index < contentBytes; index++) {
                value = (value << 8);
                value = value | (lir.read() & 0xFF);
            }
            return new IdentityInstance(value, isCustom);
        }
    }

    // Nullable Size

    // [SHRINK][NULL][*][*][#][#][#][#]

    public static int writeNullableSize(OutputStream bo) throws Exception {
        bo.write(0x40);
        return 1;
    }
    
    public static int writeNullableSize(OutputStream bo, int contentSize, boolean isNull) throws Exception {
        if (isNull) {
            bo.write(0x40);
            return 1;
        } else if (0x00 <= contentSize && contentSize <= 0x7F){
            bo.write(((contentSize & 0x7F)) | 0x80);
            return 1;
        } else {
            byte[] bytes = BMStreamUtils.getIntegerBytes(contentSize);
            int byteCount = BMStreamUtils.getByteCount(bytes, 0);
            bo.write(byteCount & 0x0F);
            if (byteCount > 0) {
                bo.write(bytes, bytes.length - byteCount, byteCount);
            }
            return 1 + byteCount;
        }
    }
    
    public static int readNullableSize(LimitedInputStream lir) throws Exception {  
        int metaData = lir.read();
        if ((metaData) == 0x40) {
            return -1;
        } else if ((metaData & 0x80) == 0x80) {
            return ((metaData & 0x7F) & 0xFF);
        } else {
            int contentBytes = (metaData & 0x0F), value = 0, index;
            for (index = 0; index < contentBytes; index++) {
                value = (value << 8);
                value = value | (lir.read() & 0xFF);
            }
            return value;
        }
    }

    // Non-nullable size
    // [SHRINK][*][*][*][#][#][#][#]

    public static int writeSize(OutputStream bo, int contentSize) throws Exception {
        if (0x00 <= contentSize && contentSize <= 0x7F){
            bo.write(((contentSize & 0x7F)) | 0x80);
            return 1;
        } else {
            byte[] bytes = BMStreamUtils.getIntegerBytes(contentSize);
            int byteCount = BMStreamUtils.getByteCount(bytes, 0);
            bo.write(byteCount & 0x0F);
            if (byteCount > 0) {
                bo.write(bytes, bytes.length - byteCount, byteCount);
            }
            return 1 + byteCount;
        }
    }
    
    public static int readSize(LimitedInputStream lir) throws Exception {  
        int metaData = lir.read();
        if ((metaData & 0x80) == 0x80) {
            return ((metaData & 0x7F) & 0xFF);
        } else {
            int contentBytes = (metaData & 0x0F), value = 0, index;
            for (index = 0; index < contentBytes; index++) {
                value = (value << 8);
                value = value | (lir.read() & 0xFF);
            }
            return value;
        }
    }

    // Non-Nullable Version #
    // [SHRINK][*][*][*][#][#][#][#]

    public static int writeVersion(OutputStream bo, int version) throws Exception {
        if (0x00 <= version && version <= 0x7F){
            bo.write((((version & 0x7F)) | 0x80) & 0xFF);
            return 1;
        } else {
            byte[] bytes = BMStreamUtils.getIntegerBytes(version);
            int byteCount = BMStreamUtils.getByteCount(bytes, 0);
            bo.write(byteCount & 0x0F);
            if (byteCount > 0) {
                bo.write(bytes, bytes.length - byteCount, byteCount);
            }
            return 1 + byteCount;
        }
    }
    
    public static int readVersion(LimitedInputStream lir) throws Exception {  
        int metaData = lir.read();
        if ((metaData & 0x80) == 0x80) {
            return ((metaData & 0x7F) & 0xFF);
        } else {
            int contentBytes = (metaData & 0x0F), value = 0, index;
            for (index = 0; index < contentBytes; index++) {
                value = (value << 8);
                value = value | (lir.read() & 0xFF);
            }
            return value;
        }
    }

    // Utility methods

    public static byte[] getIntegerBytes(int v) {
        byte[] bytes = new byte[4];
        for (int i = 3; i >= 0; i--) {
            bytes[i] = (byte) (v & 0xff);
            v = v >> 8;
        }
        return bytes;
    }

    public static byte[] getLongBytes(long v) {
        byte[] bytes = new byte[8];
        for (int i = 7; i >= 0; i--) {
            bytes[i] = (byte) (v & 0xff);
            v = v >> 8;
        }
        return bytes;
    }

    public static int getByteCount(byte[] bytes, int check) {
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] != check) {
                return bytes.length - i;
            }
        }
        return 0;
    }
}

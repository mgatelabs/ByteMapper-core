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

    public static int NULLABLE_IDENTITY_STANDARD_BIT = 0x80;
    public static int NULLABLE_IDENTITY_SHRINK_BIT = 0x40;
    public static int NULLABLE_IDENTITY_SHRINK_MASK = 0x3F;
    public static int NULLABLE_IDENTITY_NULL_BYTE = 0x20;


    public static int writeNullableIdentity(OutputStream bo) throws Exception {
        bo.write(NULLABLE_IDENTITY_NULL_BYTE);
        return 1;
    }
    
    public static int writeNullableIdentity(OutputStream bo, IdentityInstance identity, boolean isNull) throws Exception {
        if (isNull) {
            bo.write(NULLABLE_IDENTITY_NULL_BYTE);
            return 1;
        } else {
            int customBit = identity.isCustom() ? 0 : NULLABLE_IDENTITY_STANDARD_BIT;
            if (0x00 <= identity.getIdentity() && identity.getIdentity() <= NULLABLE_IDENTITY_SHRINK_MASK) {
                bo.write(((identity.getIdentity() & NULLABLE_IDENTITY_SHRINK_MASK) | customBit) | NULLABLE_IDENTITY_SHRINK_BIT);
                return 1;
            } else {
                return writeDefinitionValue(bo, identity.getIdentity(), customBit);
            }
        }
    }
    
    public static IdentityInstance readNullableIdentity(LimitedInputStream lir) throws Exception {  
        final int metaData = lir.read();
        if ((metaData) == NULLABLE_IDENTITY_NULL_BYTE) {
            return null;
        } else {
            final boolean isCustom = !((metaData & NULLABLE_IDENTITY_STANDARD_BIT) == NULLABLE_IDENTITY_STANDARD_BIT);
            final boolean isShrunk = ((metaData & NULLABLE_IDENTITY_SHRINK_BIT) == NULLABLE_IDENTITY_SHRINK_BIT);
            if (isShrunk) {
                return new IdentityInstance((metaData & NULLABLE_IDENTITY_SHRINK_MASK), isCustom);
            }
            return new IdentityInstance(readDefinitionValue(lir, metaData), isCustom);
        }
    }

    // Nullable Size

    // [SHRINK][NULL][*][*][#][#][#][#]

    public static int NULLABLE_SIZE_NULL_BYTE = 0x40;
    public static int NULLABLE_SIZE_SHRINK_BIT = 0x80;
    public static int NULLABLE_SIZE_SHRINK_MASK = 0x7F;

    public static int writeNullableSize(OutputStream bo) throws Exception {
        bo.write(NULLABLE_SIZE_NULL_BYTE);
        return 1;
    }

    public static int writeNullableSize(OutputStream bo, int contentSize) throws Exception {
        return writeNullableSize(bo,contentSize, contentSize < 0);
    }

    public static int writeNullableSize(OutputStream bo, int contentSize, boolean isNull) throws Exception {
        if (isNull) {
            bo.write(NULLABLE_SIZE_NULL_BYTE);
            return 1;
        } else if (0x00 <= contentSize && contentSize <= BMStreamUtils.NULLABLE_SIZE_SHRINK_MASK){
            bo.write(((contentSize & BMStreamUtils.NULLABLE_SIZE_SHRINK_MASK)) | NULLABLE_SIZE_SHRINK_BIT);
            return 1;
        } else {
            return writeDefinitionValue(bo, contentSize);
        }
    }
    
    public static int readNullableSize(LimitedInputStream lir) throws Exception {  
        int metaData = lir.read();
        if ((metaData) == NULLABLE_SIZE_NULL_BYTE) {
            return -1;
        } else if ((metaData & NULLABLE_SIZE_SHRINK_BIT) == NULLABLE_SIZE_SHRINK_BIT) {
            return ((metaData & BMStreamUtils.NULLABLE_SIZE_SHRINK_MASK) & 0xFF);
        } else {
            return readDefinitionValue(lir, metaData);
        }
    }

    // Non-Nullable Version #
    // [SHRINK][*][*][*][#][#][#][#]

    public static int NULLABLE_VERSION_SHRINK_BIT = 0x80;
    public static int NULLABLE_VERSION_SHRINK_MASK = 0x7F;

    public static int writeVersion(final OutputStream bo, int version) throws Exception {
        if (0x00 <= version && version <= BMStreamUtils.NULLABLE_VERSION_SHRINK_MASK){
            bo.write((((version & BMStreamUtils.NULLABLE_VERSION_SHRINK_MASK)) | BMStreamUtils.NULLABLE_VERSION_SHRINK_BIT) & 0xFF);
            return 1;
        } else {
            return writeDefinitionValue(bo, version);
        }
    }
    
    public static int readVersion(final LimitedInputStream lir) throws Exception {
        final int metaData = lir.read();
        if ((metaData & BMStreamUtils.NULLABLE_VERSION_SHRINK_BIT) == BMStreamUtils.NULLABLE_VERSION_SHRINK_BIT) {
            return ((metaData & BMStreamUtils.NULLABLE_VERSION_SHRINK_MASK) & 0xFF);
        } else {
            return readDefinitionValue(lir, metaData);
        }
    }

    // Utility methods

    public static int readDefinitionValue(final LimitedInputStream lir, final int meta) throws Exception {
        int contentBytes = (meta & 0x0F), value = 0, index;
        for (index = 0; index < contentBytes; index++) {
            value = (value << 8);
            value = value | (lir.read() & 0xFF);
        }
        return value;
    }

    public static int writeDefinitionValue(final OutputStream os, int value) throws Exception {
        return writeDefinitionValue(os, value, 0);
    }

    public static int writeDefinitionValue(final OutputStream os, int value, int extra) throws Exception {
        final byte[] bytes = BMStreamUtils.getBytes(value);
        final int byteCount = BMStreamUtils.getByteCount(bytes, 0);
        os.write(((byteCount & 0x0F) | extra));
        if (byteCount > 0) {
            os.write(bytes, bytes.length - byteCount, byteCount);
        }
        return 1 + byteCount;
    }

    public static byte[] getBytes(int v) {
        byte[] bytes = new byte[4];
        for (int i = 3; i >= 0; i--) {
            bytes[i] = (byte) (v & 0xff);
            v = v >> 8;
        }
        return bytes;
    }

    public static byte[] getBytes(long v) {
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

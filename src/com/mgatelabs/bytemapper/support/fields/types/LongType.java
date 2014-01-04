/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mgatelabs.bytemapper.support.fields.types;

import com.mgatelabs.bytemapper.support.interfaces.FieldInterface;
import com.mgatelabs.bytemapper.support.io.streams.LimitedInputStream;
import com.mgatelabs.bytemapper.util.BMStreamUtils;

import java.io.OutputStream;

/**
 *
 * @author MiniMegaton
 */
public class LongType extends SimpleBaseType {

    public LongType() {
        super(Long.class.getSimpleName(), Long.class.getCanonicalName(), "A nullable instance of an Long");
    }

    public static Long readLongValue(LimitedInputStream lir) throws Exception {
        final int meta = lir.read();
        // Tiny Number 0 - 127 (0x80)
        if ((meta & 0x80) == 0x80) {
            return new Long(((meta & 0x7F) & 0xFF));
        }
        // Check for null (0x40)
        else if ((meta) == 0x40) {
            return null;
        }
        // Expanding number
        else {
            final int size = (meta & 0x0F);
            // Inverse bit (0x20)
            boolean inverse = (meta & 0x20) == 0x20;
            long value = inverse ? -1 : 0;
            for (int index = 0; index < size; index++) {
                value = (value << 8);
                value = value | (lir.read() & 0xff);
            }
            return new Long(value);
        }
    }

    @Override
    public void read(LimitedInputStream lir, FieldInterface instance, Object target) throws Exception {
        final Long value = LongType.readLongValue(lir);
        instance.setLongValue(target, value);
    }

    public static void writeLongValue(OutputStream bos, Long valueLong) throws Exception {
        // Null (0x40)
        if (valueLong == null) {
            bos.write(0x40);
        } else {
            final long value = valueLong.longValue();
            // Tiny Number (0x80)
            if (0 <= value && value <= 0x7F) {
                bos.write(0x80 | (((int) value) & 0x7F));
            } // Expanded number
            else {
                final byte[] valueBytes = BMStreamUtils.getBytes(value);
                final boolean negative = value < 0;
                int byteCount = BMStreamUtils.getByteCount(valueBytes, negative ? -1 : 0);
                bos.write(((negative ? 0x20 : 0) + byteCount) & 0xFF);
                bos.write(valueBytes, valueBytes.length - byteCount, byteCount);
            }
        }
    }

    @Override
    public void write(OutputStream bos, FieldInterface instance, Object target) throws Exception {
        final Long value = instance.getLongValue(target);
        LongType.writeLongValue(bos, value);
    }

}

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
public class IntegerType extends SimpleBaseType {

    public IntegerType() {
        super(Integer.class.getSimpleName(), Integer.class.getCanonicalName(), "A nullable instance of an Integer");
    }

    public static Integer readIntegerValue(LimitedInputStream lir) throws Exception {
        final int meta = lir.read();
        // Tiny Number 0 - 127 (0x7F)
        if ((meta & 0x80) == 0x80) {
            return new Integer(((meta & 0x7F) & 0xFF));
        }
        // Check for null (0x40)
        else if ((meta) == 0x40) {
            return null;
        } else {
            final int size = (meta & 0x0F);
            // Inverse bit (0x20)
            boolean inverse = (meta & 0x20) == 0x20;
            int value = inverse ? -1 : 0;
            for (int index = 0; index < size; index++) {
                value = (value << 8);
                value = value | (lir.read() & 0xff);
            }
            return new Integer(value);
        }
    }

    @Override
    public void read(LimitedInputStream lir, FieldInterface instance, Object target) throws Exception {
        final Integer value = IntegerType.readIntegerValue(lir);
        instance.setIntegerValue(target, value);
    }

    public static void writeIntegerValue(OutputStream bos, Integer intValue) throws Exception {
        // Null
        if (intValue == null) {
            bos.write(0x40);
        } else {
            final int value = intValue.intValue();
            // Tiny Number
            if (value >= 0 && value <= 0x7F) {
                bos.write(0x80 | (value & 0x7F));
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
        final Integer value = instance.getIntegerValue(target);
        IntegerType.writeIntegerValue(bos, value);
    }

}

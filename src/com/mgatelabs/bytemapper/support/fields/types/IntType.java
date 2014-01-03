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
public class IntType extends SimpleBaseType {

    public IntType() {
        super("int", "int", "A primitive int value");
    }

    public static int readIntValue(LimitedInputStream lir) throws Exception {
        final int meta = lir.read();
        // Tiny Number 0 - 127 (0x80)
        if ((meta & 0x80) == 0x80) {
            return ((meta & 0x7F) & 0xFF);
        } else {
            final int size = (meta & 0x0F);
            // Inversed bit (0x40)
            boolean inversed = (meta & 0x40) == 0x40;
            int value = inversed ? -1 : 0;
            for (int index = 0; index < size; index++) {
                value = (value << 8);
                value = value | (lir.read() & 0xff);
            }
            return value;
        }
    }

    @Override
    public void read(LimitedInputStream lir, FieldInterface instance, Object target) throws Exception {
        final int value = IntType.readIntValue(lir);
        instance.setIntValue(target, value);
    }

    public static void writeIntValue(OutputStream bos, int value) throws Exception {
        // Tiny Number
        if (value >= 0 && value <= 127) {
            bos.write(0x80 | (value & 0x7F));
        } // Expaned number
        else {
            final byte[] valueBytes = BMStreamUtils.getIntegerBytes(value);
            final boolean negative = value < 0;
            int byteCount = BMStreamUtils.getByteCount(valueBytes, negative ? -1 : 0);
            bos.write(((negative ? 0x40 : 0) + byteCount) & 0xFF);
            bos.write(valueBytes, valueBytes.length - byteCount, byteCount);
        }
    }

    @Override
    public void write(OutputStream bos, FieldInterface instance, Object target) throws Exception {
        final int intValue = (int) instance.getIntValue(target);
        IntType.writeIntValue(bos, intValue);
    }
}

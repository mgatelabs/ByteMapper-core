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
public class StringType extends SimpleBaseType {

    public StringType() {
        super(String.class.getSimpleName(), String.class.getCanonicalName(), "A nullable instance of an String");
    }

    @Override
    public void read(LimitedInputStream lir, FieldInterface instance, Object target) throws Exception {
        int size = BMStreamUtils.readNullableSize(lir);

        if (size == -1) {
            instance.setStringValue(target, null);
        } else if (size > 0) {
            byte[] bytes = new byte[size];
            lir.read(bytes);
            instance.setStringValue(target, new String(bytes, "UTF-8"));
        } else {
            instance.setStringValue(target, "");
        }
    }

    @Override
    public void write(OutputStream bos, FieldInterface instance, Object target) throws Exception {
        String v = (String) instance.getStringValue(target);
        byte[] bytes;

        if (v != null) {
            bytes = v.getBytes("UTF-8");
            BMStreamUtils.writeNullableSize(bos, bytes.length, false);
            if (bytes.length > 0) {
                bos.write(bytes);
            }
        } else {
            BMStreamUtils.writeNullableSize(bos);
        }
    }
}

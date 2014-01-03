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
public class BlobType extends SimpleBaseType {

    public BlobType() {
        super("BLOB", "byte []", "byte []");
    }

    @Override
    public void read(LimitedInputStream lir, FieldInterface instance, Object target) throws Exception {
        int size = BMStreamUtils.readNullableSize(lir);
        if (size != -1) {
            byte[] bytes = new byte[size];
            lir.read(bytes);
            instance.setObjectValue(target, bytes);
        } else {
            instance.setObjectValue(target, (byte[]) null);
        }
    }

    @Override
    public void write(OutputStream bos, FieldInterface instance, Object target) throws Exception {
        byte[] bytes = (byte[]) instance.getObjectValue(target);

        if (bytes == null) {
            BMStreamUtils.writeNullableSize(bos);
        } else {
            BMStreamUtils.writeNullableSize(bos, bytes.length, false);
            if (bytes.length > 0) {
                bos.write(bytes);
            }
        }
    }
}

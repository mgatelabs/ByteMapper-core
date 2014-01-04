package com.mgatelabs.bytemapper.support.fields.types;

import com.mgatelabs.bytemapper.support.interfaces.FieldInterface;
import com.mgatelabs.bytemapper.support.io.streams.LimitedInputStream;
import com.mgatelabs.bytemapper.util.BMStreamUtils;

import java.io.OutputStream;

/**
 * Created by MiniMegaton on 1/3/14.
 */
public class EnumType extends SimpleBaseType {

    public EnumType() {
        // Not ready
        super("Enum", "Enumeration", "A nullable instance of an enum");
    }

    @Override
    public void read(LimitedInputStream lir, FieldInterface instance, Object target) throws Exception {
        int size = BMStreamUtils.readNullableSize(lir);

        if (size < 1) {
            instance.setEnumValue(target, null);
        } else if (size > 0) {
            byte[] bytes = new byte[size];
            lir.read(bytes);
            instance.setEnumValue(target, new String(bytes, "UTF-8"));
        }
    }

    @Override
    public void write(OutputStream bos, FieldInterface instance, Object target) throws Exception {
        String v = (String) instance.getEnumValue(target);
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

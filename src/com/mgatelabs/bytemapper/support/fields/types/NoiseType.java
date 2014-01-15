package com.mgatelabs.bytemapper.support.fields.types;

import com.mgatelabs.bytemapper.support.instances.FieldInstance;
import com.mgatelabs.bytemapper.support.instances.FormatInstance;
import com.mgatelabs.bytemapper.support.interfaces.FieldInterface;
import com.mgatelabs.bytemapper.support.io.streams.LimitedInputStream;
import com.mgatelabs.bytemapper.util.BMStreamUtils;

import java.io.OutputStream;
import java.security.SecureRandom;

/**
 * Created by Michael Fuller on 1/14/14.
 */
public class NoiseType extends SimpleBaseType {
    private int index;
    private int length;

    public static String PROP_MIN = "min";
    public static String PROP_MAX = "max";

    public NoiseType() {
        this(0, 5);
    }

    public NoiseType(int min, int max) {
        super("noise", "byte []", "byte []");
        this.index = min;
        this.length = max - min;
        if (index < 0) {
            index = 0;
        }
        if (length < 0) {
            length = 0;
        }
    }

    @Override
    public AbstractBaseType getInstance(FormatInstance format, FieldInstance field) {
        int min = Integer.parseInt( field.getDefinition().getProperties().get(PROP_MIN));
        int max = Integer.parseInt( field.getDefinition().getProperties().get(PROP_MAX));
        return new NoiseType(min, max);
    }

    @Override
    public void read(LimitedInputStream lir, FieldInterface instance, Object target) throws Exception {
        int size = BMStreamUtils.readNullableSize(lir);
        lir.skip(size);
    }

    @Override
    public void write(OutputStream bos, FieldInterface instance, Object target) throws Exception {

        SecureRandom sc = new SecureRandom();
        int size = index + sc.nextInt(length);

        byte[] bytes = new byte [size];
        sc.nextBytes(bytes);

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

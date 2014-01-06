package com.mgatelabs.bytemapper.support.fields.types;

import com.mgatelabs.bytemapper.support.interfaces.FieldInterface;
import com.mgatelabs.bytemapper.support.io.streams.LimitedInputStream;

import java.io.OutputStream;
import java.util.Date;

/**
 * Created by MiniMegaton on 1/5/14.
 */
public class DateType extends SimpleBaseType {

    public DateType() {
        super(Date.class.getSimpleName(), Date.class.getName(), "A nullable date instance");
    }

    @Override
    public void read(LimitedInputStream lir, FieldInterface instance, Object target) throws Exception {
        Long l = LongType.readLongValue(lir);
        if (l == null) {
            instance.setObjectValue(target, null);
        } else {
            Date d = new Date();
            d.setTime(l.longValue());
            instance.setObjectValue(target, d);
        }
    }

    @Override
    public void write(OutputStream bos, FieldInterface instance, Object target) throws Exception {
        final Date value = (Date)instance.getObjectValue(target);
        if (value == null) {
            LongType.writeLongValue(bos, null);
        } else {
            LongType.writeLongValue(bos, new Long(value.getTime()));
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mgatelabs.bytemapper.support.fields.types;

import com.mgatelabs.bytemapper.support.instances.FieldInstance;
import com.mgatelabs.bytemapper.support.instances.FormatInstance;
import com.mgatelabs.bytemapper.support.interfaces.FieldInterface;
import com.mgatelabs.bytemapper.support.interfaces.TagInterface;
import com.mgatelabs.bytemapper.support.io.streams.LimitedInputStream;
import com.mgatelabs.bytemapper.util.BMStreamUtils;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MiniMegaton
 */
public class ListType extends ComplexBaseType {

    public ListType() {
        super("list", "java.util.List", "A nullable instance of an List<Tag>");
    }

    public ListType(AbstractBaseType source, FormatInstance format, TagInterface tag) {
        super(source, format, tag);
    }

    @Override
    public AbstractBaseType getInstance(FormatInstance format, FieldInstance field) {
        return new ListType(this, format, findTagFromField(format, field));
    }

    @Override
    public void read(LimitedInputStream lir, FieldInterface instance, Object target) throws Exception {

        int contentSize = BMStreamUtils.readNullableSize(lir);
        if (contentSize == -1) {
            instance.setObjectValue(target, null);
            return;
        }

        final LimitedInputStream limiter = lir.spawn(contentSize);

        final int itemCount = BMStreamUtils.readSize(limiter);

        final List l = hasKnownTag() ? getList(getKnownTag().getClassReference(), itemCount) : new ArrayList(itemCount);

        for (int i = 0; i < itemCount; i++) {

            final TagInterface readTag;

            if (hasKnownTag()) {
                readTag = getKnownTag();

            } else {
                readTag = format.readTagHeader(lir);
            }

            final Object o;

            if (readTag != null) {
                o = readTag.readContent(lir, hasKnownTag());
            } else {
                o = null;
            }

            l.add(o);
        }

        instance.setObjectValue(target, l);
    }

    public static <T> List<T> getList(Class<T> c, int itemCount) {
        return new ArrayList<>(itemCount);
    }

    @Override
    public void write(OutputStream os, FieldInterface instance, Object target) throws Exception {

        final List l = (List) instance.getObjectValue(target);

        if (l == null) {
            BMStreamUtils.writeNullableSize(os);
            return;
        }

        final ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);

        BMStreamUtils.writeSize(baos, l.size());

        for (int i = 0; i < l.size(); i++) {

            final Object insideTarget = l.get(i);

            final TagInterface writeTag;

            if (hasKnownTag()) {
                writeTag = getKnownTag();
            } else {
                writeTag = format.writeTagHeader(baos, insideTarget);
            }

            if (writeTag != null) {
                writeTag.writeContent(baos, insideTarget, hasKnownTag());
            }
        }

        // Write total size;
        BMStreamUtils.writeNullableSize(os, baos.size(), false);
        os.write(baos.toByteArray());
        baos.reset();

    }
}

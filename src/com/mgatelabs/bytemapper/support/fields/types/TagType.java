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

import java.io.OutputStream;

/**
 *
 * @author MiniMegaton
 */
public class TagType extends ComplexBaseType {

    public TagType() {
        super("tag", "Tag Instance", "Object Instance");
    }

    public TagType(AbstractBaseType source, FormatInstance format, TagInterface tag) {
        super(source, format, tag);
    }

    @Override
    public AbstractBaseType getInstance(FormatInstance format, FieldInstance field) {
        return new TagType(this, format, findTagFromField(format, field));
    }

    @Override
    public void read(LimitedInputStream lir, FieldInterface instance, Object target) throws Exception {

        final Object o;
        final TagInterface readTag;

        if (hasKnownTag()) {
            readTag = getKnownTag();
        } else {
            readTag = format.readTagHeader(lir);
        }

        if (readTag != null) {
            o = readTag.readContent(lir, hasKnownTag());
        } else {
            o = null;
        }

        instance.setObjectValue(target, o);
    }

    @Override
    public void write(OutputStream bos, FieldInterface instance, Object target) throws Exception {

        final TagInterface writeTag;

        Object insideTarget = instance.getObjectValue(target);

        if (hasKnownTag()) {
            writeTag = getKnownTag();
        } else {
            writeTag = format.writeTagHeader(bos, insideTarget);
        }

        // Make sure we found a tag to write
        if (writeTag != null) {
            writeTag.writeContent(bos, insideTarget, hasKnownTag());
        }
    }
}

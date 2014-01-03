/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mgatelabs.bytemapper.support.fields.types;

import com.mgatelabs.bytemapper.support.instances.FieldInstance;
import com.mgatelabs.bytemapper.support.instances.FormatInstance;
import com.mgatelabs.bytemapper.support.interfaces.TagInterface;

/**
 *
 * @author MiniMegaton
 */
public abstract class ComplexBaseType extends AbstractBaseType {

    final FormatInstance format;
    final TagInterface knownTag;

    public ComplexBaseType(String type, String name, String description) {
        super(type, name, description);
        format = null;
        knownTag = null;
    }

    public ComplexBaseType(AbstractBaseType source, FormatInstance format, TagInterface tag) {
        super(source.getType(), source.getName(), source.getDescription());
        this.format = format;
        this.knownTag = tag;
    }

    public FormatInstance getFormat() {
        return format;
    }

    public TagInterface getTag() {
        return knownTag;
    }

    public TagInterface getKnownTag() {
        return knownTag;
    }

    public boolean hasKnownTag() {
        return knownTag != null;
    }

    @Override
    public boolean isType(String type) {
        type = type.toLowerCase();
        return type.startsWith(getType()) || type.startsWith(getType() + "/");
    }
    
    public TagInterface findTagFromField(FormatInstance format, FieldInstance field) {
        TagInterface foundTag = null;
        
        String startWithCheck = getType() + "/";
        
        if (field.getFieldType().toLowerCase().startsWith(startWithCheck)) {
            String tagName = field.getFieldType().substring(startWithCheck.length());
            foundTag = format.getTag(tagName);
        }
        
        return foundTag;
    }
}

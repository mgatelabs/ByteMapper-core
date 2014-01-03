/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mgatelabs.bytemapper.support.tags;

import com.mgatelabs.bytemapper.support.definitions.FieldDefinition;
import com.mgatelabs.bytemapper.support.definitions.TagDefinition;
import com.mgatelabs.bytemapper.support.fields.types.AbstractBaseType;
import com.mgatelabs.bytemapper.support.instances.FieldInstance;
import com.mgatelabs.bytemapper.support.instances.IdentityInstance;
import com.mgatelabs.bytemapper.support.interfaces.TagInterface;
import com.mgatelabs.bytemapper.support.io.streams.LimitedInputStream;
import com.mgatelabs.bytemapper.util.BMStreamUtils;

import java.beans.PropertyDescriptor;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MiniMegaton
 */
public class ComplexTagInstance extends BMStreamUtils implements TagInterface{

    private Class reference;
    private TagDefinition definition;
    private boolean ready;
    private List<FieldInstance> fields;
    private final int version;
    private final IdentityInstance identity;

    ComplexTagInstance(Class reference, int version, IdentityInstance identity) {
        this.reference = reference;
        fields = new ArrayList<>();
        this.version = version;
        this.identity = identity;
    }

    public ComplexTagInstance(TagDefinition definition, Class reference, int version) {
        this(reference, version, IdentityInstance.getCustomIdentity(definition.getName(), definition.getIdentity()));
        this.reference = reference;
        this.definition = definition;
        ready = this.reference != null;
        if (ready) {
            linkFields();
        }
    }

    public ComplexTagInstance(Class reference, AbstractBaseType type, String name, int identity, int version) {
        this(reference, version, IdentityInstance.getStandardIdentity(name, identity));
        this.reference = null;
        this.definition = null;
    }

    @Override
    public Class getClassReference() {
        return reference;
    }
    
    @Override
    public Object getClassInstance() throws Exception {
        return reference.newInstance();
    }

    private void linkFields() {
        for (FieldDefinition fieldDefinition : definition.getFields()) {
            // Make sure the field is in this version
            if (fieldDefinition.isVersioned(version)) {
                try {
                    PropertyDescriptor pd = new PropertyDescriptor(fieldDefinition.getName(), reference);//
                    if (pd.getReadMethod() != null && pd.getWriteMethod() != null) {
                        FieldInstance instanceField = new FieldInstance(fieldDefinition, pd.getReadMethod(), pd.getWriteMethod());
                        fields.add(instanceField);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(ComplexTagInstance.class.getName()).log(Level.SEVERE, null, ex);
                    ready = false;
                }
            }
        }
    }

    @Override
    public IdentityInstance getIdentity() {
        return identity;
    }

    public Class getReference() {
        return reference;
    }

    public void setReference(Class reference) {
        this.reference = reference;
    }

    public TagDefinition getDefinition() {
        return definition;
    }

    public void setDefinition(TagDefinition definition) {
        this.definition = definition;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public List<FieldInstance> getFields() {
        return fields;
    }

    public void setFields(List<FieldInstance> fields) {
        this.fields = fields;
    }

    @Override
    public Object readContent(LimitedInputStream lir, boolean isNullable) throws Exception {
        final int contentSize;       
        if (isNullable) {
            contentSize = BMStreamUtils.readNullableSize(lir);
            if (contentSize == -1) {
                return null;
            }
        } else {
            contentSize = BMStreamUtils.readSize(lir);
        }
        final LimitedInputStream spawn = lir.spawn(contentSize);
        final Object o = getClassInstance();
        for (FieldInstance field : getFields()) {
            field.read(spawn, o);
        }
        return o;
    }

    @Override
    public void writeContent(OutputStream os, Object target, boolean isNullable) throws Exception {
        if (target == null && isNullable) {
            BMStreamUtils.writeNullableSize(os);
        } else {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
            for (FieldInstance field : getFields()) {
                field.write(baos, target);
            }
            if (isNullable) {
                BMStreamUtils.writeNullableSize(os, baos.size(), false);
            } else {
                BMStreamUtils.writeSize(os, baos.size());
            }
            os.write(baos.toByteArray());
            baos.reset();
        }
    }
}

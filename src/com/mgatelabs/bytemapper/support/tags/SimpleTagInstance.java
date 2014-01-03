/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mgatelabs.bytemapper.support.tags;

import com.mgatelabs.bytemapper.support.fields.types.AbstractBaseType;
import com.mgatelabs.bytemapper.support.instances.FieldSelfInstance;
import com.mgatelabs.bytemapper.support.instances.IdentityInstance;
import com.mgatelabs.bytemapper.support.interfaces.TagInterface;
import com.mgatelabs.bytemapper.support.io.streams.LimitedInputStream;
import com.mgatelabs.bytemapper.util.BMStreamUtils;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

/**
 *
 * @author MiniMegaton
 */
public class SimpleTagInstance implements TagInterface {

    private final Class reference;
    private AbstractBaseType type;
    private FieldSelfInstance instance;
    private final IdentityInstance identity;

    public SimpleTagInstance(Class reference, AbstractBaseType type, IdentityInstance identity) {
        this.reference = reference;
        this.type = type;
        this.identity = identity;
        instance = new FieldSelfInstance(this.type);
    }
    
    public AbstractBaseType getType() {
        return type;
    }

    public void setType(AbstractBaseType type) {
        this.type = type;
    }

    public FieldSelfInstance getInstance() {
        return instance;
    }

    public void setInstance(FieldSelfInstance instance) {
        this.instance = instance;
    }
    
    @Override
    public IdentityInstance getIdentity() {
        return identity;
    }
    
    @Override
    public Object readContent(LimitedInputStream lir, boolean isNullable) throws Exception {
        final int contentSize; 
        
        contentSize = BMStreamUtils.readNullableSize(lir);
        if (contentSize == -1) {
            return null;
        }

        type.read(lir.spawn(contentSize), instance, instance);
        
        return instance.getObjectValue(instance);
    }

    @Override
    public void writeContent(OutputStream os, Object target, boolean isNullable) throws Exception {        
        if (target == null && isNullable) {
            BMStreamUtils.writeNullableSize(os);
        } else {
            // Update the instances value
            instance.setObjectValue(target, target);
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
            
            type.write(baos, instance, target);
            BMStreamUtils.writeNullableSize(os, baos.size(), false);
            os.write(baos.toByteArray());
            baos.reset();
        }
    }

    @Override
    public Class getClassReference() {
        return reference;
    }
    
    @Override
    public Object getClassInstance() throws Exception {
        return reference.newInstance();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mgatelabs.bytemapper.support.instances;

import com.mgatelabs.bytemapper.support.definitions.FieldDefinition;
import com.mgatelabs.bytemapper.support.fields.types.AbstractBaseType;
import com.mgatelabs.bytemapper.support.interfaces.FieldInterface;
import com.mgatelabs.bytemapper.support.io.streams.LimitedInputStream;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MiniMegaton
 */
public class FieldInstance implements FieldInterface{

    private final Method readMethod;
    private final Method writeMethod;
    private FieldDefinition definition;
    private boolean ready;
    AbstractBaseType type;

    public FieldInstance(FieldDefinition definition, Method readMethod, Method writeMethod) {
        this.readMethod = readMethod;
        this.writeMethod = writeMethod;
        this.definition = definition;
        this.ready = this.readMethod != null && this.writeMethod != null;
        type = null;
    }
    
    public FieldInstance(Class reference, String value) {
        final PropertyDescriptor pd;
        try {
            pd = new PropertyDescriptor(value, reference);
        } catch (IntrospectionException ex) {
            Logger.getLogger(FieldInstance.class.getName()).log(Level.SEVERE, null, ex);
            this.ready = false;
            this.readMethod = null;
            this.writeMethod = null;
            return;
        }
        this.readMethod = pd.getReadMethod();
        this.writeMethod = pd.getWriteMethod();
        this.definition = null;
        this.ready = this.readMethod != null && this.writeMethod != null;
        type = null;
    }

    @Override
    public String getFieldType() {
        return definition.getType();
    }
    
    public Method getReadMethod() {
        return readMethod;
    }

    public Method getWriteMethod() {
        return writeMethod;
    }

    public FieldDefinition getDefinition() {
        return definition;
    }

    public void setDefinition(FieldDefinition definition) {
        this.definition = definition;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public AbstractBaseType getType() {
        return type;
    }

    public void setType(AbstractBaseType type) {
        this.type = type;
    }

    // Getters

    @Override
    public String getEnumValue(Object instance) throws Exception {
        Class<?> returnEnumClass = getReadMethod().getReturnType();
        Object value = getObjectValue(instance);
        if (value != null) {
            return value.toString();
        }
        return null;
    }

    @Override
    public String getStringValue(Object instance) throws Exception {
        return (String) getReadMethod().invoke(instance);
    }

    @Override
    public Object getObjectValue(Object instance) throws Exception {
        return (Object) getReadMethod().invoke(instance);
    }

    @Override
    public Long getLongValue(Object instance) throws Exception {
        return (Long) getReadMethod().invoke(instance);
    }

    @Override
    public Integer getIntegerValue(Object instance) throws Exception {
        return (Integer) getReadMethod().invoke(instance);
    }

    @Override
    public long getLngValue(Object instance) throws Exception {
       return (long) getReadMethod().invoke(instance);
    }

    @Override
    public int getIntValue(Object instance) throws Exception {
        return (int) getReadMethod().invoke(instance);
    }

    // Setters

    @Override
    public void setEnumValue(Object instance, String value) throws Exception {
        if (value == null) {
            setObjectValue(instance, null);
        } else {
            Class<?> returnEnumClass = getReadMethod().getReturnType();
            setObjectValue(instance, Enum.valueOf((Class<Enum>)returnEnumClass, value));
        }
    }

    @Override
    public void setStringValue(Object instance, String value) throws Exception {
        getWriteMethod().invoke(instance, value);
    }

    @Override
    public void setObjectValue(Object instance, Object value) throws Exception {
        getWriteMethod().invoke(instance, value);
    }

    @Override
    public void setLongValue(Object instance, Long value) throws Exception {
        getWriteMethod().invoke(instance, value);
    }

    @Override
    public void setIntegerValue(Object instance, Integer value) throws Exception {
        getWriteMethod().invoke(instance, value);
    }

    @Override
    public void setLngValue(Object instance, long value) throws Exception {
        getWriteMethod().invoke(instance, value);
    }

    @Override
    public void setIntValue(Object instance, int value) throws Exception {
        getWriteMethod().invoke(instance, value);
    }

    @Override
    public void read(LimitedInputStream lir, Object target) throws Exception {
        getType().read(lir, this, target);
    }
    
    @Override
    public void write(OutputStream os, Object target) throws Exception {
        getType().write(os, this, target);
    }
}

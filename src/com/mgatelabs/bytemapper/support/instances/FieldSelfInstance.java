/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mgatelabs.bytemapper.support.instances;

import com.mgatelabs.bytemapper.support.fields.types.AbstractBaseType;
import com.mgatelabs.bytemapper.support.interfaces.FieldInterface;
import com.mgatelabs.bytemapper.support.io.streams.LimitedInputStream;

import java.io.OutputStream;

/**
 *
 * @author MiniMegaton
 */
public class FieldSelfInstance implements FieldInterface {

    AbstractBaseType type;
    
    Object value;

    private String fieldType;
    
    public FieldSelfInstance(AbstractBaseType type) {
        this.type = type;
    }
    
    @Override
    public String getFieldType() {
        return fieldType;
    }

    public void setType(AbstractBaseType type) {
        this.type = type;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }
    
    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
    
    @Override
    public void read(LimitedInputStream lir, Object target) throws Exception {
        type.read(lir, this, target);
    }

    @Override
    public void write(OutputStream os, Object target) throws Exception {
        type.write(os, this, target);
    }

    @Override
    public String getStringValue(Object instance) throws Exception {
        return (String) value;
    }

    @Override
    public Object getObjectValue(Object instance) throws Exception {
        return (Object) value;
    }

    @Override
    public Long getLongValue(Object instance) throws Exception {
        return (Long) value;
    }

    @Override
    public Integer getIntegerValue(Object instance) throws Exception {
        return (Integer) value;
    }

    @Override
    public long getLngValue(Object instance) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getIntValue(Object instance) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setStringValue(Object instance, String value) throws Exception {
        this.value = value;
    }

    @Override
    public void setObjectValue(Object instance, Object value) throws Exception {
        this.value = value;
    }

    @Override
    public void setLongValue(Object instance, Long value) throws Exception {
        this.value = value;
    }

    @Override
    public void setIntegerValue(Object instance, Integer value) throws Exception {
        this.value = value;
    }

    @Override
    public void setLngValue(Object instance, long value) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setIntValue(Object instance, int value) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}

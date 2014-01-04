/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mgatelabs.bytemapper.support.interfaces;

import com.mgatelabs.bytemapper.support.io.streams.LimitedInputStream;

import java.io.OutputStream;

/**
 *
 * @author MiniMegaton
 */
public interface FieldInterface {
    
    public void read(LimitedInputStream lir, Object target) throws Exception;
    public void write(OutputStream os, Object target) throws Exception;
    
    public String getFieldType();

    public String getEnumValue(Object instance) throws Exception;
    public String getStringValue(Object instance) throws Exception;
    public Object getObjectValue(Object instance) throws Exception;
    public Long getLongValue(Object instance) throws Exception;
    public Integer getIntegerValue(Object instance) throws Exception;
    public long getLngValue(Object instance) throws Exception;
    public int getIntValue(Object instance) throws Exception;

    public void setEnumValue(Object instance, String value) throws Exception;
    public void setStringValue(Object instance, String value) throws Exception;
    public void setObjectValue(Object instance, Object value) throws Exception;
    public void setLongValue(Object instance, Long value) throws Exception;
    public void setIntegerValue(Object instance, Integer value) throws Exception;
    public void setLngValue(Object instance, long value) throws Exception;
    public void setIntValue(Object instance, int value) throws Exception;
}

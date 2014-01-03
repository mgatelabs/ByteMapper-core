/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mgatelabs.bytemapper.support.fields.types;

import com.mgatelabs.bytemapper.support.instances.FieldInstance;
import com.mgatelabs.bytemapper.support.instances.FormatInstance;
import com.mgatelabs.bytemapper.support.interfaces.FieldInterface;
import com.mgatelabs.bytemapper.support.io.streams.LimitedInputStream;

import java.io.File;
import java.io.OutputStream;

/**
 *
 * @author MiniMegaton
 */
public abstract class AbstractBaseType {
    
    private final String type, name, description;

    public AbstractBaseType(String type, String name, String description) {
        this.type = type.toLowerCase();
        this.name = name;
        this.description = description;
    }
    
    public abstract AbstractBaseType getInstance(FormatInstance format, FieldInstance field);
    
    public abstract boolean isType(String type);

    public String getType() {
        return type;
    }
    
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
    
    public void linkFile(File to) {
        
    }
    
    public abstract void read(LimitedInputStream lir, FieldInterface instance, Object target) throws Exception;
    
    public abstract void write(OutputStream bos, FieldInterface instance, Object target) throws Exception;
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mgatelabs.bytemapper.support.factories;

import com.mgatelabs.bytemapper.support.fields.types.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MiniMegaton
 */
public class TypeFactory {
    
    private List<AbstractBaseType> types;

    public TypeFactory() {
        types = new ArrayList<>(20);
        addTypes();
    }
    
    private void addTypes() {
        // Numeric
        
        // Primitive int
        addType(new IntType());
        // Primitive long
        addType(new LngType());
        
        // java.lang.Integer
        addType(new IntegerType());
        // java.lang.Long
        addType(new LongType());
        
        // java.lang.String
        addType(new StringType());

        // java.uil.Date
        addType(new DateType());

        // java.util.List
        addType(new ListType());

        // Enums
        addType(new EnumType());
        
        // Byte Arrays
        addType(new BlobType());
        
        // Objects-Tags
        addType(new TagType());
        
        // Files
        addType(new FileLinkType());
    }
    
    public void addType(AbstractBaseType type) {
        this.types.add(type);
    }
    
    public void initLink(File link) {
        for (AbstractBaseType type: types) {
            type.linkFile(link);
        }
    }
    
    public AbstractBaseType find(String type) {
        for(AbstractBaseType abt: types) {
            if (abt.isType(type)) {
                return abt;
            }
        }
        return null;
    }
    
}

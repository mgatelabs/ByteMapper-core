/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mgatelabs.bytemapper.support.fields.types;

import com.mgatelabs.bytemapper.support.instances.FieldInstance;
import com.mgatelabs.bytemapper.support.instances.FormatInstance;

/**
 *
 * @author MiniMegaton
 */
public abstract class SimpleBaseType extends AbstractBaseType {

    public SimpleBaseType(String type, String name, String description) {
        super(type, name, description);
    }
    
    @Override
    public AbstractBaseType getInstance(FormatInstance format, FieldInstance field) {
        return this;
    }
    
    public boolean isType(String type) {
        return this.getType().equalsIgnoreCase(type);
    }
    
}

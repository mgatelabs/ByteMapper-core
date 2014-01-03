/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mgatelabs.bytemapper.support.definitions;

import java.util.List;
import java.util.Map;

/**
 *
 * @author MiniMegaton
 */
public class TagDefinition extends AbstractDefinition {

    private int identity;
    private List<FieldDefinition> fields;
    private Map<String, String> properties;

    public TagDefinition() {
    }

    public List<FieldDefinition> getFields() {
        return fields;
    }

    public void setFields(List<FieldDefinition> fields) {
        this.fields = fields;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }
}

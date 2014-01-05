/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mgatelabs.bytemapper.support.definitions;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 *
 * @author MiniMegaton
 */
@XmlRootElement
public class FormatDefinition extends AbstractDefinition {
    
    private List<TagDefinition> tags;

    public FormatDefinition() {
    }

    public List<TagDefinition> getTags() {
        return tags;
    }

    public void setTags(List<TagDefinition> tags) {
        this.tags = tags;
    }
    
    public static FormatDefinition load(File input) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        FormatDefinition def;
        def = mapper.readValue(input, FormatDefinition.class);
        def.sanity();
        return def;
    }

    public static FormatDefinition load(InputStream is) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        FormatDefinition def;
        try {
        def = mapper.readValue(is, FormatDefinition.class);
        } finally {
            is.close();
        }
        def.sanity();
        return def;
    }
    
    public void sanity() throws Exception {
        if (tags.isEmpty()) {
            throw new Exception("Format does not contain any custom tags");
        } else {
            for (TagDefinition tag: tags) {
                if (tag.getIdentity() <= 0) {
                    throw new Exception("The defined tag " + tag.getName() + " has an identity number < 0");
                }
            }
        }
    }
}

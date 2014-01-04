/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mgatelabs.bytemapper.support.instances;

import com.mgatelabs.bytemapper.support.definitions.FormatDefinition;
import com.mgatelabs.bytemapper.support.definitions.TagDefinition;
import com.mgatelabs.bytemapper.support.factories.TypeFactory;
import com.mgatelabs.bytemapper.support.fields.types.AbstractBaseType;
import com.mgatelabs.bytemapper.support.fields.types.IntegerType;
import com.mgatelabs.bytemapper.support.fields.types.LongType;
import com.mgatelabs.bytemapper.support.fields.types.StringType;
import com.mgatelabs.bytemapper.support.interfaces.TagInterface;
import com.mgatelabs.bytemapper.support.io.streams.LimitedInputStream;
import com.mgatelabs.bytemapper.support.tags.ComplexTagInstance;
import com.mgatelabs.bytemapper.support.tags.SimpleTagInstance;
import com.mgatelabs.bytemapper.util.BFLTag;
import com.mgatelabs.bytemapper.util.BMStreamUtils;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author MiniMegaton
 */
public class FormatInstance {

    Map<String, TagInterface> tagsByName;
    Map<Integer, TagInterface> tagsByIdentity;
    Map<Class, TagInterface> tagsByClass;

    private final FormatDefinition definition;
    private final int version;
    private boolean ready;

    public FormatInstance(FormatDefinition definition, Map<String, Class<?>> clazzes, int version, TypeFactory types) {
        this.definition = definition;
        tagsByName = new HashMap<>();
        tagsByIdentity = new HashMap<>();
        tagsByClass = new HashMap<>();
        this.version = version;
        List<FieldInstance> fields = new ArrayList<>(100);

        for (TagDefinition def : definition.getTags()) {
            if (def.isVersioned(version)) {
                ComplexTagInstance tagInstance = new ComplexTagInstance(def, clazzes.get(def.getName()), version);
                if (tagInstance.isReady()) {
                    fields.addAll(tagInstance.getFields());
                    addTagInstance(tagInstance.getIdentity().getName(), tagInstance);
                }
            }
        }
        
        // Add the basic types
        init();

        for (FieldInstance field : fields) {
            AbstractBaseType abt = types.find(field.getDefinition().getType());
            if (abt != null) {
                field.setType(abt.getInstance(this, field));
            }
        }
        
        fields.clear();
    }

    private void addTagInstance(String name, TagInterface tf) {
        tagsByName.put(name, tf);
        tagsByIdentity.put(tf.getIdentity().getIdentityKey(), tf);
        tagsByClass.put(tf.getClassReference(), tf);
    }
    
    private void init() {
        addStandardTags();
    }
    
    public void addStandardTags() {
        addStandardTag(Integer.class, new IntegerType(), 1);
        addStandardTag(Long.class, new LongType(), 2);
        addStandardTag(String.class, new StringType(), 3);
    }
    
    public void addStandardTag(Class reference, AbstractBaseType type, int identity) {
        addTagInstance(reference.getName(), new SimpleTagInstance(reference, type, IdentityInstance.getStandardIdentity(reference.getName(), identity)));
    }
    
    public Map<String, TagInterface> getTags() {
        return tagsByName;
    }

    public TagInterface getTag(String name) {
        return tagsByName.get(name);
    }

    public TagInterface getTag(int identity) {
        return tagsByIdentity.get(identity);
    }
    
    public TagInterface getTag(IdentityInstance identity) {
        return tagsByIdentity.get(identity.getIdentityKey());
    }
    
    public TagInterface getTag(Class cls) {
        return tagsByClass.get(cls);
    }

    public FormatDefinition getDefinition() {
        return definition;
    }

    public boolean isReady() {
        return ready;
    }

    public int getVersion() {
        return version;
    }

    // BINDING
    
    public TagInterface getTagInstanceFromObject(Object target) throws Exception {
        if (target == null) {
            return null;
        }
        BFLTag tag = target.getClass().getAnnotation(BFLTag.class);
        if (tag != null) {
            return getTag(tag.name());
        } else {
            TagInterface tf = tagsByClass.get(target.getClass());
            if (tf != null) {
                return tf;
            }
            throw new Exception("Passed in class did not have a @BinaryTag annotation");
        }
    }
    
    // Whole Object Read/Write
    
    public Object readObject(LimitedInputStream lir) throws Exception { 
        TagInterface tag = readTagHeader(lir);
        if (tag == null) {
            return null;
        }
        return tag.readContent(lir);
    }
    
    public void writeObject(OutputStream os, Object target) throws Exception {
        TagInterface tag = writeTagHeader(os, target);
        if (tag != null) {
            tag.writeContent(os, target);
        }
    }
    
    // Tag Pieces
    
    public TagInterface readTagHeader(LimitedInputStream lir) throws Exception {
        IdentityInstance identity = BMStreamUtils.readNullableIdentity(lir);
        if (identity == null) {
            return null;
        }
        return this.getTag(identity);
    }
    
    public TagInterface writeTagHeader(OutputStream os, Object target) throws Exception {
        final TagInterface tagInstance = getTag(target.getClass());
        if (tagInstance != null) {
            BMStreamUtils.writeNullableIdentity(os, tagInstance.getIdentity(), false);
        } else {
            BMStreamUtils.writeNullableIdentity(os);
        }
        return tagInstance;
    }
}

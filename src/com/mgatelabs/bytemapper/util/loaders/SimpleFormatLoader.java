package com.mgatelabs.bytemapper.util.loaders;

import java.util.HashMap;
import java.util.Map;

/**
 * This is a really basic loader i made to remove the Google Reflections dependency.
 *
 * If you want a better experience I would implement another sub-class of AbstractFormatLoader which will
 * search through specified class paths to build the results for the getClasses() call.
 *
 * Created by MiniMegaton on 1/2/14.
 */
public class SimpleFormatLoader extends AbstractFormatLoader {

    private Map<String, Class<?>> knownClasses;

    public SimpleFormatLoader() {
        knownClasses = new HashMap<String, Class<?>>();
    }

    public SimpleFormatLoader addKnownClass(String name, Class<?> reference) {
        knownClasses.put(name, reference);
        return this;
    }

    public SimpleFormatLoader addKnownClass(Class<?> reference) {
        return addKnownClass(reference.getSimpleName(), reference);
    }

    @Override
    public Map<String, Class<?>> getClasses() {
        return knownClasses;
    }
}

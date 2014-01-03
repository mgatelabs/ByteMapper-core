package com.mgatelabs.bytemapper.util.loaders;

import com.mgatelabs.bytemapper.support.instances.FormatInstance;
import com.mgatelabs.bytemapper.support.interfaces.FormatLoaderInterface;
import com.mgatelabs.bytemapper.support.io.FormatIO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by MiniMegaton on 1/2/14.
 */
public abstract class AbstractFormatLoader implements FormatLoaderInterface {
    public FormatInstance loadInstance(FormatIO info, int version) throws Exception {
        return new FormatInstance(info.getFormatDefinition(), getClasses(), version, info.getTypes());
    }

    /**
     *
     * @author MiniMegaton
     */
    public static class SearchFormatLoader extends AbstractFormatLoader{

        final List<String> searchPaths;

        public SearchFormatLoader() {
            this.searchPaths = new ArrayList<String>(2);
        }

        public SearchFormatLoader addSearchPath(String path) {
            searchPaths.add(path);
            return this;
        }

        @Override
        public Map<String, Class<?>> getClasses() {
            /*
            Map<String, Class<?>> name2Class = new HashMap<>();
            for (String path : searchPaths) {
                // Find all classes that have the special
                Reflections reflections = new Reflections(ClasspathHelper.forPackage(path));
                Set<Class<?>> searchResults = reflections.getTypesAnnotatedWith(BFLTag.class);

                for (Class<?> clz : searchResults) {
                    BFLTag bt = clz.getAnnotation(BFLTag.class);
                    if (!bt.skip()) {
                        name2Class.put(bt.name(), clz);
                    }
                }
            }

            return new FormatInstance(info.getFormatDefinition(), name2Class, version, info.getTypes());
            return name2class;
            */
            return null;
        }

        @Override
        public FormatInstance loadInstance(FormatIO info, int version) throws Exception {
            throw new Exception("Not implemented");
        }
    }
}

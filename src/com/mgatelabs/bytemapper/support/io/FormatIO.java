/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mgatelabs.bytemapper.support.io;

import com.mgatelabs.bytemapper.support.definitions.FormatDefinition;
import com.mgatelabs.bytemapper.support.factories.TypeFactory;
import com.mgatelabs.bytemapper.support.fields.types.AbstractBaseType;
import com.mgatelabs.bytemapper.support.instances.FormatInstance;
import com.mgatelabs.bytemapper.support.interfaces.FormatLoaderInterface;
import com.mgatelabs.bytemapper.support.interfaces.TagInterface;
import com.mgatelabs.bytemapper.support.io.streams.LimitedInputStream;
import com.mgatelabs.bytemapper.util.BMResult;
import com.mgatelabs.bytemapper.util.BMStreamUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author MiniMegaton
 */
public class FormatIO {

    TypeFactory types;
    final FormatLoaderInterface formatLoader;
    FormatDefinition formatDefinition;
    File formatFile;
    private Map<Integer, FormatInstance> instances;

    public static byte[] CONTENT_PREFIX = new byte[]{(byte) 'B', (byte) 'M', (byte) 'F'};

    // Constructors
    public FormatIO(FormatLoaderInterface formatLoader, File formatFile) {
        this(formatLoader, formatFile, new TypeFactory());
    }

    public FormatIO(FormatLoaderInterface formatLoader, File formatFile, TypeFactory types) {
        this.formatFile = formatFile;
        instances = new HashMap<>();
        this.types = types;
        this.formatLoader = formatLoader;
        this.formatDefinition = null;
    }

    public FormatIO init() throws Exception {
        formatDefinition = FormatDefinition.load(formatFile);
        return this;
    }

    public FormatIO init(InputStream is) throws Exception {
        formatDefinition = FormatDefinition.load(is);
        return this;
    }

    // Types
    public TypeFactory getTypes() {
        return types;
    }

    public void setTypes(TypeFactory types) {
        this.types = types;
    }

    public void addType(AbstractBaseType abt) {
        this.types.addType(abt);
    }

    public FormatDefinition getFormatDefinition() {
        return formatDefinition;
    }

    // Instance Loading
    private FormatInstance getFormatInstance(int version) throws Exception {
        FormatInstance fi = instances.get(version);
        if (fi == null) {
            fi = formatLoader.loadInstance(this, version);
            instances.put(version, fi);
        }
        return fi;
    }

    // Read/Write
    public BMResult load(File inputFile) throws Exception {
        long fileSize = inputFile.length();
        FileInputStream fis = new FileInputStream(inputFile);
        BufferedInputStream bis = new BufferedInputStream(fis, 2048);
        return load(inputFile, bis, 0, fileSize);
    }

    public BMResult load(File linkTo, InputStream is, long offset, long length) throws Exception {
        LimitedInputStream lir = new LimitedInputStream(is, offset, length, linkTo != null);
        try {

            for (byte desiredByte : CONTENT_PREFIX) {
                if (lir.read() != desiredByte) {
                    throw new Exception("Error, the provided input did not start with required prefix");
                }
            }

            // Read the version of the file
            final int fileVersion = BMStreamUtils.readVersion(lir);

            // Read the version of the content
            final int contentVersion = BMStreamUtils.readVersion(lir);

            // Get the format to match the version number
            FormatInstance fi = getFormatInstance(contentVersion);

            // Make sure we can link back to the current file
            types.initLink(linkTo);

            // Make sure we have a format instance to work with
            if (fi != null) {
                BMResult result = new BMResult(fi, linkTo);
                // Get the size of everything else, of -1, we have a null instance
                final int contentSize = BMStreamUtils.readNullableSize(lir);
                if (contentSize == -1) {
                    result.success(null, 0);
                } else {
                    LimitedInputStream contentInputStream = lir.spawn(contentSize);
                    TagInterface tag = fi.readTagHeader(contentInputStream);
                    if (tag != null) {
                        Object r = tag.readContent(lir);
                        result.success(r, tag.getIdentity().getIdentityKey());
                    } else {
                        result.success(null, 0);
                    }
                }
                return result;
            } else {
                throw new Exception("Could not load format instance for version " + contentVersion);
            }

        } finally {
            lir.close();
        }
    }

    public void save(File saveTo, Object target, int version) throws Exception {
        FileOutputStream fos = new FileOutputStream(saveTo);
        BufferedOutputStream bos = new BufferedOutputStream(fos, 1024);
        save(bos, target, version);
    }

    public void save(OutputStream os, Object target, int version) throws Exception {
        try {
            FormatInstance fi = getFormatInstance(version);
            // Clear any links
            types.initLink(null);

            // Identifier
            for (byte p: CONTENT_PREFIX) {
                os.write(p);
            }

            // File Version
            BMStreamUtils.writeVersion(os, 1);

            // Content Version
            BMStreamUtils.writeVersion(os, version);

            if (target == null) {
                BMStreamUtils.writeNullableSize(os);
            } else {
                ByteArrayOutputStream bufferedContent = new ByteArrayOutputStream(2048);
                fi.writeObject(bufferedContent, target);

                // The content's full size
                BMStreamUtils.writeNullableSize(os, bufferedContent.size());

                // Write out the tag's content
                os.write(bufferedContent.toByteArray());
            }
        } finally {
            os.flush();
            os.close();
        }
    }
}

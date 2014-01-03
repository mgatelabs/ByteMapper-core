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
import com.mgatelabs.bytemapper.util.BMStreamUtils;
import com.mgatelabs.bytemapper.util.FileLink;

import java.io.File;
import java.io.OutputStream;

/**
 *
 * @author MiniMegaton
 */
public class FileLinkType extends SimpleBaseType {
    
    public static String PARM_COMPRESS = "compress";
    
    private File loadLink;
    boolean compress;
    private FileLinkType parent;
    
    public FileLinkType() {
        super("FileLink", "File Link", "File Linker");
    }
    
    public FileLinkType(FileLinkType parent) {
        this();
        this.parent = parent;
    }

    @Override
    public void linkFile(File to) {
        loadLink = to;
    }

    @Override
    public AbstractBaseType getInstance(FormatInstance format, FieldInstance field) {
        FileLinkType newInstance = new FileLinkType(this);
        newInstance.setCompress("true".equals(field.getDefinition().getProperties().get(PARM_COMPRESS)));
        return newInstance;
    }

    @Override
    public void read(LimitedInputStream lir, FieldInterface instance, Object target) throws Exception {
        this.loadLink = parent.getLoadLink();
        // Get the nullable offset
        int contentLength = BMStreamUtils.readNullableSize(lir);
        if (contentLength == -1) {
            instance.setObjectValue(target, (FileLink) null);
        } else {
            FileLink link = new FileLink();
            link.linkToFile(loadLink, lir.getPosition(), contentLength, compress);
            instance.setObjectValue(target, (FileLink) link);
            lir.skip(contentLength);
        }
    }

    @Override
    public void write(OutputStream bos, FieldInterface instance, Object target) throws Exception {
        this.loadLink = parent.getLoadLink();
        FileLink insideTarget = (FileLink)instance.getObjectValue(target);
        if (insideTarget == null) {
            BMStreamUtils.writeNullableSize(bos, 0, true);
        } else {
            byte [] bytes;
            if (compress) {
                bytes = insideTarget.getCompressedBytes();
            } else {
                bytes = insideTarget.getBytes();
            }
            
            BMStreamUtils.writeNullableSize(bos, bytes.length, false);
            bos.write(bytes);
        }
    }

    public boolean isCompress() {
        return compress;
    }

    public void setCompress(boolean compress) {
        this.compress = compress;
    }

    public File getLoadLink() {
        return loadLink;
    }
}

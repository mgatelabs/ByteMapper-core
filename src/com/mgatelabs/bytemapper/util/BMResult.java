/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mgatelabs.bytemapper.util;

import com.mgatelabs.bytemapper.support.instances.FormatInstance;

import java.io.File;

/**
 * After you make a load request, the result of that operation will be returned with this instance.
 * @author MiniMegaton
 */
public class BMResult {
    private final FormatInstance formatInstance;
    private final File inputFile;
    boolean ready;
    private String message;
    private Object objectInstance;
    private int objectIdentity;

    public BMResult(FormatInstance formatInstance, File inputFile) {
        this.formatInstance = formatInstance;
        this.inputFile = inputFile;
        this.ready = false;
        message = null;
        objectIdentity = -1;
        objectInstance = null;
    }
    
    public BMResult(String message, File inputFile) {
        this.formatInstance = null;
        this.inputFile = inputFile;
        this.ready = false;
        this.message = message;
        objectIdentity = -1;
        objectInstance = null;
    }

    public void success(Object instance, int identity) {
        objectInstance = instance;
        objectIdentity = identity;
        ready = true;
    }
    
    public void failure(String message) {
        this.message = message;
    }
    
    public File getInputFile() {
        return inputFile;
    }
    
    public FormatInstance getFormatInstance() {
        return formatInstance;
    }

    public boolean isReady() {
        return ready;
    }

    public String getMessage() {
        return message;
    }

    public Object getObjectInstance() {
        return objectInstance;
    }

    public int getObjectIdentity() {
        return objectIdentity;
    }
}

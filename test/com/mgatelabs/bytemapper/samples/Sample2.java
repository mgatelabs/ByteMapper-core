/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mgatelabs.bytemapper.samples;

import com.mgatelabs.bytemapper.util.BFLTag;

/**
 * This is a sample class, not for real use
 * @author MiniMegaton
 */
@BFLTag(name = "Sample2")
public class Sample2 {
    
    private Sample1 object1;
    private int int1;
    private long long1;
    private String string1;
    private byte [] bytes;

    public Sample2() {
    }

    
    
    public Sample1 getObject1() {
        return object1;
    }

    public void setObject1(Sample1 object1) {
        this.object1 = object1;
    }

    public int getInt1() {
        return int1;
    }

    public void setInt1(int int1) {
        this.int1 = int1;
    }

    public long getLong1() {
        return long1;
    }

    public void setLong1(long long1) {
        this.long1 = long1;
    }

    public String getString1() {
        return string1;
    }

    public void setString1(String string1) {
        this.string1 = string1;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}

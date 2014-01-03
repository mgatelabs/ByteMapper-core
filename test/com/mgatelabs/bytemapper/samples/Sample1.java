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
@BFLTag(name = "Sample1")
public class Sample1 {
    private int int1;
    private int int2;
    private int int3;
    private int int4;
    private int int5;

    public Sample1() {
    }

    public int getInt1() {
        return int1;
    }

    public void setInt1(int int1) {
        this.int1 = int1;
    }

    public int getInt2() {
        return int2;
    }

    public void setInt2(int int2) {
        this.int2 = int2;
    }

    public int getInt3() {
        return int3;
    }

    public void setInt3(int int3) {
        this.int3 = int3;
    }

    public int getInt4() {
        return int4;
    }

    public void setInt4(int int4) {
        this.int4 = int4;
    }

    public int getInt5() {
        return int5;
    }

    public void setInt5(int int5) {
        this.int5 = int5;
    }

    @Override
    public String toString() {
        return "Sample1{" + "int1=" + int1 + ", int2=" + int2 + ", int3=" + int3 + ", int4=" + int4 + ", int5=" + int5 + '}';
    }
}

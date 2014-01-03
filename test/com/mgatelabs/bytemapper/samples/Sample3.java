/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mgatelabs.bytemapper.samples;

import com.mgatelabs.bytemapper.util.BFLTag;
import com.mgatelabs.bytemapper.util.FileLink;

/**
 * This is a sample class, not for real use
 * @author MiniMegaton
 */
@BFLTag(name = "Sample3")
public class Sample3 {
    private FileLink link1;
    private FileLink link2;
    private FileLink link3;
    private String string1;
    private String string2;
    private String string3;

    public FileLink getLink1() {
        return link1;
    }

    public void setLink1(FileLink link1) {
        this.link1 = link1;
    }

    public FileLink getLink2() {
        return link2;
    }

    public void setLink2(FileLink link2) {
        this.link2 = link2;
    }

    public FileLink getLink3() {
        return link3;
    }

    public void setLink3(FileLink link3) {
        this.link3 = link3;
    }

    public String getString1() {
        return string1;
    }

    public void setString1(String string1) {
        this.string1 = string1;
    }

    public String getString2() {
        return string2;
    }

    public void setString2(String string2) {
        this.string2 = string2;
    }

    public String getString3() {
        return string3;
    }

    public void setString3(String string3) {
        this.string3 = string3;
    }
}

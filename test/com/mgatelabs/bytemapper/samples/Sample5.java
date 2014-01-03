/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mgatelabs.bytemapper.samples;

import com.mgatelabs.bytemapper.util.BFLTag;

import java.util.List;

/**
 * This is a sample class, not for real use
 * @author MiniMegaton
 */
@BFLTag(name = "Sample5")
public class Sample5 {
    
    private List list1;
    private List<Integer> list2;
    private List<Sample4> list3;

    public List getList1() {
        return list1;
    }

    public void setList1(List list1) {
        this.list1 = list1;
    }

    public List<Integer> getList2() {
        return list2;
    }

    public void setList2(List<Integer> list2) {
        this.list2 = list2;
    }

    public List<Sample4> getList3() {
        return list3;
    }

    public void setList3(List<Sample4> list3) {
        this.list3 = list3;
    } 
}

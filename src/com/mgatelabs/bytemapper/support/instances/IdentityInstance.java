/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mgatelabs.bytemapper.support.instances;

/**
 *
 * @author MiniMegaton
 */
public class IdentityInstance {
    private int identity;
    private String name;
    private boolean custom;

    public IdentityInstance(int identity, boolean custom) {
        this.name = "unknown";
        this.identity = identity;
        this.custom = custom;
    }
    
    public IdentityInstance(String name, int identity, boolean custom) {
        this.name = name;
        this.identity = identity;
        this.custom = custom;
    }
    
    public static IdentityInstance getCustomIdentity(String name, int identity) {
        return new IdentityInstance(name, identity, true);
    }
    
    public static IdentityInstance getStandardIdentity(String name, int identity) {
        return new IdentityInstance(name, identity, false);
    }
    
    public int getIdentityKey() {
        return custom ? identity : -identity;
    }
    
    public int getIdentity() {
        return identity;
    }

    public String getName() {
        return name;
    }

    public boolean isCustom() {
        return custom;
    }

    public void setCustom(boolean custom) {
        this.custom = custom;
    }
    
}

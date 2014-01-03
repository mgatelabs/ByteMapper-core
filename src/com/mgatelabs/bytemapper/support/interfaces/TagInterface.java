/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mgatelabs.bytemapper.support.interfaces;

import com.mgatelabs.bytemapper.support.instances.IdentityInstance;
import com.mgatelabs.bytemapper.support.io.streams.LimitedInputStream;

import java.io.OutputStream;

/**
 *
 * @author MiniMegaton
 */
public interface TagInterface {
    
    public Object getClassInstance() throws Exception;
    
    public Class getClassReference();
    
    public IdentityInstance getIdentity();
    
    public Object readContent(LimitedInputStream lir, boolean isNullable) throws Exception;

    public void writeContent(OutputStream os, Object target, boolean iNullable) throws Exception;
}

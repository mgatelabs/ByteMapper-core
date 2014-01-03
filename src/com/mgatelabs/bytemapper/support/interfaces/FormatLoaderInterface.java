/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mgatelabs.bytemapper.support.interfaces;

import com.mgatelabs.bytemapper.support.instances.FormatInstance;
import com.mgatelabs.bytemapper.support.io.FormatIO;

import java.util.Map;

/**
 *
 * @author MiniMegaton
 */
public interface FormatLoaderInterface {
    public Map<String, Class<?>> getClasses();
    public FormatInstance loadInstance(FormatIO info, int version) throws Exception;
}

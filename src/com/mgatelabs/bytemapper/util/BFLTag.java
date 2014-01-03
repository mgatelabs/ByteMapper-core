/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mgatelabs.bytemapper.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This is a basic annotation you could apply to classes in order to get more information out of them.
 * @author MiniMegaton
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface BFLTag {
    String name();
    boolean skip() default false;
}

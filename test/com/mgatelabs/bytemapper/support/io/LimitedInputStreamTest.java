/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mgatelabs.bytemapper.support.io;

import com.mgatelabs.bytemapper.support.io.streams.LimitedInputStream;
import junit.framework.TestCase;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;

/**
 *
 * @author MiniMegaton
 */
public class LimitedInputStreamTest extends TestCase {
    
    public LimitedInputStreamTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    
    /**
     * Test of forward method, of class LimitedInputReader.
     */
    public void testForward() throws Exception {
        System.out.println("forward");
        
        byte [] sample = new byte [] {(byte)'h',(byte)'e', (byte)'l', (byte)'l', (byte)'o'};
        ByteArrayInputStream bais = new ByteArrayInputStream(sample);
        BufferedInputStream bis = new BufferedInputStream(bais);
        LimitedInputStream topLevel = new LimitedInputStream(bis, 0, sample.length);
        topLevel.skip(3);
        LimitedInputStream spawn = topLevel.spawn(2);
        spawn.forward();
        
        assertEquals(3, spawn.getOffset());
        assertEquals(5, spawn.getPosition());
        assertEquals(5, topLevel.getPosition());
    }

    /**
     * Test of skip method, of class LimitedInputReader.
     */
    public void testSkip() throws Exception {
        System.out.println("skip");
        
        byte [] sample = new byte [] {(byte)'h',(byte)'e', (byte)'l', (byte)'l', (byte)'o'};
        ByteArrayInputStream bais = new ByteArrayInputStream(sample);
        BufferedInputStream bis = new BufferedInputStream(bais);
        LimitedInputStream topLevel = new LimitedInputStream(bis, 0, sample.length);
        topLevel.skip(3);
        LimitedInputStream spawn = topLevel.spawn(2);
        spawn.skip(2);
        
        assertEquals(3, spawn.getOffset());
        assertEquals(5, spawn.getPosition());
        assertEquals(5, topLevel.getPosition());
    }

    /**
     * Test of getOffset method, of class LimitedInputReader.
     */
    public void testGetOffset() throws Exception {
        System.out.println("getOffset");
        
        byte [] sample = new byte [] {(byte)'h',(byte)'e', (byte)'l', (byte)'l', (byte)'o'};
        ByteArrayInputStream bais = new ByteArrayInputStream(sample);
        BufferedInputStream bis = new BufferedInputStream(bais);
        LimitedInputStream topLevel = new LimitedInputStream(bis, 0, sample.length);
        topLevel.skip(3);
        LimitedInputStream spawn = topLevel.spawn(2);
        
        assertEquals(3, spawn.getOffset());
    }

    /**
     * Test of getPosition method, of class LimitedInputReader.
     */
    public void testGetPosition() throws Exception {
        System.out.println("getPosition");
        
        byte [] sample = new byte [] {(byte)'h',(byte)'e', (byte)'l', (byte)'l', (byte)'o'};
        ByteArrayInputStream bais = new ByteArrayInputStream(sample);
        BufferedInputStream bis = new BufferedInputStream(bais);
        LimitedInputStream topLevel = new LimitedInputStream(bis, 0, sample.length);
        LimitedInputStream spawn = topLevel.spawn(3);
        spawn.skip(3);
        
        assertEquals(topLevel.getPosition(), spawn.getPosition());
    }
    
}

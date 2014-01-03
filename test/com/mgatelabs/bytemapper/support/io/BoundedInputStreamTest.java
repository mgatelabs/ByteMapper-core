/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mgatelabs.bytemapper.support.io;

import com.mgatelabs.bytemapper.support.io.streams.BoundedInputStream;
import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 *
 * @author MiniMegaton
 */
public class BoundedInputStreamTest extends TestCase {
    
    
    private byte [] data;
    
    public BoundedInputStreamTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        data = new byte[]{0,1,2,3,4,5,6,7,8,9,10,11,12};
    }
    
    private InputStream getInputStream() {
        return new ByteArrayInputStream(data);
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of read method, of class BoundedInputStream.
     */
    public void testRead() throws Exception {
        System.out.println("read (1 byte)");
        int count;
        BoundedInputStream instance;
        
        instance = new BoundedInputStream(getInputStream(), 0, data.length);
        count = 0;
        while (instance.read() != -1) {
            count++;
        }
        assertEquals(data.length, count);
        
        instance = new BoundedInputStream(getInputStream(), 0, data.length);
        assertEquals(0, instance.read());
        assertEquals(1, instance.read());
        assertEquals(2, instance.read());
        assertEquals(3, instance.read());
        assertEquals(4, instance.read());
        assertEquals(5, instance.read());
        assertEquals(6, instance.read());
        assertEquals(7, instance.read());
        assertEquals(8, instance.read());
        assertEquals(9, instance.read());
        assertEquals(10, instance.read());
        assertEquals(11, instance.read());
        assertEquals(12, instance.read());
        assertEquals(-1, instance.read());
        
        instance = new BoundedInputStream(getInputStream(), 6, 6);
        count = 0;
        while (instance.read() != -1) {
            count++;
        }
        assertEquals(6, count);
        
        instance = new BoundedInputStream(getInputStream(), 6, 6);
        assertEquals(6, instance.read());
        assertEquals(7, instance.read());
        assertEquals(8, instance.read());
        assertEquals(9, instance.read());
        assertEquals(10, instance.read());
        assertEquals(11, instance.read());
        assertEquals(-1, instance.read());
    }

    /**
     * Test of read method, of class BoundedInputStream.
     */
    public void testRead_byteArr() throws Exception {
        System.out.println("read (byte [])");
        BoundedInputStream instance;
        int v;
        
        instance = new BoundedInputStream(getInputStream(), 0, data.length);
        assertEquals(data.length, instance.read(new byte[20]));
        
        instance = new BoundedInputStream(getInputStream(), 6, 6);
        assertEquals(6, instance.read(new byte[20]));
        
        
        instance = new BoundedInputStream(getInputStream(), 0, 6);
        
        v = instance.read(new byte[4]); 
        assertEquals(4, v);
        v = instance.read(new byte[4]); 
        assertEquals(2, v);
    }

    /**
     * Test of read method, of class BoundedInputStream.
     */
    public void testRead_3args() throws Exception {
        System.out.println("read (byte [], i, i2)");
        
        BoundedInputStream instance;
        int v;
        byte [] bytes;
        
        instance = new BoundedInputStream(getInputStream(), 0, data.length);
        assertEquals(data.length, instance.read(new byte[20]));
        
        instance = new BoundedInputStream(getInputStream(), 6, 6);
        assertEquals(6, instance.read(new byte[20], 0, 6));
        
        instance = new BoundedInputStream(getInputStream(), 0, 6);
        bytes = new byte[4];
        v = instance.read(bytes, 0, 4); 
        assertEquals(4, v);
        assertEquals(0, bytes[0]);
        assertEquals(1, bytes[1]);
        assertEquals(2, bytes[2]);
        assertEquals(3, bytes[3]);
        bytes = new byte[4];
        v = instance.read(bytes, 2, 2); 
        assertEquals(2, v);
        assertEquals(4, bytes[2]);
        assertEquals(5, bytes[3]);
        
        instance = new BoundedInputStream(getInputStream(), 6, 6);
        bytes = new byte[4];
        v = instance.read(bytes, 0, 4); 
        assertEquals(4, v);
        assertEquals(6, bytes[0]);
        assertEquals(7, bytes[1]);
        assertEquals(8, bytes[2]);
        assertEquals(9, bytes[3]);
        bytes = new byte[4];
        v = instance.read(bytes, 2, 2); 
        assertEquals(2, v);
        assertEquals(10, bytes[2]);
        assertEquals(11, bytes[3]);
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mgatelabs.bytemapper;

import com.mgatelabs.bytemapper.samples.*;
import com.mgatelabs.bytemapper.support.interfaces.FormatLoaderInterface;
import com.mgatelabs.bytemapper.support.io.FormatIO;
import com.mgatelabs.bytemapper.util.BMResult;
import com.mgatelabs.bytemapper.util.FileLink;
import com.mgatelabs.bytemapper.util.loaders.SimpleFormatLoader;
import junit.framework.TestCase;

import java.io.File;
import java.util.*;

/**
 *
 * @author MiniMegaton
 */
public class TestSampleFormats extends TestCase {
    
    private FormatIO fio;
    
    public TestSampleFormats(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // Link up classes
        FormatLoaderInterface fl = new SimpleFormatLoader()
                .addKnownClass(Sample1.class)
                .addKnownClass(Sample2.class)
                .addKnownClass(Sample3.class)
                .addKnownClass(Sample4.class)
                .addKnownClass(Sample5.class)
                .addKnownClass(Sample6.class)
                .addKnownClass(Sample7.class)
                ;
        // Make the new format
        fio = new FormatIO(fl, new File("sample.js")).init();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    public void testBasicIntTag() throws Exception {
        
        System.out.println("Write1");
        
        Sample1 sample = new Sample1();
        sample.setInt1(0xF);
        sample.setInt2(0xFF);
        sample.setInt3(0xFFF);
        sample.setInt4(0xFFFF);
        sample.setInt5(0xFFFFF);
        
        fio.save(new File("sample1.out"), sample, 1);
        
        System.out.println("Read1");
        
        BMResult fr = fio.load(new File("sample1.out"));
        
        assertNotNull(fr);
        assertTrue(fr.isReady());
        assertEquals(1, fr.getObjectIdentity());
        assertNotNull(fr.getObjectInstance());
        
        Sample1 o = (Sample1) fr.getObjectInstance();
        
        assertEquals(o.getInt1(), 0x0F);
        assertEquals(o.getInt2(), 0x0FF);
        assertEquals(o.getInt3(), 0x0FFF);
        assertEquals(o.getInt4(), 0x0FFFF);
        assertEquals(o.getInt5(), 0x0FFFFF);  
    }
    
    public void testNestedTags() throws Exception {
        
        System.out.println("Write2");
        
        Sample1 sample = new Sample1();
        sample.setInt1(0xF);
        sample.setInt2(0xFF);
        sample.setInt3(0xFFF);
        sample.setInt4(0xFFFF);
        sample.setInt5(0xFFFFF);
        
        Sample2 sample2 = new Sample2();
        sample2.setInt1(0xFF);
        sample2.setLong1(0xFFFFFF);
        sample2.setString1("Hello World");
        sample2.setBytes(sample2.getString1().getBytes("UTF-8"));
        sample2.setObject1(sample);
        
        fio.save(new File("sample2.out"), sample2, 1);
        
        System.out.println("Read2");
        
        BMResult fr = fio.load(new File("sample2.out"));
        
        assertNotNull(fr);
        assertTrue(fr.isReady());
        assertEquals(fr.getObjectIdentity(), 2);
        assertNotNull(fr.getObjectInstance());
        
        Sample2 o = (Sample2)fr.getObjectInstance();
        
        assertNotNull(o);
        
        assertEquals(o.getInt1(), 0xFF);
        assertEquals(o.getLong1(), 0xFFFFFF);
        assertEquals(o.getString1(), "Hello World");
        assertTrue(Arrays.equals(o.getBytes(), "Hello World".getBytes("UTF-8")));
        
        assertEquals(o.getObject1().getInt1(), 0x0F);
        assertEquals(o.getObject1().getInt2(), 0x0FF);
        assertEquals(o.getObject1().getInt3(), 0x0FFF);
        assertEquals(o.getObject1().getInt4(), 0x0FFFF);
        assertEquals(o.getObject1().getInt5(), 0x0FFFFF);  
    }

    public void testBytesString() throws Exception {
        
        System.out.println("Write3");
        
        Sample2 sample2 = new Sample2();
        sample2.setInt1(0xFF);
        sample2.setLong1(0xFFFFFFFF);
        sample2.setString1("Hello World");
        sample2.setBytes(sample2.getString1().getBytes("UTF-8"));
        sample2.setObject1(null);
        
        fio.save(new File("sample3.out"), sample2, 1);
        
        System.out.println("Read3");
        
        BMResult fr = fio.load(new File("sample3.out"));
        
        assertNotNull(fr);
        assertTrue(fr.isReady());
        assertEquals(fr.getObjectIdentity(), 2);
        assertNotNull(fr.getObjectInstance());
        
        Sample2 o = (Sample2)fr.getObjectInstance();
        
        assertNotNull(o);
        
        assertEquals(o.getInt1(), 0xFF);
        assertEquals(o.getLong1(), 0xFFFFFFFF);
        assertEquals(o.getString1(), "Hello World");
        assertTrue(Arrays.equals(o.getBytes(), "Hello World".getBytes("UTF-8")));
        assertNull(o.getObject1());

    }

    public void testNulls() throws Exception {
        
        System.out.println("Write4");
        
        Sample2 sample2 = new Sample2();
        sample2.setInt1(0xFF);
        sample2.setLong1(0xFFFFFFF);
        sample2.setString1(null);
        sample2.setBytes(null);
        sample2.setObject1(null);
        
        fio.save(new File("sample4.out"), sample2, 1);
        
        System.out.println("Read4");
        
        BMResult fr = fio.load(new File("sample4.out"));
        
        assertNotNull(fr);
        assertTrue(fr.isReady());
        assertEquals(fr.getObjectIdentity(), 2);
        assertNotNull(fr.getObjectInstance());
        
        Sample2 o = (Sample2)fr.getObjectInstance();
        
        assertNotNull(o);
        
        assertEquals(o.getInt1(), 0xFF);
        assertEquals(o.getLong1(), 0xFFFFFFF);
        assertNull(o.getString1());
        assertNull(o.getBytes());
        assertNull(o.getObject1());

    }
    
    public void testNullObject() throws Exception {
        
        System.out.println("Write5");
        
        fio.save(new File("sample5.out"), null, 1);
        
        System.out.println("Read5");
        
        BMResult fr = fio.load(new File("sample5.out"));
        
        assertNotNull(fr);
        assertTrue(fr.isReady());
        assertEquals(fr.getObjectIdentity(), 0);
        assertNull(fr.getObjectInstance());
    }
    
    public void testFileLink() throws Exception {
        
        System.out.println("Write6");
        
        Sample3 sample = new Sample3();
        sample.setLink1(new FileLink().linkToBlob(new byte[]{'1','2','3','4','5','6','7','8','9'}));
        sample.setLink2(new FileLink().linkToFile(new File("123456789.txt")));
        
        sample.setString1("123456789");
        sample.setString2("abcdefghi");
        
        fio.save(new File("sample6.out"), sample, 1);
        
        System.out.println("Read6");
        
        BMResult fr = fio.load(new File("sample6.out"));
        byte [] bytes;
        
        assertNotNull(fr);
        assertTrue(fr.isReady());
        assertEquals(fr.getObjectIdentity(), 3);
        assertNotNull(fr.getObjectInstance());
        
        sample = (Sample3)fr.getObjectInstance();
        
        assertEquals(9, sample.getLink1().getLength());
        assertEquals(9, sample.getLink2().getLength());
        assertTrue(sample.getLink1().isLinked());
        assertTrue(sample.getLink2().isLinked());
        assertNull(sample.getLink3());
        
        bytes = sample.getLink1().getBytes();
        assertEquals('1', bytes[0]);
        assertEquals('2', bytes[1]);
        assertEquals('3', bytes[2]);
        assertEquals('4', bytes[3]);
        assertEquals('5', bytes[4]);
        assertEquals('6', bytes[5]);
        assertEquals('7', bytes[6]);
        assertEquals('8', bytes[7]);
        assertEquals('9', bytes[8]);
        
        bytes = sample.getLink2().getBytes();
        assertEquals('1', bytes[0]);
        assertEquals('2', bytes[1]);
        assertEquals('3', bytes[2]);
        assertEquals('4', bytes[3]);
        assertEquals('5', bytes[4]);
        assertEquals('6', bytes[5]);
        assertEquals('7', bytes[6]);
        assertEquals('8', bytes[7]);
        assertEquals('9', bytes[8]);
        
        assertEquals(sample.getString1(), "123456789");
        assertEquals(sample.getString2(), "abcdefghi");
        assertNull(sample.getString3());
    }
    
    public void testNullFileLinks() throws Exception {
        
        System.out.println("Write7");
        
        Sample3 sample = new Sample3();
        sample.setLink3(new FileLink().linkToBlob(new byte[]{'1','2','3','4','5','6','7','8','9'}));
        
        fio.save(new File("sample7.out"), sample, 1);
        
        System.out.println("Read7");
        
        BMResult fr = fio.load(new File("sample7.out"));
        byte [] bytes;
        
        assertNotNull(fr);
        assertTrue(fr.isReady());
        assertEquals(fr.getObjectIdentity(), 3);
        assertNotNull(fr.getObjectInstance());
        
        sample = (Sample3)fr.getObjectInstance();
        
        assertNull(sample.getLink1());
        assertNull(sample.getLink2());
        assertNotNull(sample.getLink3());
        assertNull(sample.getString1());
        assertNull(sample.getString2());
        assertNull(sample.getString3());
        
        bytes = sample.getLink3().getBytes();
        assertEquals('1', bytes[0]);
        assertEquals('2', bytes[1]);
        assertEquals('3', bytes[2]);
        assertEquals('4', bytes[3]);
        assertEquals('5', bytes[4]);
        assertEquals('6', bytes[5]);
        assertEquals('7', bytes[6]);
        assertEquals('8', bytes[7]);
        assertEquals('9', bytes[8]);   
        assertEquals(9, bytes.length);
    }
    
    public void testFileLinkCompression() throws Exception {
        System.out.println("Write8 - Test Compression");
        
        Sample3 sample = new Sample3();
        sample.setLink3(new FileLink().linkToFile(new File("big.txt")));
        
        fio.save(new File("sample8.out"), sample, 1);
    }
    
    public void testFileLinkNoCompression() throws Exception {
        System.out.println("Write9 - Test With Out Compression");
        
        Sample3 sample = new Sample3();
        sample.setLink2(new FileLink().linkToFile(new File("big.txt")));
        
        fio.save(new File("sample9.out"), sample, 1);
    }
    
    public void testIntegerLong1() throws Exception {
        Sample4 sample;
        
        System.out.println("Write10");
        
        sample = new Sample4();
        sample.setInt1(1);
        sample.setInt2(new Integer(1));
        sample.setLong1(1);
        sample.setLong2(new Long(1));
        
        fio.save(new File("sample10.out"), sample, 1);
        
        System.out.println("Read10");
        
        BMResult fr = fio.load(new File("sample10.out"));
        
        assertNotNull(fr);
        assertTrue(fr.isReady());
        assertEquals(fr.getObjectIdentity(), 4);
        assertNotNull(fr.getObjectInstance());
        
        sample = (Sample4)fr.getObjectInstance();
        
        assertNotNull(sample.getInt2());
        assertNotNull(sample.getLong2());

        assertEquals(1, sample.getInt1());
        assertEquals(new Integer(1), sample.getInt2());
        assertEquals(1, sample.getLong1());
        assertEquals(new Long(1), sample.getLong2());
    }
    
    public void testIntegerLong2() throws Exception {
        Sample4 sample;
        
        System.out.println("Write11");
        
        sample = new Sample4();
        sample.setInt1(1);
        sample.setInt2(new Integer(2));
        sample.setLong1(3);
        sample.setLong2(new Long(4));
        
        fio.save(new File("sample11.out"), sample, 1);
        
        System.out.println("Read11");
        
        BMResult fr = fio.load(new File("sample11.out"));
        
        assertNotNull(fr);
        assertTrue(fr.isReady());
        assertEquals(fr.getObjectIdentity(), 4);
        assertNotNull(fr.getObjectInstance());
        
        sample = (Sample4)fr.getObjectInstance();
        
        assertNotNull(sample.getInt2());
        assertNotNull(sample.getLong2());

        assertEquals(1, sample.getInt1());
        assertEquals(new Integer(2), sample.getInt2());
        assertEquals(3, sample.getLong1());
        assertEquals(new Long(4), sample.getLong2());
    }
    
    public void testIntegerLong3() throws Exception {
        Sample4 sample;
        
        System.out.println("Write12");
        
        sample = new Sample4();
        sample.setInt1(1);
        sample.setInt2(null);
        sample.setLong1(3);
        sample.setLong2(null);
        
        fio.save(new File("sample12.out"), sample, 1);
        
        System.out.println("Read12");
        
        BMResult fr = fio.load(new File("sample12.out"));
        
        assertNotNull(fr);
        assertTrue(fr.isReady());
        assertEquals(fr.getObjectIdentity(), 4);
        assertNotNull(fr.getObjectInstance());
        
        sample = (Sample4)fr.getObjectInstance();
        
        assertNull(sample.getInt2());
        assertNull(sample.getLong2());

        assertEquals(1, sample.getInt1());
        assertEquals(3, sample.getLong1());
    }
    
    public void testNullList() throws Exception {
        Sample5 s5;
        int index = 13;
        
        System.out.println("Write" + index);
        
        s5 = new Sample5();
        s5.setList1(null);
        s5.setList2(null);
        s5.setList3(null);
        
        fio.save(new File("sample"+index+".out"), s5, 1);
        
        System.out.println("Read" + index);
        
        BMResult fr = fio.load(new File("sample"+index+".out"));
        
        assertNotNull(fr);
        assertTrue(fr.isReady());
        assertEquals(fr.getObjectIdentity(), 5);
        assertNotNull(fr.getObjectInstance());
        
        s5 = (Sample5) fr.getObjectInstance();
        
        assertNull(s5.getList1());
        assertNull(s5.getList2());
        assertNull(s5.getList3());
    }
    
    public void testFilledLists() throws Exception {
        Sample5 s5;
        Sample4 s4;
        int index = 14;
        
        System.out.println("Write" + index);
        
        s5 = new Sample5();
        s5.setList1(new ArrayList(5));
        s5.setList2(new ArrayList<Integer>(1));
        s5.setList3(new ArrayList<Sample4>(1));
 
        s4 = new Sample4();
        s4.setInt1(1);
        s4.setInt2(new Integer(2));
        s4.setLong1(3);
        s4.setLong2(new Long(4));
        
        s5.getList1().add("Hello World");
        s5.getList2().add(new Integer(1));
        s5.getList3().add(s4);
        
        fio.save(new File("sample"+index+".out"), s5, 1);
        
        System.out.println("Read" + index);
        
        BMResult fr = fio.load(new File("sample"+index+".out"));
        
        assertNotNull(fr);
        assertTrue(fr.isReady());
        assertEquals(fr.getObjectIdentity(), 5);
        assertNotNull(fr.getObjectInstance());
        
        s5 = (Sample5) fr.getObjectInstance();
        
        assertNotNull(s5.getList1());
        assertNotNull(s5.getList2());
        assertNotNull(s5.getList3());
        
        assertEquals(1, s5.getList1().size());
        assertEquals(1, s5.getList2().size());
        assertEquals(1, s5.getList3().size());
        
        assertEquals("Hello World", s5.getList1().get(0));
        
        assertEquals(new Integer(1), s5.getList2().get(0));
        
        s4 = s5.getList3().get(0);
        
        assertEquals(1, s4.getInt1());
        assertEquals(new Integer(2), s4.getInt2());
        assertEquals(3, s4.getLong1());
        assertEquals(new Long(4), s4.getLong2());
    }
    
    public void testEmptyLists() throws Exception {
        Sample5 s5;
        int index = 15;
        
        System.out.println("Write" + index);
        
        s5 = new Sample5();
        s5.setList1(new ArrayList(5));
        s5.setList2(new ArrayList<Integer>(1));
        s5.setList3(new ArrayList<Sample4>(1));
        
        fio.save(new File("sample"+index+".out"), s5, 1);
        
        System.out.println("Read" + index);
        
        BMResult fr = fio.load(new File("sample"+index+".out"));
        
        assertNotNull(fr);
        assertTrue(fr.isReady());
        assertEquals(fr.getObjectIdentity(), 5);
        assertNotNull(fr.getObjectInstance());
        
        s5 = (Sample5) fr.getObjectInstance();
        
        assertNotNull(s5.getList1());
        assertNotNull(s5.getList2());
        assertNotNull(s5.getList3());
        
        assertEquals(0, s5.getList1().size());
        assertEquals(0, s5.getList2().size());
        assertEquals(0, s5.getList3().size());
    }

    public void testEnum1() throws Exception {
        Sample6 s6;
        int index = 16;

        System.out.println("Write" + index);

        s6 = new Sample6();
        s6.setEnum1(SampleEnum1.ALPHA);
        s6.setEnum2(SampleEnum1.BETA);
        s6.setEnum3(null);

        fio.save(new File("sample"+index+".out"), s6, 1);

        System.out.println("Read" + index);

        BMResult fr = fio.load(new File("sample"+index+".out"));

        assertNotNull(fr);
        assertTrue(fr.isReady());
        assertEquals(6, fr.getObjectIdentity());
        assertNotNull(fr.getObjectInstance());

        s6 = (Sample6) fr.getObjectInstance();

        assertNotNull(s6.getEnum1());
        assertNotNull(s6.getEnum2());
        assertNull(s6.getEnum3());

        assertEquals(SampleEnum1.ALPHA, s6.getEnum1());
        assertEquals(SampleEnum1.BETA, s6.getEnum2());
    }

    public void testDate1() throws Exception {
        Sample7 s7;
        int index = 17;

        System.out.println("Write" + index);

        s7 = new Sample7();
        s7.setD1(new Date());
        s7.setD2(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTime());
        s7.setD3(null);

        fio.save(new File("sample"+index+".out"), s7, 1);

        System.out.println("Read" + index);

        BMResult fr = fio.load(new File("sample"+index+".out"));

        assertNotNull(fr);
        assertTrue(fr.isReady());
        assertEquals(7, fr.getObjectIdentity());
        assertNotNull(fr.getObjectInstance());

        Sample7 s7b = (Sample7) fr.getObjectInstance();

        assertNotNull(s7b.getD1());
        assertNotNull(s7b.getD2());
        assertNull(s7b.getD3());

        assertEquals(s7.getD1(), s7b.getD1());
        assertEquals(s7.getD2(), s7b.getD2());
    }
}

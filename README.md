Byte Mapper (Core)
============

Byte Mapper, BM, is a basic mapper to read/write POJO instances to a very compressed binary file format.  While working on another project, I originally was going to use Jackson to read/write POJO to JSON/XMl, but after looking at the overhead of saving large byte arrays I came up with this binary mapper, which is nowhere as powerful as Jackson.  The format comes with additional limitations and restrictions which are needed to keep track of multiple file formats, since binary formats are very fickle and need strict rule sets or they are just become a collection of random bytes.

I would not use this right now, its still under early alpha development.

Format Design
------------

The format, Byte Mapper Format, BMF, is based upon the SWF file format, which I did extensive research into many years ago.  It shares some of the same concepts such as tags and binary tricks to reduce size.  The one major difference between SWF and BMF is SWF will go the extra step of reading and writing at the bit level, which creates a whole other host of problems, but allows them to shrink files down even further.  BMF instead focuses on keeping the format bound to bytes, so there is no need to invent a bit input/output stream reader.

The one major design choice you will see is shrinking integer values and dealing with null values.  For example integers can be represented by a maximum of 4 bytes.  I could just write out all four bytes, but that would be wasteful for any value less than 0x00FFFFFF.  To shrink integers a definition byte has been included before the actual byte values, which indicates the number of bytes to follow and additional options.

Format Definition  
------------

Supported POJO Field Types
------------

**Primitive types**

* int
* long (lng)

**Instance Types**

* Integer
* Long
* String
* byte [] (blob)

**Special Types**

* class (tag or tag/tagName)
* list (list or list/tagName)
* filelink
* enum

Required 3rd Party Libraries
------------

* jackson-annotations (Tested with 2.2.3)
* jackson-core (Tested with 2.2.3)
* jackson-databind (Tested with 2.2.3)

* junit (Tested with 3.8.1)

Limitations and quirks
------------

**FileLink type**

The FileLink is basically a byte [] field that is not loaded into memory.  You may ask, why not load everything?  Sometime you don’t need fifteen megabytes of a twenty meg file loaded.  Instead the FileLink will store the path to the format's input file, a starting position and length.  So when required the field can grab the original file and provide content.

Example
============

In the test code, you can find the following sample files.  The definitions for each class can be found in **sample.js**.

* Sample1
  * five int fields
* Sample2
  * class reference, int, long, string and byte []
* Sample3
  * Three file links, one with compression, and three string fields
* Sample4
  * int, Integer, long, Long
* Sample5
  * List, List&lt;Integer&gt;, List&lt;Sample4&gt;

If you want to see all the classes in action, you can run the JUnit test **TestSampleFormats.java**, and make sure the working directory is set to *test-resources/TestSampleFormats*.

Appendix
============

Version 1 Format Specifications
------------

&lt;&gt; = Single Byte
{} = Single Bit
() = Referenced Type

**Starting Prefix**

&lt;B&gt;&lt;M&gt;&lt;F&gt;(Format Version)(Content Version)(Nullable Size)(Tag Header)(Tag Content)

**Tag Header**

(Null-able Tag Identity)

**Complex Tag Content**

(Null-able Size)(Field)...(Field)

**Simple Tag Content**

(Null-able Size)(Field)

**Version**

{SHRINK}{0}{0}{0}{#}{#}{#}{#}&lt;Bytes&gt;

If (Value & 0x80 > 0) : Is Tiny Integer, Version = (Value & 0x7F)

**Null-able Size**

{SHRINK}{NULL?}{0}{0}{#}{#}{#}{#}&lt;Bytes&gt;

If (Value == 0x40) : Null Identity, Size = -1
Else If (Value & 0x80 > 0) : Is Tiny Integer, Size = (Value & 0x7F)

**Null-able Identity**

{STANDARD?}{SHRINK}{NULL?}{0}{#}{#}{#}{#}&lt;Bytes&gt;

If (Value == 0x20) : Null Identity
Else If (Value & 0x40 > 0) : Is Tiny Integer, Identity Key = (Value & 0x3F)
Else If (Value & 0x80 > 0) : Is a built in "simple" tag, Integer, Long, String

**signed int field (4 bytes)**

{SHRINK}{INVERSE?}{0}{0}{#}{#}{#}{#}&lt;Bytes&gt;

**Integer field (4 bytes)**

{SHRINK}{INVERSE?}{NULL?}{0}{#}{#}{#}{#}&lt;Bytes&gt;

**signed long field (8 bytes)**

{SHRINK}{INVERSE?}{0}{0}{#}{#}{#}{#}&lt;Bytes&gt;

**Long field (8 bytes)**

{SHRINK}{INVERSE?}{NULL?}{0}{#}{#}{#}{#}&lt;Bytes&gt;

**String field (UTF-8)**

(Nullable Size)&lt;Bytes&gt;

**byte [] field**

(Nullable Size)&lt;Bytes&gt;

**FileLink field**

(Nullable Size)&lt;Bytes&gt;

**Tag Field**

(Nullable Size)(Field)

**List Field**

(Nullable Size)(Size)(Field)...(Field)

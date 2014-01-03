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

Supported field types
------------

**Primitive types**

* int
* long

**Instance Types**

* Integer
* Long
* String
* byte []

**Special Types**

* tag
* list

Required 3rd Party Libraries
------------

* jackson-annotations (Tested with 2.2.3)
* jackson-core (Tested with 2.2.3)
* jackson-databind (Tested with 2.2.3)

* junit (Tested with 3.8.1)

Limitations and quirks
------------


Example
============


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

(Nullable Tag Identity)

**Complex Tag Content**

(Nullable Size)(Field)...(Field)

**Simple Tag Content**

(Nullable Size)(Field)

**Version**

{SHRINK}{0}{0}{0}{#}{#}{#}{#}&lt;Bytes&gt;

**Nullable Size**

{SHRINK}{NULL?}{0}{0}{#}{#}{#}{#}&lt;Bytes&gt;

**Nullable Identity**

{SHRINK}{STANDARD?}{NULL?}{0}{#}{#}{#}{#}&lt;Bytes&gt;

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

shiny-mapper
============

A basic mapper to read/write POJO instances to a very compressed binary file format.  While working on another project, I originally was going to use Jackson to read/write POJO to JSON/XMl, but after looking at the overhead of saving large byte arrays I came up with this binary mapper, which is nowhere as powerful as Jackson.  The format comes with additional limitations and restrictions which are needed to keep track of multiple file formats, since binary formats are very fickle and need strict rule sets or they are just become a collection of random bytes.

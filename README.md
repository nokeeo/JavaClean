JavaClean
=========
JavaClean is a Java Application that sorts files based on a XML file that represents a directory structure.

Requirements
--------
Java 7 <br />

#### Tested on:
OSX 10.9

XML Overview
--------
There are three main tags in the XML Directory Structure. The three tags are directory, file, and fileProperty.

### Directory Tag
The directory tag represents a directory within a file structure.  There are two main types of directories. Statically named directories and dynamically named directories.<br />

####Statically Named Directories
Statically named directories require the type and name attributes. The value of type must be the string "folder" This will produce a directory with the given name.<br />
Example:
```xml
<directory type="folder" name="the-name-of-the-directory" />
```

#### Dynamically Named Directories
The name of directories can also be created on the fly.  An example of this is the year directory type. All files from that year will be sorted into the directory.  The name attribute is not necessary and will be ignored.<br />
Example:
```xml
<directory type="year" />
```

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
The name of directories can also be created on the fly.  An example of this is the year directory type. All files will be sorted into a directory that is named after the year the file was last modified.  The name attribute is not necessary and will be ignored.<br />
Example:
```xml
<directory type="year" />
```

###File and FileProperty Tags
The file and the fileProperty tags allows the user to specify what kind of files belong in a directory. For example:
```xml
<directory type="folder" name="Example Directory">
  <file>
    <fileProperty type="fileType">png</fileProperty>
  </file>
</directory>
```
The directory, Example Directory will only contain files with the file type png.<br />
A file can also contain more than one fileProperty tag. For example:
```xml
<directory type="folder" name="Example Directory">
  <file>
    <fileProperty type="fileType">png</fileProperty>
    <fileProperty type="startsWith">a</fileProperty>
  </file>
</directory>
```
The directory will contain all files with the file type png and start with the letter 'a'.<br />
A directory can also have more than one file. For example:
```xml
<directory type="folder" name="Example Directory">
  <file>
    <fileProperty type="fileType">png</fileProperty>
  </file>
  <file>
    <fileProperty type="fileType">gif</fileProperty>
  </file>
</directory>
```
The directory will contain all files with the file type png or gif.<br />

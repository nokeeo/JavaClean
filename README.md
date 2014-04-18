JavaClean
=========
JavaClean is a Java Application that sorts files based on a XML file that represents a directory structure.

Requirements
--------
Java 7 <br />
configSchema.xsd file must be in the root directory of the project or the directory of the jar file.

#### Tested on:
OSX 10.9

Running the Application via the Command Line
--------
Provide the arguments in the order of: <br />
1. Configuration file path <br />
2. Source Directory <br />
3. Destination Directory <br />

If no arguments are provide the application will launch a GUI.

Automation
--------
####Mac
javaclean.sh file in supportFiles directory provides the necessary operations required to automate running the application. In the file change the bash variables to the desired paths and files.

After change the path of the javaclean.sh file in the automator workflow to the location of the edited javaclean.sh file. The automator event can then be opened by a recurring iCal event.

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
A directory can also have more than one file tag. For example:
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

XML Tags
--------
### Dynamically named Directory Tags
For all dynamically named directory tags the name attribute will be ignored.
#### Year
Sorts files into directories with the names of the years the files were last modified.
```xml
<directory type="year" />
```
#### Month
Sorts files into directories with the numeric values of the months the files were last modified.
```xml
<directory type="month" />
```
#### Day
Sorts files into directories with the numeric values of the days the files were last modified.
```xml
<directory type="day" />
```
### Preset Directory Tags
For all preset directory tags the name attribute is optional. If no name attribute is provided the name of the directory will be the name of the preset tag.
#### Video
Sorts all video files into this folder.
```xml
<directory type="video" />
```
#### Pictures
Sorts all image files into this folder.
```xml
<directory type="picture" />
```
#### Audio
Sorts all audio files into this folder.
```xml
<directory type="audio" />
```
#### Documents
Sorts all document files(pdf, doc, ppt, etc) into this folder.
```xml
<directory type="document" />
```
### File Property Tags
#### Contains
The file name contains a specified value.
```xml
<fileProperty type="contains">example</fileProperty>
```
#### File Type
The file has the specified extension.
```xml
<fileProperty type="fileType">doc</fileProperty>
```
#### Starts With
The file starts with the specified value.
```xml
<fileProperty type="startsWith">startWithValue</fileProperty>
```

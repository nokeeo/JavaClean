/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaclean.directoryStructures;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;

/**
 * Represents a directory where all the contents were last modified on the same
 * day.
 * @author Eric Lee
 */
public class DayDirectoryStructure extends DirectoryStructure {
    public DayDirectoryStructure(String directoryName) {
        super(directoryName);
    }
    
    /**
     * Always returns true, because all files were modified on a date.
     * @param originalPath The path of the file to check
     * @return True if the check is successful 
     */
    public boolean checkForPathMatch(Path originalPath) {
        return true;
    }
    
    /**
     * Gets the name of the directory which will be the numeric representation
     * of the day
     * @param originalPath the path to the file
     * @return Returns the name of the directory.
     */
    protected String getCurrentDirectoryName(Path originalPath) {
        try {
            BasicFileAttributes fileAttributes = Files.readAttributes(originalPath, BasicFileAttributes.class);
            long year = fileAttributes.lastModifiedTime().toMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("d");
            return sdf.format(year);
        }
        catch(IOException e){
            System.err.println(e);
        }
        
        return null;
    }
}

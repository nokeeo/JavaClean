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
 * A directory structure for all files within a month.
 * @author Eric Lee
 */
public class MonthDirectoryStructure extends DirectoryStructure {
    public MonthDirectoryStructure(String directoryName) {
        super(directoryName);
    }
    
    /**
     * Always returns true because all files have a last modified month
     * @param originalPath
     * @return 
     */
    public boolean checkForPathMatch(Path originalPath) {
        return true;
    }
    
    /**
     * Gets the numeric representation for the directories month
     * @param originalPath
     * @return 
     */
    protected String getCurrentDirectoryName(Path originalPath) {
        try {
            BasicFileAttributes fileAttributes = Files.readAttributes(originalPath, BasicFileAttributes.class);
            long month = fileAttributes.lastModifiedTime().toMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("M");
            return sdf.format(month);
        }
        catch(IOException e){
            System.err.println(e);
        }
        
        return null;
    }
}

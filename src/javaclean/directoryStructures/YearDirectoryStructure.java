/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaclean.directoryStructures;

import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @author Eric Lee
 */
public class YearDirectoryStructure extends DirectoryStructure {

    public YearDirectoryStructure(String directoryName) {
        super(directoryName);
    }
    
    /**
     * Always returns true because all files have a last modified year
     * @param originalPath
     * @return 
     */
    public boolean checkForPathMatch(Path originalPath) {
        return true;
    }
    
    /**
     * Returns the year of the year directory.
     * @param originalPath
     * @return 
     */
    protected String getCurrentDirectoryName(Path originalPath) {
        try {
            BasicFileAttributes fileAttributes = Files.readAttributes(originalPath, BasicFileAttributes.class);
            long year = fileAttributes.lastModifiedTime().toMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            return sdf.format(year);
        }
        catch(IOException e){
            System.err.println(e);
        }
        
        return null;
    }
}

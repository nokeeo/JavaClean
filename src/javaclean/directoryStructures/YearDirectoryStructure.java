/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaclean.directoryStructures;

import javaclean.directoryStructures.DirectoryStructure;
import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.nio.file.attribute.BasicFileAttributes;

/**
 *
 * @author ericlee
 */
public class YearDirectoryStructure extends DirectoryStructure {

    public YearDirectoryStructure(String directoryName) {
        super(directoryName);
    }
    
    public boolean checkForPathMatch(Path originalPath) {
        return true;
    }
    
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaclean.directoryStructures;

import javaclean.directoryStructures.DirectoryStructure;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;

/**
 *
 * @author ericlee
 */
public class MonthDirectoryStructure extends DirectoryStructure {
    public MonthDirectoryStructure(String directoryName) {
        super(directoryName);
    }
    
    public boolean checkForPathMatch(Path originalPath) {
        return true;
    }
    
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaclean.fileStructures;

import java.util.*;
import java.nio.file.*;

/**
 * Outlines a file individual file structure.
 * A file structure is composed of several file properties.
 * @author Eric Lee
 */
public class FileStructure {
    protected ArrayList<FileProperty> properties = new ArrayList<FileProperty>();
    
    /**
     * Checks if all the file properties match the given path
     * @param originalPath The path to check
     * @return true if all file properties match
     */
    public boolean checkForPathMatch(Path originalPath) {
        if(properties.isEmpty())
            return true;
        
        for(FileProperty property : this.properties) {
            if(!property.checkForPathMatch(originalPath)) 
                return false;
        }
        return true;
    }
    
    /**
     * Adds a file property to the file
     * @param fileProp The property to add
     */
    public void addFileProperty(FileProperty fileProp) {
        this.properties.add(fileProp);
    }
}

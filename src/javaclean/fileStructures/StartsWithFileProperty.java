/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaclean.fileStructures;

import java.nio.file.Path;
/**
 * Creates a file property to check if a file starts with a value
 * @author Eric Lee
 */
public class StartsWithFileProperty extends FileProperty {
    private final String startsWithValue;
    
    /**
     * @param startsWithValue Value the file should start with 
     */
    public StartsWithFileProperty(String startsWithValue) {
        this.startsWithValue = startsWithValue;
    }
    
    /**
     * Checks to see if a given file starts with the value.
     * @param originalPath The path to check with
     * @return true if the file starts with the value
     */
    public boolean checkForPathMatch(Path originalPath) {
        String fileName = originalPath.getFileName().toString();
        return fileName.startsWith(this.startsWithValue);
    }
}

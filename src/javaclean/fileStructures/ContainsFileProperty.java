/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaclean.fileStructures;

import java.nio.file.*;
/**
 * Checks if a file name contains a certain value.
 * @author Eric Lee
 */
public class ContainsFileProperty extends FileProperty {
    private final String containsValue;
    
    /**
     * @param the value that the file name must include 
     */
    public ContainsFileProperty(String containsValue) {
        this.containsValue = containsValue;
    }
    
    /**
     * Checks if the file name contains a given value
     * @param originalPath The path to check
     * @return true if the file name contains that value.
     */
    public boolean checkForPathMatch(Path originalPath) {
        String fileName = originalPath.getFileName().toString();
        return fileName.contains(this.containsValue);
    }
}

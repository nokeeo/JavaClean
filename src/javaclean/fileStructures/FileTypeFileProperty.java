/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaclean.fileStructures;

import java.nio.file.Path;


/**
 * File property for a given file extension.
 * @author Eric Lee
 */
public class FileTypeFileProperty extends FileProperty{
    private final String propertyFileExt;
    
    /**
     * @param fileExt File extension of the property
     */
    public FileTypeFileProperty(String fileExt) {
        this.propertyFileExt = fileExt;
    }
    
    /**
     * Checks to see if a given file has a certain file type
      * @param originalPath The path to check with
     * @return true if the property is a match
     */
    public boolean checkForPathMatch(Path originalPath) {
        String fileName = originalPath.getFileName().toString();
        int fileExtIndex = fileName.lastIndexOf(".");
        String fileExt = fileName.substring(fileExtIndex + 1);
        return this.propertyFileExt.equals(fileExt);
    }
}

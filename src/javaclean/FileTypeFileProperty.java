/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaclean;

import java.nio.file.*;

/**
 *
 * @author ericlee
 */
public class FileTypeFileProperty extends FileProperty{
    private String propertyFileExt;
    
    public FileTypeFileProperty(String fileExt) {
        this.propertyFileExt = fileExt;
    }
    
    public boolean checkForPathMatch(Path originalPath) {
        String fileName = originalPath.getFileName().toString();
        int fileExtIndex = fileName.lastIndexOf(".");
        String fileExt = fileName.substring(fileExtIndex + 1);
        return this.propertyFileExt.equals(fileExt);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaclean.fileStructures;

import javaclean.fileStructures.FileProperty;
import java.nio.file.*;
/**
 *
 * @author ericlee
 */
public class StartsWithFileProperty extends FileProperty {
    private String startsWithValue;
    
    public StartsWithFileProperty(String startsWithValue) {
        this.startsWithValue = startsWithValue;
    }
    
    public boolean checkForPathMatch(Path originalPath) {
        String fileName = originalPath.getFileName().toString();
        return fileName.startsWith(this.startsWithValue);
    }
}

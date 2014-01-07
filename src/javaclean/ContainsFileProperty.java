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
public class ContainsFileProperty extends FileProperty {
    private String containsValue;
    
    public ContainsFileProperty(String containsValue) {
        this.containsValue = containsValue;
    }
    
    public boolean checkForPathMatch(Path originalPath) {
        String fileName = originalPath.getFileName().toString();
        return fileName.contains(this.containsValue);
    }
}

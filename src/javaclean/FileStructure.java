/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaclean;

import java.util.*;
import java.nio.file.*;

/**
 *
 * @author ericlee
 */
public class FileStructure {
    protected ArrayList<FileProperty> properties = new ArrayList<FileProperty>();
    
    public boolean checkForPathMatch(Path originalPath) {
        if(properties.isEmpty())
            return true;
        
        for(FileProperty property : this.properties) {
            if(!property.checkForPathMatch(originalPath)) 
                return false;
        }
        return true;
    }
}

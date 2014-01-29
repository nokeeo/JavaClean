/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaclean.directoryStructures;

import javaclean.directoryStructures.DirectoryStructure;
import java.nio.file.Path;

/**
 *
 * @author ericlee
 */
public class FolderDirectoryStructure extends DirectoryStructure {
    public FolderDirectoryStructure(String directoryName) {
        super(directoryName);
    }
    
    public boolean checkForPathMatch(Path originalPath) {
        return true;
    }
    
    protected String getCurrentDirectoryName(Path originalPath) {
        return this.directoryName;
    }
}

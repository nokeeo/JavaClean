/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaclean;

import java.nio.file.Path;
import java.util.*;

/**
 *
 * @author ericlee
 */
public abstract class DirectoryStructure {
    
    ArrayList<DirectoryStructure> subDirectories = new ArrayList<DirectoryStructure>();
    protected String directoryName;
    
    abstract boolean checkForPathMatch(Path originalPath);
    abstract String getCurrentDirectoryName(Path originalPath);
    
    public DirectoryStructure(String directoryName) {
        this.directoryName = directoryName;
    }
    
    public String getNextPath(Path originalPath) {
        if(subDirectories.isEmpty())
            return getCurrentDirectoryName(originalPath) + "/";
        //Else
        for(DirectoryStructure subDirectory : subDirectories) {
            if(subDirectory.checkForPathMatch(originalPath)) {
                String nextPath = subDirectory.getNextPath(originalPath);
                if(nextPath == null)
                    return null;
                return getCurrentDirectoryName(originalPath) + "/" + nextPath;
            }
        }
        return null;
    }
}

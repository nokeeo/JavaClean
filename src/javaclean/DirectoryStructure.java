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
    ArrayList<FileStructure> fileStructures = new ArrayList<FileStructure>();
    protected String directoryName;
    
    abstract boolean checkForPathMatch(Path originalPath);
    abstract String getCurrentDirectoryName(Path originalPath);
    
    public DirectoryStructure(String directoryName) {
        this.directoryName = directoryName;
    }
    
    public String getNextPath(Path originalPath) {
        
        if(!fileStructures.isEmpty()) {
            if(!checkFileStructures(originalPath))
                return null;
        }
        
        for(DirectoryStructure subDirectory : subDirectories) {
            if(subDirectory.checkForPathMatch(originalPath)) {
                String nextPath = subDirectory.getNextPath(originalPath);
                if(nextPath == null)
                    return null;
                return getCurrentDirectoryName(originalPath) + "/" + nextPath;
            }
        }
        return getCurrentDirectoryName(originalPath) + "/";
    }
    
    private boolean checkFileStructures(Path originalPath) {
        boolean fileStructureCheck = false;
        System.out.println(originalPath.getFileName().toString());
        for(FileStructure currentFileStructure : fileStructures) {
            if(currentFileStructure.checkForPathMatch(originalPath)) {
                fileStructureCheck = true;
                break;
            }
        }
        return fileStructureCheck;
    }
}

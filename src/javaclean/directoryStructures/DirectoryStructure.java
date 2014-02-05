/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaclean.directoryStructures;

import java.nio.file.Path;
import java.util.*;
import javaclean.fileStructures.FileStructure;

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
        
        if(subDirectories.isEmpty())
            return getCurrentDirectoryName(originalPath) + "/";
        
        boolean matchFound = true;
        String nextPath = null;
        for(DirectoryStructure subDirectory : subDirectories) { 
            if(subDirectory.checkForPathMatch(originalPath)) {
                nextPath = subDirectory.getNextPath(originalPath);
                if(nextPath == null)
                    matchFound = false;
                else {
                    matchFound = true;
                    break;
                }
            }
        }
        if(matchFound == false)
            return null;
        return getCurrentDirectoryName(originalPath) + "/" + nextPath;
        
    }
    
    public void addSubDirectory(DirectoryStructure dirStruct) {
        this.subDirectories.add(dirStruct);
    }
    
    public void addFileStructure(FileStructure fileStruct) {
        this.fileStructures.add(fileStruct);
    }
    
    private boolean checkFileStructures(Path originalPath) {
        boolean fileStructureCheck = false;
        for(FileStructure currentFileStructure : fileStructures) {
            if(currentFileStructure.checkForPathMatch(originalPath)) {
                fileStructureCheck = true;
                break;
            }
        }
        return fileStructureCheck;
    }
}

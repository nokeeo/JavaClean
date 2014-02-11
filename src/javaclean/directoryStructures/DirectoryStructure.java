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
 * The abstract class for a representation of a directory.
 * @author ericlee
 */
public abstract class DirectoryStructure {
    
    ArrayList<DirectoryStructure> subDirectories = new ArrayList<DirectoryStructure>();
    ArrayList<FileStructure> fileStructures = new ArrayList<FileStructure>();
    protected String directoryName;
    
    /**
     * checks for a path match for a given structure
     * @param originalPath Path of file
     * @return true if a match is found false if not
     */
    abstract boolean checkForPathMatch(Path originalPath);
    
    /**
     * Gets the name of the current directory
     * @param originalPath original path of the file
     * @return the name of the current directory
     */
    abstract String getCurrentDirectoryName(Path originalPath);
    
    /**
     * Constructor
     * @param directoryName The name of the directory
     */
    public DirectoryStructure(String directoryName) {
        this.directoryName = directoryName;
    }
    
    /**
     * Gets the next path to check
     * @param originalPath The path of the file to check
     * @return null if no other files to check
     * The full path of file's path within the directory structure.
     */
    public String getNextPath(Path originalPath) {
        
        //If there are file structures check for match on the file structures
        if(!fileStructures.isEmpty()) {
            if(!checkFileStructures(originalPath))
                return null;
        }
        
        //If there are no subdirectories a match is found
        if(subDirectories.isEmpty())
            return getCurrentDirectoryName(originalPath) + "/";
        
        //Else check subdirectories for a match
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
    
    /**
     * Adds a subdirectory to a structure
     * @param dirStruct Structure to add
     */
    public void addSubDirectory(DirectoryStructure dirStruct) {
        this.subDirectories.add(dirStruct);
    }
    
    /**
     * Adds file structure to the directory structure
     * @param fileStruct The file structure to add
     */
    public void addFileStructure(FileStructure fileStruct) {
        this.fileStructures.add(fileStruct);
    }
    
    /**
     * Check if there is a path match within this file structure
     * @param originalPath Path of file
     * @return true if a match is found, false if not
     */
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

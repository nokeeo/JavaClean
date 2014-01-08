/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaclean;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
/**
 *
 * @author ericlee
 */
public class FileMover {
    
    protected DirectoryStructure dirStructure;
    protected String sourcePath;
    protected String destinationPath;
    
    public FileMover(DirectoryStructure dirStructure, String sourcePath, String destinationPath) {
        this.dirStructure = dirStructure;
        this.sourcePath = sourcePath;
        this.destinationPath = destinationPath;
    }
    
    public void moveFiles() {
        Path sourceDir = Paths.get(this.sourcePath + "/");
        DirectoryStream<Path> stream;
        try {
            stream = Files.newDirectoryStream(sourceDir);
        
            for(Path currentFile : stream) {
                BasicFileAttributes fileAttributes = Files.readAttributes(currentFile, BasicFileAttributes.class);
                if(!fileAttributes.isDirectory()) {
                    this.processFile(currentFile);
                }
            }
        }
        catch(IOException | DirectoryIteratorException e) {
            System.err.println(e);
        }
    }
    
    private void processFile(Path file) {
        String moveLocation = dirStructure.getNextPath(file);
        if(moveLocation != null) {
            Path movePath = Paths.get(this.destinationPath + "/" + moveLocation);
            try {
                Files.createDirectories(movePath);
                Files.move(file, Paths.get(this.destinationPath + "/" + moveLocation + file.getFileName().toString()));
            }
            catch(IOException e) {
                System.err.println(e);
            }
        }
    }
}

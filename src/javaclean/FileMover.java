/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaclean;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import javax.swing.SwingWorker;
/**
 *
 * @author ericlee
 */
public class FileMover extends SwingWorker{
    
    protected DirectoryStructure dirStructure;
    protected String sourcePath;
    protected String destinationPath;
    protected ArrayList<Path> filesToMove = new ArrayList<Path>();
    protected FileMoverInterface delegate;
    
    public FileMover(DirectoryStructure dirStructure, String sourcePath, String destinationPath, FileMoverInterface delegate) {
        this.dirStructure = dirStructure;
        this.sourcePath = sourcePath;
        this.destinationPath = destinationPath;
        this.delegate = delegate;
        
        Path sourceDir = Paths.get(this.sourcePath + "/");
        DirectoryStream<Path> stream;
        try {
            stream = Files.newDirectoryStream(sourceDir);
        
            for(Path currentFile : stream) {
                BasicFileAttributes fileAttributes = Files.readAttributes(currentFile, BasicFileAttributes.class);
                if(!fileAttributes.isDirectory()) {
                    this.filesToMove.add(currentFile);
                }
            }
        }
        catch(IOException | DirectoryIteratorException e) {
            System.err.println(e);
        }
    }
    
    public void moveFiles() {
        int filesToMove = this.filesToMove.size();
        int completedFiles = 0;
        this.setProgress(0);
        for(Path currentFile : this.filesToMove) {
            this.processFile(currentFile);
            
            completedFiles++;
            this.setProgress((completedFiles / filesToMove) * 100);
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
    
    protected Void doInBackground(){
        this.delegate.moveFilesStarted();
        this.moveFiles();
        return null;
    }
    
    protected void done() {
        this.delegate.moveFilesCompleted();
    }
}

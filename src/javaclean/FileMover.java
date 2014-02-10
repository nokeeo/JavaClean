/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaclean;

import javaclean.directoryStructures.DirectoryStructure;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import javax.swing.SwingWorker;
/**
 * Moves file to a destination folder.
 * A delegate variable can be set that conforms to the FileMoverInterface.
 * The method moveFilesCompleted will be called at the completion of moving the 
 * files
 * @author Eric Lee
 */
public class FileMover extends SwingWorker{
    public FileMoverInterface delegate;
    protected DirectoryStructure dirStructure;
    protected String sourcePath;
    protected String destinationPath;
    protected ArrayList<Path> filesToMove = new ArrayList<Path>();
    
    /**
     * @param dirStructure the directory structure the destination should adhere to 
    * @param sourcePath the path to the files to move
     * @param destinationPath the path to where the files should be moved
     * @param delegate the delegate that receives all callbacks
     */
    public FileMover(DirectoryStructure dirStructure, String sourcePath, String destinationPath, FileMoverInterface delegate) {
        this.dirStructure = dirStructure;
        this.sourcePath = sourcePath;
        this.destinationPath = destinationPath;
        this.delegate = delegate;
        
        Path sourceDir = Paths.get(this.sourcePath + "/");
        
        //Construct the list of all the files to move
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
    
    /**
     * Moves the files from the source to destination
     * Keeps track of the progress of the move via SwingWorker
     */
    private void moveFiles() {
        int filesToMove = this.filesToMove.size();
        int completedFiles = 0;
        this.setProgress(0);
        for(Path currentFile : this.filesToMove) {
            this.processFile(currentFile);
            
            completedFiles++;
            this.setProgress((completedFiles / filesToMove) * 100);
        }
    }
    
    /**
     * Moves an individual file.
     * @param file file to move
     */
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
    
    /**
     * This method is called when this task is executed.
     * Performs callback to delegate and starts moving the files.
     * @return 
     */
    protected Void doInBackground(){
        this.delegate.moveFilesStarted();
        this.moveFiles();
        return null;
    }
    
    /**
     * Runs when the files are done moving. Callsback to the delegate informing
     * it that the files have been moved.
     */
    protected void done() {
        this.delegate.moveFilesCompleted();
    }
}

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
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
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
    public boolean verbose;
    
    protected DirectoryStructure dirStructure;
    protected String sourcePath;
    protected String destinationPath;
    protected ArrayList<Path> filesToMove = new ArrayList<Path>();
    
    
    /**
     * @param dirStructure the directory structure the destination should adhere to 
    * @param sourcePath the path to the files to move
     * @param destinationPath the path to where the files should be moved
     */
    public FileMover(DirectoryStructure dirStructure, String sourcePath, String destinationPath) {
        this.dirStructure = dirStructure;
        this.sourcePath = sourcePath;
        this.destinationPath = destinationPath;
        this.verbose = false;
        
        Path sourceDir = Paths.get(this.sourcePath + "/");
        
        //Construct the list of all the files to move
        DirectoryStream<Path> stream;
        try {
            stream = Files.newDirectoryStream(sourceDir);
            for(Path currentFile : stream) {
                BasicFileAttributes fileAttributes = Files.readAttributes(currentFile, BasicFileAttributes.class);
                System.out.println(currentFile.toString());
                if(!fileAttributes.isDirectory() || Arrays.asList(getAppleFiles()).contains(getFileExtension(currentFile))) {
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
            if(moveIndividualFile(file, movePath, file.getFileName().toString())) {
                deleteFile(file);
            }
        }
    }
    
    /**
     * This method is called when this task is executed.
     * Performs callback to delegate and starts moving the files.
     * @return 
     */
    protected Void doInBackground(){
        if(delegate != null)
            delegate.moveFilesStarted();
        
        this.moveFiles();
        return null;
    }
    
    /**
     * Runs when the files are done moving. Callsback to the delegate informing
     * it that the files have been moved.
     */
    protected void done() {
        if(delegate != null)
            this.delegate.moveFilesCompleted();
    }
    
    protected String getFileExtension(Path file) {
        String fileString = file.toString().toLowerCase();
        
        int dotIndex = fileString.lastIndexOf(".");
        if(dotIndex != -1)
            return fileString.substring(dotIndex); 
        else
            return "";
    }
    
    protected boolean moveIndividualFile(Path file, Path movePath, String fileName) {
        try {
            System.out.println(file.getFileName().toString());
            BasicFileAttributes attributes = Files.readAttributes(file,  BasicFileAttributes.class);
            if(attributes.isDirectory()) {
                DirectoryStream<Path> stream = Files.newDirectoryStream(file);
                for(Path currentFile : stream) {
                    moveIndividualFile(currentFile, Paths.get(movePath.toString() + "/" + file.getFileName()), currentFile.getFileName().toString());
                }
            }
            else {
                 Files.createDirectories(movePath);
                 Files.copy(file, Paths.get(movePath.toString() + "/" + fileName));
                 if(verbose)
                    System.out.println("Moved " + file.toString());
            }
        }
        catch (FileAlreadyExistsException e) {
            return renameAndMoveFile(file, movePath, fileName);
        }
        catch(IOException e) {
            System.out.println(e);
            return false;
        }
        return true;
    }
    
    protected boolean renameAndMoveFile(Path file, Path movePath, String fileNameToMove) {
        int extentionIndex = fileNameToMove.lastIndexOf(".");
        String fileName = fileNameToMove.substring(0, extentionIndex);
        
        int incrementStartIndex = fileName.lastIndexOf("(");
        int incrementEndIndex = fileName.lastIndexOf(")");
        String incrementStringValue = this.getNextIncrementValue(incrementStartIndex, incrementEndIndex, fileName);
        if(incrementStringValue != null) {
            String newFileName =  incrementStringValue + fileNameToMove.substring(extentionIndex);
            return this.moveIndividualFile(file, movePath, newFileName);
        }
        else {
            return false;
        }
    }
    
    protected String getNextIncrementValue(int start, int end, String fileName) {
        if(start <= end && end < fileName.length()) {
            String rawFileName = fileName;
            if(start != -1 && end != -1) {
                String currentIncrement = fileName.substring(start + 1, end);
                try {
                    int incrementValue = Integer.parseInt(currentIncrement) + 1;
                    rawFileName = fileName.substring(0, start);
                    return rawFileName + "(" + Integer.toString(incrementValue) + ")";
                }
                catch(NumberFormatException e) {
                    return rawFileName + "(1)";
                }
            }
            else {
                return rawFileName + "(1)";
            }
        }
        
        return null;
    }
    
    protected void deleteFile(Path file) {
        try {
            BasicFileAttributes attributes = Files.readAttributes(file, BasicFileAttributes.class);
            if(attributes.isDirectory()) {
                DirectoryStream<Path> stream = Files.newDirectoryStream(file);
                for(Path currentFile : stream) {
                    deleteFile(currentFile);
                }
            }
            Files.delete(file);
        }
        catch(IOException e) {
            System.out.println(e);
        }
    }
    
     protected String[] getAppleFiles() {
        return new String[] {
            ".pages",
            ".key",
            ".keynote",
            ".numbers",
            ".fcp",
            ".band",
            ".logic",
            ".imovieproj"
        };
    }
}

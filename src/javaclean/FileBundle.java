/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javaclean;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
/**
 *
 * @author Home
 */
public class FileBundle {
    public String bundleName;
    private String bundleType = "fileBundle";
    public ArrayList<Path> files;
    public ArrayList<FileBundle> directories;
    
    public FileBundle(String name, String type) {
        this.bundleName = name;
        this.setBundelType(type);
        this.files = new ArrayList<Path>();
        this.directories = new ArrayList<FileBundle>();
    }
    
    public FileBundle(String name, String type, String format) {
        this(name, "directoryBundle");
        if(format.equals("archieve")) {
            directories.add(new FileBundle("Pictures", ""));
            directories.add(new FileBundle("Documents", ""));
            directories.add(new FileBundle("Audio", ""));
            directories.add(new FileBundle("Video", ""));
            directories.add(new FileBundle("Other", ""));
        }
    }
    
    public String getBundleType() {
        return this.bundleType;
    }
    
    public void setBundelType(String type) {
        if(type.equals("fileBundle")) {
            this.directories = new ArrayList<FileBundle>();
            this.bundleType = type;
        }
        else if(type.equals("directoryBundle")) {
            this.files = new ArrayList<Path>();
            this.bundleType = type;
        }
    }
    
    public FileBundle getDirectoryByName(String name) {
        for(int i = 0; i < this.directories.size(); i++) {
            FileBundle currentBundle = this.directories.get(i);
            if(currentBundle.bundleName.equals(name))
                return currentBundle;
        }
        
        return null;
    }
    
    public void moveFiles(String path) {
        String bundleType = this.getBundleType();
        if(bundleType.equals("directoryBundle")) {
            for(int i = 0; i < this.directories.size(); i++) {
                FileBundle currentBundle = this.directories.get(i);
                //Check if the directory exists.
                boolean directoryFound = false;
                Path newPath = Paths.get(path + "/" + currentBundle.bundleName);
                if(!Files.exists(newPath)) {
                    try {
                        Files.createDirectory(newPath);
                    }
                    
                    catch(IOException e) {
                        System.err.println(e);
                    }
                }
                currentBundle.moveFiles(newPath.toString());
            }
        }
        else if(bundleType.equals("fileBundle")) {
            boolean directoryFound = false;
            
            for(int i = 0; i < this.files.size(); i++) {
                Path currentFile = this.files.get(i);
                
                try {
                    Files.move(currentFile, Paths.get(path + "/" + currentFile.getFileName()));
                }
                
                catch(IOException | SecurityException e) {
                    System.out.println("Count not move " + currentFile.getFileName().toString());
                }
            }
        }
    }
}

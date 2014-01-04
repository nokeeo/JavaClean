/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javaclean;

import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;
import javax.swing.*;

import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jnlp.*;

/**
 *
 * @author Home
 */
public class JavaClean {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try { 
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); 
        } 
        
        catch(Exception e) { 
            System.err.println(e); 
        }
        
        String targetPath = "";
        String destinationPath = "";
        
        JOptionPane.showMessageDialog(null, "Select Target Directory");
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
        int fcReturn = fc.showOpenDialog(null);
        
        if(fcReturn == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fc.getSelectedFile();
            targetPath = selectedFile.getAbsolutePath();
        }
        else {
            JOptionPane.showMessageDialog(null, "Operation aborted!!");
            System.exit(0);
        }
        
        JOptionPane.showMessageDialog(null, "Select Destination Directory");
        fcReturn = fc.showOpenDialog(null);
        
        if(fcReturn == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fc.getSelectedFile();
            destinationPath = selectedFile.getAbsolutePath();
        }
        else {
            JOptionPane.showMessageDialog(null, "Operation aborted!!");
            System.exit(0);
        }
        
        ArrayList<String> imageTypes = new ArrayList<String>();
        imageTypes.add(".img");
        imageTypes.add(".png");
        imageTypes.add(".jpg");
        imageTypes.add(".jpeg");
        imageTypes.add(".raw");
        imageTypes.add(".gif");
        imageTypes.add(".bmp");
        imageTypes.add(".bmp");
        
        ArrayList<String> docTypes = new ArrayList<String>();
        docTypes.add(".doc");
        docTypes.add(".docx");
        docTypes.add(".pages");
        docTypes.add(".ppt");
        docTypes.add(".pptx");
        docTypes.add(".keynote");
        docTypes.add(".pdf");
        
        ArrayList<String> audioTypes = new ArrayList<String>();
        audioTypes.add(".mp3");
        audioTypes.add(".m4a");
        audioTypes.add(".m4p");
        audioTypes.add(".alac");
        audioTypes.add(".wav");
        audioTypes.add(".flac");
        
        ArrayList<String> videoTypes = new ArrayList<String>();
        videoTypes.add(".mov");
        videoTypes.add(".m4v");
        videoTypes.add(".wmv");
        videoTypes.add(".mp4");
        
        ArrayList<FileBundle> yearBundles = new ArrayList<FileBundle>();
        
        Path sourceDir = Paths.get(targetPath + "/");
        DirectoryStream<Path> stream;
        try {
            stream = Files.newDirectoryStream(sourceDir);
        
            for(Path currentFile : stream) {
                String currentFileName = currentFile.getFileName().toString();
                BasicFileAttributes fileAttributes = Files.readAttributes(currentFile, BasicFileAttributes.class);

                if(!fileAttributes.isDirectory()) {
                    FileBundle currentYearFileBundle = JavaClean.getYearBundle(yearBundles, fileAttributes.lastModifiedTime().toMillis());

                    int fileExtIndex = currentFileName.lastIndexOf(".");
                    if(fileExtIndex != -1) {
                        String currentFileType = currentFileName.substring(fileExtIndex);
                        if(imageTypes.contains(currentFileType)) {
                            FileBundle imageBundle = currentYearFileBundle.getDirectoryByName("Pictures");
                            imageBundle.files.add(currentFile);
                        }
                        else if(docTypes.contains(currentFileType)) {
                            FileBundle docBundle = currentYearFileBundle.getDirectoryByName("Documents");
                            docBundle.files.add(currentFile);
                        }
                        else if(audioTypes.contains(currentFileType)) {
                            FileBundle audioBundle = currentYearFileBundle.getDirectoryByName("Audio");
                            audioBundle.files.add(currentFile);
                        }
                        else if(videoTypes.contains(currentFileType)) {
                            FileBundle videoBundle = currentYearFileBundle.getDirectoryByName("Video");
                            videoBundle.files.add(currentFile);
                        }
                        else {
                            if(!currentFileName.equals(".DS_Store")) {
                                FileBundle otherBundle = currentYearFileBundle.getDirectoryByName("Other");
                                otherBundle.files.add(currentFile);
                            }
                        }

                    }
                }
            }
        
        }
       
        catch(IOException | DirectoryIteratorException e) {
            System.err.println(e);
        }
        
        for(int i = 0; i < yearBundles.size(); i++) {
            FileBundle currentYearBundle = yearBundles.get(i);
            System.out.println(currentYearBundle.getBundleType() + " " + currentYearBundle.bundleName);
            Path newDirectory = Paths.get(destinationPath + "/" + currentYearBundle.bundleName);
            try{
                Files.createDirectories(newDirectory);
                currentYearBundle.moveFiles(destinationPath + "/" + currentYearBundle.bundleName);
            }
            catch(IOException e) {
                System.err.println(e);
            }
        }
    }
    
    public static FileBundle getYearBundle(ArrayList<FileBundle> yearBundles, long year) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String yearString = sdf.format(year);
        for(int i = 0; i < yearBundles.size(); i++) {
            FileBundle currentFileBundle = yearBundles.get(i);
            if(currentFileBundle.bundleName.equals(yearString))
                return currentFileBundle;
        }
        FileBundle newBundle = new FileBundle(yearString, "directoryBundle", "archieve");
        yearBundles.add(newBundle);
        return newBundle;
    }
}

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
        
        String configPath = "";
        String targetPath = "";
        String destinationPath = "";
        DirectoryStructure directoryStructure;
        
        JOptionPane.showMessageDialog(null, "Select Config File");
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        
        int fcReturn = fc.showOpenDialog(null);
        
        if(fcReturn == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fc.getSelectedFile();
            configPath = selectedFile.getAbsolutePath();
        }
        else {
            JOptionPane.showMessageDialog(null, "Operation aborted!!");
            System.exit(0);
        }
        
        directoryStructure = XMLConfigReader.parseFile(configPath);
        
        JOptionPane.showMessageDialog(null, "Select Target Directory");
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
        fcReturn = fc.showOpenDialog(null);
        
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
        
        Path sourceDir = Paths.get(targetPath + "/");
        DirectoryStream<Path> stream;
        try {
            stream = Files.newDirectoryStream(sourceDir);
        
            for(Path currentFile : stream) {
                String currentFileName = currentFile.getFileName().toString();
                BasicFileAttributes fileAttributes = Files.readAttributes(currentFile, BasicFileAttributes.class);

                if(!fileAttributes.isDirectory()) {
                    String moveLocation = directoryStructure.getNextPath(currentFile);
                    if(moveLocation != null) {
                        Path movePath = Paths.get(destinationPath + "/" + moveLocation);
                        try {
                            Files.createDirectories(movePath);
                            Files.move(currentFile, Paths.get(destinationPath + "/" + moveLocation + currentFileName));
                        }
                        catch(IOException e) {
                            System.err.println(e);
                        }
                    }
                }
            }
        
        }
       
        catch(IOException | DirectoryIteratorException e) {
            System.err.println(e);
        }
    }
}

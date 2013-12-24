/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javaclean;

import java.io.File;
import java.util.*;
import javaclean.FileBundle;
import javaclean.CompareFiles;
import java.text.SimpleDateFormat;
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Home
 */
public class JavaClean {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
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
        
       
        File folder = new File(targetPath + "/");
        
        File[] files = folder.listFiles();
        
        for(int i = 0; i < files.length; i++) {
            File currentFile = files[i];
            String currentFileName = currentFile.getName();
            if(!currentFile.isDirectory()) {
                FileBundle currentYearFileBundle = JavaClean.getYearBundle(yearBundles, currentFile.lastModified());
                
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
        
        /*for(int i = 0; i < yearBundles.size(); i++) {
            FileBundle currentBundle = yearBundles.get(i);
            System.out.println(currentBundle.bundleName);
            for(int k = 0; k < currentBundle.directories.size(); k++) {
                System.out.println(currentBundle.directories.get(k).bundleName);
                
                
               
            }
        }*/
        
        for(int i = 0; i < yearBundles.size(); i++) {
            FileBundle currentYearBundle = yearBundles.get(i);
            System.out.println(currentYearBundle.getBundleType() + " " + currentYearBundle.bundleName);
            File newDirectory = new File(destinationPath + "/" + currentYearBundle.bundleName);
            newDirectory.mkdir();
            
            JavaClean.moveFiles(currentYearBundle, destinationPath + "/" + currentYearBundle.bundleName);
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
    
    public static void moveFiles(FileBundle bundleToMove, String path) {
        File moveDirectory = new File(path);
        File[] checkFiles = moveDirectory.listFiles();
        
        String bundleType = bundleToMove.getBundleType();
        if(bundleType.equals("directoryBundle")) {
            for(int i = 0; i < bundleToMove.directories.size(); i++) {
                FileBundle currentBundle = bundleToMove.directories.get(i);
                //Check if the directory exists.
                boolean directoryFound = false;
                if(checkFiles != null) {
                    for(int k = 0; k < checkFiles.length; k++) {
                        File currentCheckFile = checkFiles[k];
                        if(currentCheckFile.getName().equals(currentBundle.bundleName)) {
                            String newPath = path + "/" + currentBundle.bundleName;
                            JavaClean.moveFiles(currentBundle, newPath.format(newPath , currentBundle.bundleName));
                            directoryFound = true;
                            break;
                        }
                    }
                }
                if(!directoryFound) {
                    String newPath = path + "/" + currentBundle.bundleName;
                    System.out.println(currentBundle.getBundleType() + " " + currentBundle.bundleName);
                    File newDirectory = new File(newPath);
                    newDirectory.mkdir();
                        
                    JavaClean.moveFiles(currentBundle, newPath);
                    
                }
            }
        }
        else if(bundleType.equals("fileBundle")) {
            boolean directoryFound = false;
            
            for(int i = 0; i < bundleToMove.files.size(); i++) {
                File currentFile = bundleToMove.files.get(i);
                if(currentFile.renameTo(new File(path + "/" + currentFile.getName())))
                    System.out.println("Moved " + currentFile.getName());
                else
                    System.out.println("Can't move " + currentFile.getName());
                    System.out.println(path + currentFile.getName());
            }
        }
    }
}

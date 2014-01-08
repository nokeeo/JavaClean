/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javaclean;

import java.io.*;
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
        
        catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) { 
            System.err.println(e); 
        }
        
        SelectPathsFrame mainWindow = new SelectPathsFrame();
        mainWindow.setVisible(true);
    }
}

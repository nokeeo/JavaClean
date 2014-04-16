/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javaclean;

import javaclean.directoryStructures.DirectoryStructure;
import javax.swing.*;


/**
 *
 * @author Eric Lee
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
        
        //If arguments are given through the cmd line
        if(args.length == 3) {
            DirectoryStructure dirStructure;
            try {
                System.out.println("Reading Configuration XML file");
                System.out.println(args[0]);
                dirStructure = XMLConfigReader.parseFile(args[0]);
                
                System.out.println("Preparing to move files");
                FileMover fileMover = new FileMover(dirStructure, args[1], args[2]);
                fileMover.verbose = true;
                fileMover.execute();
                
                System.out.println("Files moved");
            } 
            
            catch (XMLParseException e) {
                System.out.println("The computer elfs have could not read your XML file: \n" + e.getMessage());
            }
            
        }
        
        //If no args show GUI
        else if(args.length == 0){
            SelectPathsFrame mainWindow = new SelectPathsFrame();
            mainWindow.setVisible(true);
        }
        
        else {
            System.out.println("The computer elfs are confused. Invalid number of arguments");
        }
    }
}

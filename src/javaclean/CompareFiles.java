package javaclean;
import java.io.*;
import java.util.*;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Home
 */
public class CompareFiles implements Comparator<File> {
    
    public int compare(File compareFile, File compareToFile) {
        if(compareFile.lastModified() > compareToFile.lastModified())
            return 1;
        else
            return 0;
    }
}

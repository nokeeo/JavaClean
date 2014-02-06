/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaclean.fileStructures;

import java.nio.file.*;
/**
 * Abstract class of a file property.
 * @author Eric Lee
 */
public abstract class FileProperty {
    /**
     * Check if the given path is a match with the file property.
     * @param originalPath
     * @return 
     */
    abstract boolean checkForPathMatch(Path originalPath);
}

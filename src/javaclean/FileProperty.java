/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaclean;

import java.nio.file.*;
/**
 *
 * @author ericlee
 */
public abstract class FileProperty {
    abstract boolean checkForPathMatch(Path originalPath);
}

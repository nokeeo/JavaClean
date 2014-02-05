/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaclean;

/**
 * Interface for the FileMover Delegate.
 * @author Eric Lee
 */
public interface FileMoverInterface {
    public void moveFilesCompleted();
    public void moveFilesStarted();
}

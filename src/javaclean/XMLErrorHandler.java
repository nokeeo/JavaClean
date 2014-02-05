/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaclean;

import javax.swing.JOptionPane;
import org.xml.sax.*;

/**
 *
 * @author ericlee
 */
public class XMLErrorHandler implements ErrorHandler{
    public void warning(SAXParseException e) throws SAXException {
        System.out.println(e);
    }
    
    public void error(SAXParseException e) throws SAXException {
        String errorMessage = "Line " + Integer.toString(e.getLineNumber()) + ": " + e.getMessage();
        throw new SAXException(errorMessage);
    }
    
    public void fatalError(SAXParseException e) throws SAXException {
        String errorMessage = "Line " + Integer.toString(e.getLineNumber()) + ": " + e.getMessage();
        throw new SAXException(errorMessage);
    }
}

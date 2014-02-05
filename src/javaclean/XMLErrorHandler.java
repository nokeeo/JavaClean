/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaclean;

import org.xml.sax.*;

/**
 * Responds to schema errors when reading the XML document
 * @author Eric Lee
 */
public class XMLErrorHandler implements ErrorHandler{
    /**
     * Warning will fail quietly.
     * @param e
     * @throws SAXException 
     */
    public void warning(SAXParseException e) throws SAXException {
        System.out.println(e);
    }
    
    /**
     * Errors throw a SAXException with the line number where the error occurred
     * @param e
     * @throws SAXException 
     */
    public void error(SAXParseException e) throws SAXException {
        String errorMessage = "Line " + Integer.toString(e.getLineNumber()) + ": " + e.getMessage();
        throw new SAXException(errorMessage);
    }
    
    /**
     * Fatal errors throw a SAXException with the line number where the error occurred
     * @param e
     * @throws SAXException 
     */
    public void fatalError(SAXParseException e) throws SAXException {
        String errorMessage = "Line " + Integer.toString(e.getLineNumber()) + ": " + e.getMessage();
        throw new SAXException(errorMessage);
    }
}

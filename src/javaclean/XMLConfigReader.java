/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaclean;

import javaclean.fileStructures.StartsWithFileProperty;
import javaclean.fileStructures.FileTypeFileProperty;
import javaclean.fileStructures.FileProperty;
import javaclean.fileStructures.ContainsFileProperty;
import javaclean.fileStructures.FileStructure;
import javaclean.directoryStructures.DayDirectoryStructure;
import javaclean.directoryStructures.FileTypeDirectoryStructure;
import javaclean.directoryStructures.FolderDirectoryStructure;
import javaclean.directoryStructures.MonthDirectoryStructure;
import javaclean.directoryStructures.YearDirectoryStructure;
import javaclean.directoryStructures.DirectoryStructure;
import java.io.File;
import java.io.IOException;
import javax.xml.XMLConstants;
import javax.xml.parsers.*;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * A XML Reader that constructs the directory data structure.
 * @author ericlee
 */
public class XMLConfigReader {
    
    /**
     * Parses a given XML file and returns data structure
     * @param filename The XML file to parse
     * @return The directory structure represented in the XML file
     * @throws XMLParseException If the XML doc does not match the schema the
     * method will throw a XMLParseException.
     */
    public static DirectoryStructure parseFile(String filename) throws XMLParseException{        
        try {
            File configFile = new File(filename);
            
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(new File("./configSchema.xsd")));
            
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            dbf.setValidating(false);
            dbf.setSchema(schema);
            
            DocumentBuilder dBuilder = dbf.newDocumentBuilder();
            dBuilder.setErrorHandler(new XMLErrorHandler());
            
            Document parseDoc = dBuilder.parse(configFile);
            
            Node root = parseDoc.getElementsByTagName("directory").item(0);
            return XMLConfigReader.parseDirectoryNode(root, new FolderDirectoryStructure(""));
            
        } 
        catch (SAXException | ParserConfigurationException | IOException e) {
            throw new XMLParseException(e.getMessage());
        }
    }
    
    /**
     * Parses a Directory Node
     * @param node The node to parse
     * @param directory The directory structure the directory should be added to
     * @return the directory structure with the node added
     */
    private static DirectoryStructure parseDirectoryNode(Node node, DirectoryStructure directory) {
        //Parese current node
        if(node.getNodeName().equals("directory")) {
            if(node.hasAttributes()) {
                Node typeNode = node.getAttributes().getNamedItem("type");
                DirectoryStructure dirStructure = null;
                if(typeNode != null) {
                    dirStructure = getDirectoryStructureForType(node, typeNode);
                    directory.addSubDirectory(dirStructure);
                }
                parseDirectoryChildren(node, dirStructure);
            }
        }
        
        else if(node.getNodeName().equals("file")) {
            FileStructure newFileStructure = parseFileNode(node);
            directory.addFileStructure(newFileStructure);
        }
        return directory;
    }
    
    /**
     * Parses a file node
     * @param fileNode The file node to parse
     * @return The FileStructure of the file node.
     */
    private static FileStructure parseFileNode(Node fileNode) {
        FileStructure file = new FileStructure();
        NodeList fileProperties = fileNode.getChildNodes();
        for(int i = 0; i < fileProperties.getLength(); i++) {
            FileProperty newProperty = parseFilePropertyNode(fileProperties.item(i));
            if(newProperty != null)
                file.addFileProperty(newProperty);
        }
        return file;
    }
    
    /**
     * Parses the given type of a directory node and returns the corresponding
     * structure.
     * @param directoryNode The directory node to get the type for
     * @param typeNode The node of the type.
     * @return The 
     */
    private static DirectoryStructure getDirectoryStructureForType(Node directoryNode, Node typeNode) {
        DirectoryStructure returnStructure = null;
        String type = typeNode.getNodeValue();
        if(type.equals("video") || type.equals("picture") || type.equals("audio") || type.equals("document")) {
                Node nameNode = directoryNode.getAttributes().getNamedItem("name");
                if(nameNode != null)
                    returnStructure = new FileTypeDirectoryStructure(nameNode.getNodeName(), typeNode.getNodeValue());
                else {
                    String typeValue = typeNode.getNodeValue();
                    String dirName = Character.toUpperCase(typeValue.charAt(0)) + typeValue.substring(1);
                    returnStructure = new FileTypeDirectoryStructure(dirName, typeValue);
                }
        }
        
        else if(type.equals("year"))
            returnStructure = new YearDirectoryStructure(null);
        
        else if(type.equals("month"))
            returnStructure = new MonthDirectoryStructure(null);
        
        else if(type.equals("day"))
            returnStructure = new DayDirectoryStructure(null);
        else if(type.equals("folder")) {
             Node nameNode = directoryNode.getAttributes().getNamedItem("name");
             if(nameNode != null) {
                 returnStructure = new FolderDirectoryStructure(nameNode.getNodeValue());
             }
             
             else
                 System.out.println("All Folder directory needs a name!");
        }
        
        return returnStructure;
    }
    
    /**
     * Parses the directory's children
     * @param parentNode The Parent XML node
     * @param parentDirectory The Parent Directory Structure
     */
    private static void parseDirectoryChildren(Node parentNode, DirectoryStructure parentDirectory) {
        NodeList children = parentNode.getChildNodes();
        for(int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            XMLConfigReader.parseDirectoryNode(child, parentDirectory);
        }
    }
    
    /**
     * Parses a File Property XML Node
     * @param property The File Property XML Node
     * @return A FileProperty Data Structure
     */
    private static FileProperty parseFilePropertyNode(Node property) {
        String propertyName = property.getNodeName().toLowerCase();
        String propertyValue = property.getTextContent();
        
        if(propertyName.equals("fileproperty") && property.hasAttributes()) {
            Node typeNode = property.getAttributes().getNamedItem("type");
            if(typeNode != null) {
                String typeValue = typeNode.getNodeValue().toLowerCase();
                switch (typeValue) {
                    case "contains":
                        return new ContainsFileProperty(propertyValue);
                    case "startswith":
                        return new StartsWithFileProperty(propertyValue);
                    case "filetype":
                        return new FileTypeFileProperty(propertyValue);
                }
            }
        }
        return null;
    }
}

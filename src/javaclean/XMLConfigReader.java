/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaclean;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.*;
import org.xml.sax.SAXException;
import org.w3c.dom.*;

/**
 *
 * @author ericlee
 */
public class XMLConfigReader {
    
    public static DirectoryStructure parseFile(String filename) {
        try {
            File configFile = new File(filename);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbf.newDocumentBuilder();
            
            Document parseDoc = dBuilder.parse(configFile);
            
            Node root = parseDoc.getElementsByTagName("directory").item(0);
            return XMLConfigReader.parseDirectoryNode(root, new FolderDirectoryStructure(""));
            
        } 
        
        catch (ParserConfigurationException | SAXException | IOException e) {
            System.err.println(e);
        }
        
        return null;
    }
    
    public static DirectoryStructure parseDirectoryNode(Node node, DirectoryStructure directory) {
        //Parese current node
        if(node.getNodeName().equals("directory")) {
            if(node.hasAttributes()) {
                Node typeNode = node.getAttributes().getNamedItem("type");
                DirectoryStructure dirStructure = null;
                if(typeNode != null) {
                    dirStructure = getDirectoryStructureForType(node, typeNode);
                    directory.subDirectories.add(dirStructure);
                }
                else {
                    Node nameNode = node.getAttributes().getNamedItem("name");
                    if(nameNode != null) {
                        dirStructure = new FolderDirectoryStructure(nameNode.getNodeValue());
                        directory.subDirectories.add(dirStructure);
                    }
                    else {
                        System.err.println("The directory element at " + node.getUserData("lineNumber") + " must have a name");
                    }
                }
                
                parseDirectoryChildren(node, dirStructure);
            }
        }
        
        else if(node.getNodeName().equals("file")) {
            FileStructure newFileStructure = parseFileNode(node);
            directory.fileStructures.add(newFileStructure);
        }
        return directory;
    }
    
    public static FileStructure parseFileNode(Node fileNode) {
        FileStructure file = new FileStructure();
        NodeList fileProperties = fileNode.getChildNodes();
        for(int i = 0; i < fileProperties.getLength(); i++) {
            FileProperty newProperty = parseFilePropertyNode(fileProperties.item(i));
            if(newProperty != null)
                file.properties.add(newProperty);
        }
        return file;
    }
    
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
        
        return returnStructure;
    }
    
    private static void parseDirectoryChildren(Node parentNode, DirectoryStructure parentDirectory) {
        NodeList children = parentNode.getChildNodes();
        for(int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            XMLConfigReader.parseDirectoryNode(child, parentDirectory);
        }
    }
    
    private static FileProperty parseFilePropertyNode(Node property) {
        String propertyName = property.getNodeName().toLowerCase();
        String propertyValue = property.getTextContent();
        
        if(propertyName.equals("contains"))
            return new ContainsFileProperty(propertyValue);
        else if(propertyName.equals("startswith"))
            return new StartsWithFileProperty(propertyValue);
        else if(propertyName.equals("filetype"))
            return new FileTypeFileProperty(propertyValue);
        else
            return null;
    }
}

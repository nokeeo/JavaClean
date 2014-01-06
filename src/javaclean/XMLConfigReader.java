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
                
                parseDirectoryChildren(node, dirStructure);
            }
        }
        return directory;
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
        
        if(type.equals("year")) {
            returnStructure = new YearDirectoryStructure(null);
        }
        
        return returnStructure;
    }
    
    private static void parseDirectoryChildren(Node parentNode, DirectoryStructure parentDirectory) {
        NodeList children = parentNode.getChildNodes();
        for(int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            XMLConfigReader.parseDirectoryNode(child, parentDirectory);
        }
    }
}

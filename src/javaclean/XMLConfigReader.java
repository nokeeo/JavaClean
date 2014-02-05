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
import org.xml.sax.SAXException;
import org.w3c.dom.*;

/**
 *
 * @author ericlee
 */
public class XMLConfigReader {
    
    public static DirectoryStructure parseFile(String filename) throws XMLParseException{        
        try {
            File configFile = new File(filename);
            
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(new File("src/javaclean/configSchema.xsd")));

            /*SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setNamespaceAware(true);
            spf.setSchema(schema);
            SAXParser parser = spf.newSAXParser();*/
            
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            dbf.setValidating(false);
            dbf.setSchema(schema);
            
            DocumentBuilder dBuilder = dbf.newDocumentBuilder();
            dBuilder.setErrorHandler(new XMLErrorHandler());
            
            //dBuilder.setErrorHandler(new XMLErrorHandler());
            
            Document parseDoc = dBuilder.parse(configFile);
            
            Node root = parseDoc.getElementsByTagName("directory").item(0);
            return XMLConfigReader.parseDirectoryNode(root, new FolderDirectoryStructure(""));
            
        } 
        catch (SAXException | ParserConfigurationException | IOException e) {
            throw new XMLParseException(e.getMessage());
        }
    }
    
    public static DirectoryStructure parseDirectoryNode(Node node, DirectoryStructure directory) {
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
    
    public static FileStructure parseFileNode(Node fileNode) {
        FileStructure file = new FileStructure();
        NodeList fileProperties = fileNode.getChildNodes();
        for(int i = 0; i < fileProperties.getLength(); i++) {
            FileProperty newProperty = parseFilePropertyNode(fileProperties.item(i));
            if(newProperty != null)
                file.addFileProperty(newProperty);
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
        
        else if(type.equals("day"))
            returnStructure = new DayDirectoryStructure(null);
        else if(type.equals("folder")) {
             Node nameNode = directoryNode.getAttributes().getNamedItem("name");
             if(nameNode != null) {
                 returnStructure = new FolderDirectoryStructure(nameNode.getNodeValue());
             }
             
             else
                 System.out.println("Folder directory needs a name!");
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

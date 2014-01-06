/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaclean;

import java.nio.file.Path;
import java.util.Arrays;

/**
 *
 * @author ericlee
 */
public class FileTypeDirectoryStructure extends DirectoryStructure {
    private String[] fileTypes;
        
    public FileTypeDirectoryStructure(String directoryName, String type){
        super(directoryName);
        
        if(type.equals("video"))
            this.fileTypes = this.getVideoFileTypes();
        else if(type.equals("picture"))
            this.fileTypes = this.getPictureFileTypes();
        else if(type.equals("audio"))
            this.fileTypes = this.getAudioFileTypes();
        else
            this.fileTypes = this.getDocumentFileTypes();
    }
    
    public boolean checkForPathMatch(Path originalPath) {
        String currentFileName = originalPath.getFileName().toString();
        int fileExtIndex = currentFileName.lastIndexOf(".");
        if(fileExtIndex == -1)
            return false;
        else {
            String fileExt = currentFileName.substring(fileExtIndex).toLowerCase();
            return Arrays.asList(this.fileTypes).contains(fileExt);
        }
    }
    
    protected String getCurrentDirectoryName(Path originalPath) {
        return this.directoryName;
    }
    
    private String[] getVideoFileTypes() {
        return new String[]{
            ".aaf",
            ".3gp", 
            ".asf", 
            ".avchd",
            ".avi",
            ".cam",
            ".flv",
            ".m1v",
            ".m2v",
            ".fla",
            ".flr",
            ".sol",
            ".m4v",
            ".mkv",
            ".wrap",
            ".mov",
            ".mpeg",
            ".mpg",
            ".mpe",
            ".mp4",
            ".mxf",
            ".roq",
            ".nsv",
            ".rm",
            ".svi",
            ".smi",
            ".swf",
            ".wmv",
            ".fcp",
            ".mswmm",
            ".ppj",
            ".imovieproj",
            ".veg",
            ".suf",
            ".wlmp"
        }; 
    }
    
    private String[] getPictureFileTypes() {
        return new String[] {
            ".ase",
            ".art",
            ".bmp",
            ".blp",
            ".cd5",
            ".cit",
            ".cpt",
            ".cr2",
            ".cut",
            ".dds",
            ".dib",
            ".djvu",
            ".egt",
            ".exif",
            ".gif",
            ".gpl",
            ".grf",
            ".icns",
            ".ico",
            ".iff",
            ".jng",
            ".jpeg",
            ".jpg",
            ".jp2",
            ".jps",
            ".lbm",
            ".max",
            ".miff",
            ".msp",
            ".nitf",
            ".ota",
            ".pbm",
            ".pc1", 
            ".pc2",
            ".pc3",
            ".pcf",
            ".pcx",
            ".pdn",
            ".pgm",
            ".pl1",
            ".pl2",
            ".pl3",
            ".pict",
            ".png",
            ".pnm",
            ".pns",
            ".ppm",
            ".psb",
            ".psd",
            ".pdd",
            ".psp",
            ".px",
            ".pxm",
            ".pxr",
            ".qfx",
            ".raw",
            ".rle",
            ".sct",
            ".sgi",
            ".tga",
            ".tiff",
            ".tif",
            ".vtf",
            ".xbm",
            ".xcf",
            ".xpm",
            ".ogg"
        };
    }
    
    private String[] getAudioFileTypes() {
        return new String[] {
            ".8svx",
            ".16svx",
            ".aiff",
            ".au",
            ".bwf",
            ".cdda",
            ".wav",
            ".flac",
            ".la",
            ".pac",
            ".m4a",
            ".ape",
            ".ofr",
            ".ofs",
            ".off",
            ".rka",
            ".shn",
            ".tta",
            ".wv",
            ".wma",
            ".brstm",
            ".ast",
            ".amr",
            ".mp2",
            ".mp3",
            ".spx",
            ".gsm",
            ".aac",
            ".mpc",
            ".vqf",
            ".ra",
            ".rm",
            ".ots",
            ".swa",
            ".vox",
            ".voc",
            ".dwd",
            ".smp",
            ".aup",
            ".band",
            ".cust",
            ".mid",
            ".mus",
            ".sib",
            ".sid",
            ".ly",
            ".gym",
            ".vgm",
            ".psf",
            ".nsf",
            ".mod",
            ".ptb",
            ".s3m",
            ".sx",
            ".it",
            ".mt2",
            ".mng",
            ".m4p"
        };
    }
    
    private String[] getDocumentFileTypes() {
        return new String[] {
            ".602",
            ".abw",
            ".acl",
            ".afp",
            ".ami",
            ".ans",
            ".asc",
            ".aww",
            ".ccf",
            ".csv",
            ".cwk",
            ".dbk",
            ".doc",
            ".docm",
            ".docx",
            ".dot",
            ".dotx",
            ".egt",
            ".epub",
            ".ezw",
            ".fdx",
            ".ftm",
            ".ftx",
            ".gdoc",
            ".html",
            ".htm",
            ".hwp",
            ".hwpml",
            ".lwp",
            ".mbp",
            ".mcw",
            ".mobi",
            ".nb",
            ".nbp",
            ".odm",
            ".odt",
            ".ott",
            ".omm",
            ".pages",
            ".pap",
            ".pdax",
            ".pdf",
            ".rtf",
            ".quox",
            ".rpt",
            ".sdw",
            ".stw",
            ".sxw",
            ".tex",
            ".troff",
            ".txt",
            ".uof",
            ".uoml",
            ".via",
            ".wpd",
            ".wps",
            ".wpt",
            ".wrd",
            ".wrf",
            ".wri",
            ".xhtml",
            ".xml",
            ".xps"
        };
    }
}

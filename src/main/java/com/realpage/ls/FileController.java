package com.realpage.ls;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;

public class FileController {
	final static Logger logger = Logger.getLogger(FileController.class);
	
	InputStream input = null;
	String filePath = null;
	
	public FileController(String filePath){
		this.filePath = filePath;
	}

	public String read(){
		String fileContent = "";
		
		// This will reference one line at a time
        String line = "";

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(filePath);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                fileContent += line;
            }   

            // Always close files.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            logger.debug("FileController: Unable to open file '" + filePath + "'");                
            logger.debug(ex.getCause());
            logger.debug(ex.getMessage());
        }
        catch(IOException ex) {
        	logger.debug("FileController: Error reading file '" + filePath + "'");                  
        }	
        
        logger.debug("FileController: Read from " + filePath);
        logger.debug("FileController: " + fileContent);
        return fileContent;
    }
}


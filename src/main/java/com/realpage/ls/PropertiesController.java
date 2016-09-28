package com.realpage.ls;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesController {
	final static Logger logger = Logger.getLogger(PropertiesController.class);
	
	public PropertiesController(){
	}
	
	public Properties getProperties(String filePath){
		Properties prop = new Properties();
		InputStream input = null;

		try {
			input = new FileInputStream(filePath);
			prop.load(input);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return prop;
	}
}

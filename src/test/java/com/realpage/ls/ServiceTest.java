package com.realpage.ls;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import com.realpage.ls.RestController;
import java.util.Properties;
import org.testng.Assert;
import static net.javacrumbs.jsonunit.JsonAssert.*;

public class ServiceTest{
	PropertiesController propsMan = new PropertiesController();
	RestController restMan = new RestController();
	FileController fileMan = null;
	String url = null;
	String authHeader = null;
	String payload = null;
	Properties props = null;	
	
	@BeforeSuite()
	public void setup(){
		props = propsMan.getProperties("config.properties");
		url = props.getProperty("host") + props.getProperty("endPointGet");	
		authHeader = props.getProperty("authHeader");
		restMan = new RestController(authHeader);
	}
	
	@Test
	public void testPositive() {
		//Expected response from service
		fileMan = new FileController("./data/output/out1.json");
		String expected = fileMan.read();
		
		//Actual response from service
		String actual = restMan.doGet(url);
		Assert.assertNotNull(actual);
		
		//Using JsonUnit for json comparison
		//https://github.com/lukas-krecan/JsonUnit
		assertJsonEquals(expected, actual);
	}

	
	@AfterSuite()
	public void cleanup(){
	}
}

package com.realpage.ls;


import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class RestController {
	final static Logger logger = Logger.getLogger(RestController.class);
	String authHeader = "";
	
	public RestController(){
	}
	public RestController(String authHeader){
		this.authHeader = authHeader;
	}

	public String doGet(String endPoint){
		String res = null;

		try {
			logger.debug("RestController: Calling endPoint: " + endPoint);
			Client client = Client.create();
			WebResource webResource = client.resource(endPoint);
			
			//Pattern: http://juristr.com/blog/2015/05/jersey-webresource-ignores-headers/
			WebResource.Builder builder = webResource.accept(MediaType.APPLICATION_JSON);
			builder.type(MediaType.APPLICATION_JSON);
			if(authHeader!="") 
				builder.header(HttpHeaders.AUTHORIZATION, authHeader);
			ClientResponse response = builder.get(ClientResponse.class);
			
			String error = handleError(response);
			if(error!=null) 
				res = error;
			else 
				res = response.getEntity(String.class);
		} catch (Exception e) {
			res = e.getMessage();
		}
		logger.debug("RestController: Response received.");
		logger.debug("RestController: Output from Server...");
		logger.debug("RestController: " + res);
		return res;	
	}
	public String doPost(String url, String payload) {
		String res = null;
		try{	
			logger.debug("RestController: Sending POST to : " + url);
			logger.debug("RestController: Payload: " + payload);
			Client client = Client.create();
			WebResource webResource = client.resource(url);
			ClientResponse response = webResource.type("application/json").post(ClientResponse.class, payload);

			String error = handleError(response);
			if(error!=null) 
				res = error;
			else 
				res = response.getEntity(String.class);
		} catch (Exception e) {
			res = e.getMessage();
		}

		logger.debug("RestController: Output from Server: ");
		logger.debug("RestController: " + res);
		return res;
	}
	private String handleError(ClientResponse response) {
		String ret = null;
		logger.debug("RestController: Status code = " + response.getStatus());
		switch(response.getStatus()){
		case 201:	
			break;
		case 200: 	
			break;
		case 400:
		{
			ret = response.getStatus() + " " + Response.Status.BAD_REQUEST.toString();
			break;
		}
		case 503:
		{
			ret = response.getStatus() + " " + Response.Status.SERVICE_UNAVAILABLE.toString();
			break;
		}
		default:	
			ret = Integer.toString(response.getStatus());
		}
		return ret;
	}

}

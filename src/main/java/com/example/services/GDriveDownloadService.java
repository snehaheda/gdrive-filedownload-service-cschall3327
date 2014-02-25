package com.example.services;



import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.PathParam;
import javax.ws.rs.HeaderParam;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;

import com.google.api.services.drive.model.File;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponse;


import java.io.IOException;

import java.io.InputStream;
import java.util.*;



import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;


@Path("/downloadfile")

public class GDriveDownloadService {

	@GET
	@Path("/{fileId}")
	public String  get(@HeaderParam("accessToken") String accessToken,@PathParam("fileId") String fileId) throws Exception {
		
		String msg = "Error!";
		
		//set the token
		GoogleTokenResponse response  = new GoogleTokenResponse();
		response.setAccessToken(accessToken);
		
		//build the credentials
		GoogleCredential credential = new GoogleCredential().setFromTokenResponse(response);
	    
		HttpTransport httpTransport = new NetHttpTransport();
	    JsonFactory jsonFactory = new JacksonFactory();
		
	    //Create a new authorized API client
	    Drive service = new Drive.Builder(httpTransport, jsonFactory, credential).build();
	    
	    File file = service.files().get(fileId).execute();
	    if (file.getDownloadUrl() != null && file.getDownloadUrl().length() > 0) {
	        try {
	          HttpResponse resp =
	              service.getRequestFactory().buildGetRequest(new GenericUrl(file.getDownloadUrl()))
	                  .execute();
	        
	             BufferedReader in = new BufferedReader(
	  		        new InputStreamReader(resp.getContent()));
		  		String inputLine;
		  		StringBuffer response2 = new StringBuffer();
		   
		  		while ((inputLine = in.readLine()) != null) {
		  			response2.append(inputLine);
		  		}
		  		in.close();
		  		return response2.toString();
	          
	        } catch (IOException e) {
	          // An error occurred.	         
	          return null;
	        }
	    }
	    return null;
	}

}


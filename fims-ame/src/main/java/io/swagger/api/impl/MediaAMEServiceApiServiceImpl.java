package io.swagger.api.impl;

import io.swagger.api.*;
import io.swagger.model.*;


import io.swagger.model.AmeJobType;

import java.util.List;
import io.swagger.api.NotFoundException;

import java.io.InputStream;
//FTT just a test to move from Oracle to OpenJDK
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

import java.net.*;
import java.nio.file.StandardCopyOption;
import java.io.IOException;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@RequestScoped
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2020-02-14T11:10:22.170Z")
public class MediaAMEServiceApiServiceImpl implements MediaAMEServiceApiService {
 

	
	  public Response mediaAMEServiceJobPost(AmeJobType createJobRequest,SecurityContext securityContext)
      throws NotFoundException {


      AmeJobType resultJob;
      try {
	      resultJob = createJobRequest;
	      
	      System.out.println("fetching url");
	      
	      // get URL 
	      String contentUrl = resultJob.getBmObject().getBmContents().get(0).getLocation();
	      
	      System.out.println("getting template url");
	      
	      // get template id
	      String templateId = resultJob.getProfile().getAmeTemplate().getAmeTemplateID();
	      
	      resultJob.setStatus(AmeJobType.StatusEnum.QUEUED);
	      
	      System.out.println("setting up worker");
	      
	      int newId = MediaAMEServiceJobRegistry.addJob(resultJob);
	
	      resultJob.setResourceID(new Integer(newId).toString());
	      
	      // start worker thread
	      MediaAMEServiceWorker worker = MediaAMEServiceJobRegistry.getWorker(newId);
	      
	      System.out.println("starting worker");
	      
	      worker.start();
	      
	      System.out.println("done with processing req.");
      }
      catch (Exception e) {
    	  System.out.println("error " + e.getMessage());
    	  return Response.serverError().entity(e.getMessage()).build();
      }
    
      System.out.println(resultJob.toString());
      return Response.ok().entity(resultJob.toString()).build();
  }

  public Response mediaAMEServiceUpdateModelPost(List<String> files,SecurityContext securityContext)
	throws NotFoundException {

	  	// FTT This code snippet is copied over from NESs user_extension/handle_updateModel_fragment.h
	    // Difference is, that NES uses mount points, and we use a webserver with urls

	  boolean error = false;
	  String errorStr = "unknown";
	  // FTT Source
	  String faceDBFilename = "unknown";
	  String classifierFilename = "unknown";

	  System.out.println("-------------------------Received model update ");

	  try {

		  for (int i = 0; i < files.size() && !error; ++i)
		  {
		  	  String f = files.get(i).toLowerCase();
			  if (f.endsWith(".orf"))
			  {
				  faceDBFilename = files.get(i);
				  // check if url is valid
				  try{
				  	URL test = new URL(faceDBFilename);
				  }
				  catch(Exception e) // e.g. MalformedURLException
				  {
				  	if(!error){
						error = true;
						errorStr = "E: ORF File: " + e.toString();
					}
				  }
			  }
			  else if (f.endsWith(".h5"))
			  {
				  classifierFilename = files.get(i);
				  try{
					  URL test = new URL(classifierFilename);
				  }
				  catch(Exception e) // e.g. MalformedURLException
				  {
					  if(!error){
						  error = true;
						  errorStr = "E: H5 File: " + e.toString();
					  }
				  }
			  }
			  else {
				  if(!error) {
					  error = true;
					  errorStr = "E: unknown file extension " + files.get(i);
				  }
			  }
		  }
	  }
      catch (Exception e) {
				System.out.println("error " + e.getMessage());
				return Response.serverError().entity("model update failed " + e.getMessage()).build();
	  }

	  if (error) {
		  return Response.status(415).entity("Error " + errorStr).build();
	  }

	  ModelUpdateThread t =  new ModelUpdateThread();
	  t.setData(files);
	  t.start();
	  return Response.ok().entity("model update started ").build();
	}
}

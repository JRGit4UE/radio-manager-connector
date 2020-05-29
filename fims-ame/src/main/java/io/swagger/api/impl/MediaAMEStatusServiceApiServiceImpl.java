package io.swagger.api.impl;

import io.swagger.api.*;
import io.swagger.model.*;


import io.swagger.model.AmeJobType;
import io.swagger.model.ManagequeueRequest;

import java.util.List;
import io.swagger.api.NotFoundException;

import java.io.InputStream;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@RequestScoped
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-06-06T10:05:32.593+02:00")
public class MediaAMEStatusServiceApiServiceImpl implements MediaAMEStatusServiceApiService {
 
	
	  public Response mediaAMEStatusServiceJobByJobIdGet(Integer jobId,Boolean details,SecurityContext securityContext)
      throws NotFoundException {
		  
		  AmeJobType job = MediaAMEServiceJobRegistry.getJob(jobId.intValue());
	      String s = "";
	      if (job != null) {
	    	  s = job.toString();
	    	  return Response.ok().entity(s).build();
	      }
	      else return Response.status(404).entity("No job with specified ID").build();
  }
      public Response mediaAMEStatusServiceJobGet(String jobIds,String fromDate,String toDate,Boolean includeActive,Boolean includeQueued,Boolean includeFinished,Boolean includeFailed,Integer maxNumberResults,Boolean details,SecurityContext securityContext)
      throws NotFoundException {
       	  
      String s = MediaAMEServiceJobRegistry.serialise();
    	  
      return Response.ok().entity(s).build();
  }
      public Response mediaAMEStatusServiceJobManageByJobIdGet(Integer jobId,SecurityContext securityContext)
      throws NotFoundException {
      
      AmeJobType job = MediaAMEServiceJobRegistry.getJob(jobId.intValue());
      String s = "[]";
      if (job != null) {
    	  s = job.toString();
    	  return Response.ok().entity(s).build();
      }
      else return Response.status(404).entity("No job with specified ID").build();
      
  }
      public Response mediaAMEStatusServiceJobManageByJobIdPost(AmeJobType manageJobRequest,Integer jobId,SecurityContext securityContext)
      throws NotFoundException {

      AmeJobType job = MediaAMEServiceJobRegistry.getJob(jobId.intValue());
      String s = "";
      if (job != null) {
    	  
    	  // check if status changed
    	  if (job.getStatus() != manageJobRequest.getStatus()) {
    		  
    		  MediaAMEServiceJobRegistry.updateStatus(jobId.intValue(),manageJobRequest.getStatus());
    		  
    		  s = MediaAMEServiceJobRegistry.getJob(jobId.intValue()).toString();
        	  return Response.ok().entity(s).build();
    		  
    	  }
    	  else {
    		  s = job.toString();
        	  return Response.ok().entity(s).build();
    	  }

      }
      else return Response.status(404).entity("No job with specified ID").build();


  }
      public Response mediaAMEStatusServiceQueueByQueueIdGet(Integer queueId,SecurityContext securityContext)
      throws NotFoundException {
      // TODO
      return Response.status(501).entity("not implemented").build();
  }
      public Response mediaAMEStatusServiceQueueGet(SecurityContext securityContext)
      throws NotFoundException {
       // TODO
       return Response.status(501).entity("not implemented").build();
  }
      public Response mediaAMEStatusServiceQueueManageByQueueIdGet(Integer queueId,SecurityContext securityContext)
      throws NotFoundException {
      // TODO
      return Response.status(501).entity("not implemented").build();
  }
      public Response mediaAMEStatusServiceQueueManageByQueueIdPost(ManagequeueRequest manageQueueRequest,Integer queueId,SecurityContext securityContext)
      throws NotFoundException {
      // TODO
      return Response.status(501).entity("not implemented").build();  
  }
      public Response mediaAMEStatusServiceQueueStatusByQueueIdGet(Integer queueId,SecurityContext securityContext)
      throws NotFoundException {
      // TODO
      return Response.status(501).entity("not implemented").build();
  }
}

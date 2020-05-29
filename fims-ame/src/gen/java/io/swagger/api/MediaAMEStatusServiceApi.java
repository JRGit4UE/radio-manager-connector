package io.swagger.api;

import io.swagger.model.*;
import io.swagger.api.MediaAMEStatusServiceApiService;
import io.swagger.api.impl.MediaAMEStatusServiceApiServiceImpl;

import io.swagger.annotations.ApiParam;
import io.swagger.jaxrs.*;

import io.swagger.model.AmeJobType;
import io.swagger.model.ManagequeueRequest;

import java.util.Map;
import java.util.List;
import io.swagger.api.NotFoundException;

import java.io.InputStream;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.*;
import javax.inject.Inject;

import javax.validation.constraints.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

@Path("/mediaAMEStatusService")
@Consumes({ "application/json" })
@Produces({ "application/json" })
@io.swagger.annotations.Api(description = "the mediaAMEStatusService API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-06-06T10:05:32.593+02:00")
public class MediaAMEStatusServiceApi  {

    @Inject MediaAMEStatusServiceApiService service = new MediaAMEStatusServiceApiServiceImpl();

    @GET
    @Path("/job/{jobId}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "retrieveJob", notes = "", response = AmeJobType.class, tags={  })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "", response = AmeJobType.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 200, message = "", response = Void.class) })
    public Response mediaAMEStatusServiceJobByJobIdGet( @PathParam("jobId") Integer jobId,  @QueryParam("details") Boolean details,@Context SecurityContext securityContext)
    throws NotFoundException {
        return service.mediaAMEStatusServiceJobByJobIdGet(jobId,details,securityContext);
    }
    @GET
    @Path("/job")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "retrieveJobs", notes = "", response = AmeJobType.class, responseContainer = "List", tags={  })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "", response = AmeJobType.class, responseContainer = "List"),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 200, message = "", response = Void.class) })
    public Response mediaAMEStatusServiceJobGet(  @QueryParam("jobIds") String jobIds,  @QueryParam("fromDate") String fromDate,  @QueryParam("toDate") String toDate,  @QueryParam("includeActive") Boolean includeActive,  @QueryParam("includeQueued") Boolean includeQueued,  @QueryParam("includeFinished") Boolean includeFinished,  @QueryParam("includeFailed") Boolean includeFailed,  @QueryParam("maxNumberResults") Integer maxNumberResults,  @QueryParam("details") Boolean details,@Context SecurityContext securityContext)
    throws NotFoundException {
    	
        return service.mediaAMEStatusServiceJobGet(jobIds,fromDate,toDate,includeActive,includeQueued,includeFinished,includeFailed,maxNumberResults,details,securityContext);
    }
    @GET
    @Path("/job/{jobId}/manage")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "retrieveJobStatus", notes = "", response = AmeJobType.class, tags={  })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "", response = AmeJobType.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 200, message = "", response = Void.class) })
    public Response mediaAMEStatusServiceJobManageByJobIdGet( @PathParam("jobId") Integer jobId,@Context SecurityContext securityContext)
    throws NotFoundException {
        return service.mediaAMEStatusServiceJobManageByJobIdGet(jobId,securityContext);
    }
    @POST
    @Path("/job/{jobId}/manageX")
    //@Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "manageJob", notes = "", response = AmeJobType.class, tags={  })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "", response = AmeJobType.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 200, message = "", response = Void.class) })
    public Response mediaAMEStatusServiceJobManageByJobIdPost(AmeJobType manageJobRequest, @PathParam("jobId") Integer jobId,@Context SecurityContext securityContext)
    throws NotFoundException {
        return service.mediaAMEStatusServiceJobManageByJobIdPost(manageJobRequest,jobId,securityContext);
    }
    @POST
    @Path("/job/{jobId}/manage")
    //@Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "manageJob", notes = "", response = AmeJobType.class, tags={  })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "", response = AmeJobType.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 200, message = "", response = Void.class) })
    public Response mediaAMEStatusServiceJobManageByJobIdPost(String s, @PathParam("jobId") Integer jobId,@Context SecurityContext securityContext)
    throws NotFoundException {
    	
    	AmeJobType manageJobRequest = null;
    	
    	try {
    		 ObjectMapper mapper = new ObjectMapper();
    		    //JsonNode actualObj = mapper.readTree(s);
    		
    		    
    		    manageJobRequest = mapper.readValue(s, AmeJobType.class);
    		    

    	}
    	catch (Exception e) {
    		Response.status(501).entity("Error parsing JSON: " + e.getMessage()).build();
    	}

    	
        return service.mediaAMEStatusServiceJobManageByJobIdPost(manageJobRequest,jobId,securityContext);
    }
    @GET
    @Path("/queue/{queueId}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "retrieveQueue", notes = "", response = Object.class, tags={  })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "", response = Object.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 200, message = "", response = Void.class) })
    public Response mediaAMEStatusServiceQueueByQueueIdGet( @PathParam("queueId") Integer queueId,@Context SecurityContext securityContext)
    throws NotFoundException {
        return service.mediaAMEStatusServiceQueueByQueueIdGet(queueId,securityContext);
    }
    @GET
    @Path("/queue")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "retrieveQueues", notes = "", response = Object.class, tags={  })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "", response = Object.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 200, message = "", response = Void.class) })
    public Response mediaAMEStatusServiceQueueGet(@Context SecurityContext securityContext)
    throws NotFoundException {
        return service.mediaAMEStatusServiceQueueGet(securityContext);
    }
    @GET
    @Path("/queue/{queueId}/manage")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "retrieveQueueForManagement", notes = "", response = Object.class, tags={  })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "", response = Object.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 200, message = "", response = Void.class) })
    public Response mediaAMEStatusServiceQueueManageByQueueIdGet( @PathParam("queueId") Integer queueId,@Context SecurityContext securityContext)
    throws NotFoundException {
        return service.mediaAMEStatusServiceQueueManageByQueueIdGet(queueId,securityContext);
    }
    @POST
    @Path("/queue/{queueId}/manage")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "manageQueue", notes = "", response = Object.class, tags={  })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "", response = Object.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 200, message = "", response = Void.class) })
    public Response mediaAMEStatusServiceQueueManageByQueueIdPost(@ApiParam(value = "" ,required=true) ManagequeueRequest manageQueueRequest, @PathParam("queueId") Integer queueId,@Context SecurityContext securityContext)
    throws NotFoundException {
        return service.mediaAMEStatusServiceQueueManageByQueueIdPost(manageQueueRequest,queueId,securityContext);
    }
    @GET
    @Path("/queue/{queueId}/status")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "retrieveQueueStatus", notes = "", response = Object.class, tags={  })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "", response = Object.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 200, message = "", response = Void.class) })
    public Response mediaAMEStatusServiceQueueStatusByQueueIdGet( @PathParam("queueId") Integer queueId,@Context SecurityContext securityContext)
    throws NotFoundException {
        return service.mediaAMEStatusServiceQueueStatusByQueueIdGet(queueId,securityContext);
    }
}

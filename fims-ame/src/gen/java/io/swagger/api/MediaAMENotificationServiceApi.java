package io.swagger.api;

import io.swagger.model.*;

import io.swagger.api.MediaAMENotificationServiceApiService;
import io.swagger.api.impl.MediaAMENotificationServiceApiServiceImpl;

import io.swagger.annotations.ApiParam;
import io.swagger.jaxrs.*;

import java.util.Map;
import java.util.List;
import io.swagger.api.NotFoundException;

import java.io.InputStream;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.*;
import javax.inject.Inject;

import javax.validation.constraints.*;

@Path("/mediaAMENotificationService")
@Consumes({ "application/json" })
@Produces({ "application/json" })
@io.swagger.annotations.Api(description = "the mediaAMENotificationService API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-06-06T10:05:32.593+02:00")
public class MediaAMENotificationServiceApi  {

    @Inject MediaAMENotificationServiceApiService service = new MediaAMENotificationServiceApiServiceImpl();

    @POST
    @Path("/notifyFaultX")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "notifyFaultX", notes = "", response = Object.class, tags={  })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "", response = Object.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 200, message = "", response = Void.class) })
    public Response mediaAMENotificationServiceNotifyFaultPost(@ApiParam(value = "" ,required=true) NotifyfaultRequest notifyFaultRequest,@Context SecurityContext securityContext)
    throws NotFoundException {
        return service.mediaAMENotificationServiceNotifyFaultPost(notifyFaultRequest,securityContext);
    }
    
    @POST
    @Path("/notifyFault")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "notifyFault", notes = "", response = Object.class, tags={  })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "", response = Object.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 200, message = "", response = Void.class) })
    public Response mediaAMENotificationServiceNotifyFaultPost(String s,@Context SecurityContext securityContext)
    throws NotFoundException {
    	
    	NotifyfaultRequest notifyFaultRequest = null;
    	
    	try {
    		 ObjectMapper mapper = new ObjectMapper();
    		    JsonNode actualObj = mapper.readTree(s);
    		 
    		    System.out.println("done w parsing");
    		    
    		    notifyFaultRequest = mapper.readValue(s, NotifyfaultRequest.class);
    		    
    		    System.out.println("parsed into obj");

    	}
    	catch (Exception e) {
    		return Response.status(501).entity("Error parsing JSON: " + e.getMessage()).build();
    	}
    	
        return service.mediaAMENotificationServiceNotifyFaultPost(notifyFaultRequest,securityContext);
    }
    
    @POST
    @Path("/notifymediaAMEResultX")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "notifymediaAMEResultX", notes = "", response = Object.class, tags={  })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "", response = Object.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 200, message = "", response = Void.class) })
    public Response mediaAMENotificationServiceNotifymediaAMEResultPost(@ApiParam(value = "" ,required=true) NotifymediaameresultRequest notifymediaAMEResultRequest,@Context SecurityContext securityContext)
    throws NotFoundException {
        return service.mediaAMENotificationServiceNotifymediaAMEResultPost(notifymediaAMEResultRequest,securityContext);
    }
    
    @POST
    @Path("/notifymediaAMEResult")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "notifymediaAMEResult", notes = "", response = Object.class, tags={  })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "", response = Object.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 200, message = "", response = Void.class) })
    public Response mediaAMENotificationServiceNotifymediaAMEResultPost(String s,@Context SecurityContext securityContext)
    throws NotFoundException {
    	
    	NotifymediaameresultRequest notifymediaAMEResultRequest = null;
    	
    	try {
    		 ObjectMapper mapper = new ObjectMapper();
    		    JsonNode actualObj = mapper.readTree(s);
    		 
    		    System.out.println("done w parsing (mar)");
    		    
    		    notifymediaAMEResultRequest = mapper.readValue(s, NotifymediaameresultRequest.class);
    		    
    		    System.out.println("parsed into obj");

    	}
    	catch (Exception e) {
    		return Response.status(501).entity("Error parsing JSON: " + e.getMessage()).build();
    	}
    	
        return service.mediaAMENotificationServiceNotifymediaAMEResultPost(notifymediaAMEResultRequest,securityContext);
    }
}

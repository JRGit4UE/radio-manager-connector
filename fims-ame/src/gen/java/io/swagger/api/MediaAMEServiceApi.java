package io.swagger.api;

import io.swagger.model.*;
import io.swagger.api.MediaAMEServiceApiService;
import io.swagger.api.impl.MediaAMEServiceApiServiceImpl;

import io.swagger.annotations.ApiParam;
import io.swagger.jaxrs.*;

import io.swagger.model.AmeJobType;

import java.util.Map;
import java.util.List;
import io.swagger.api.NotFoundException;

import java.io.InputStream;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.inject.Inject;

import javax.validation.constraints.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

//import io.swagger.api.impl.MediaAMEServiceWorkerConfig;
import java.util.ArrayList;


@Path("/mediaAMEService")
@io.swagger.annotations.Api(description = "the mediaAMEService API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-06-06T10:05:32.593+02:00")
public class MediaAMEServiceApi  {

    @Inject MediaAMEServiceApiService service = new MediaAMEServiceApiServiceImpl();

    @POST
    @Path("/jobX")
    //@Consumes({ "application/json" })
    @Produces({ "application/json" })
    public Response mediaAMEServiceJobPost(AmeJobType createJobRequest,@Context SecurityContext securityContext)
    throws NotFoundException {
    	System.out.println("addjobX");
    	
        return service.mediaAMEServiceJobPost(createJobRequest,securityContext);
    }
    
    @POST
    @Path("/job")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    public Response mediaAMEServiceJobPost(String s,@Context SecurityContext securityContext)
    throws NotFoundException {
    	System.out.println("addjob" + s);

//Just a local test to write a first draft of the config!!!
//        System.out.println("-------------------------------------------------");
//        MediaAMEServiceWorkerConfig.writeFile();
//        System.out.println(MediaAMEServiceWorkerConfig.getGstLibraryPath());
//        System.out.println("-------------------------------------------------");
//
//        if(0<1)return null;

        ModelUpdateStates state = ModelUpdateState.getInstance().getState();
        if(state != ModelUpdateStates.Ok) {
            if(state == ModelUpdateStates.InProgress) {
                System.out.println("Model update in progress");
                return Response.status(503).entity("{\"status\": \"Model update in progress\"}").build();
            }
            else if(state == ModelUpdateStates.Failed) {
                System.out.println("Model update failed");
                return Response.status(500).entity("{\"status\": \"Model update failed\"}").build();
            }
            else
                return Response.status(500).entity("{\"status\": \"Unknown model update state " + state.toString() + "\"}").build();
        }

        AmeJobType job = null;
    	
    	try {
    		 ObjectMapper mapper = new ObjectMapper();
    		    
    		 	System.out.println("received: "+s);
    		 
    		 	//JsonNode actualObj = mapper.readTree(s);
    		 
    		    
    		    job = mapper.readValue(s, AmeJobType.class);
    		    
    		    System.out.println("parsed into obj");

    	}
    	catch (Exception e) {
    		System.out.println("Error parsing JSON: " + e.getMessage());
    		return Response.status(501).entity("Error parsing JSON: " + e.getMessage()).build();
    	}
    	
        return service.mediaAMEServiceJobPost(job,securityContext);
    }

    @POST
    @Path("/model/update")
    @Consumes({ "application/json" })
    @Produces({ "text/plain; charset=utf-8" })
    @io.swagger.annotations.ApiOperation(value = "updateModel", notes = "Notify worker to update model", response = Object.class, tags={  })
    @io.swagger.annotations.ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 200, message = "Ok", response = Object.class),

            @io.swagger.annotations.ApiResponse(code = 500, message = "Model updated failed", response = Object.class),

            @io.swagger.annotations.ApiResponse(code = 503, message = "Model updated running", response = Object.class),

            @io.swagger.annotations.ApiResponse(code = 200, message = "", response = Object.class) })

    public Response mediaAMEServiceUpdateModelPost(@ApiParam(value = "JSON object containing the list of file paths to be used" ,required=true) String json,@Context SecurityContext securityContext)
            throws NotFoundException {
        System.out.println("received: " + json);
        try {
            ObjectMapper mapper = new ObjectMapper();
            //String json = "{ \"files\": [\"file:///c:/test1.orf\", \"file:///c:/test1.h5\" ] }";
            JsonNode root = mapper.readTree(json);
            JsonNode n = root.get("files");
            List<String> files = mapper.convertValue(n, ArrayList.class);
            return service.mediaAMEServiceUpdateModelPost(files,securityContext);
        }
        catch (Exception e) {
            System.out.println("Error parsing JSON: " + e.getMessage());
            return Response.status(501).entity("Error parsing JSON: " + e.getMessage()).build();
        }
    }
}

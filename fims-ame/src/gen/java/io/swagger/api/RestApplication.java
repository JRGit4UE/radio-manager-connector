package io.swagger.api;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import io.swagger.api.impl.*;

@ApplicationPath("/v2")
public class RestApplication extends Application {

    public Set<Class<?>> getClasses() {
        return new HashSet<Class<?>>(Arrays.asList(
        		MediaAMEServiceApi.class, 
        		MediaAMEServiceApiServiceImpl.class, 
        		MediaAMEStatusServiceApi.class,
        		MediaAMEStatusServiceApiServiceImpl.class,
        		MediaAMENotificationServiceApi.class,
        		MediaAMENotificationServiceApiServiceImpl.class));
    }
    
    /*
    public Set<Object> getSingletons() {
    	Set<Object> singletons=new HashSet<>();
        singletons.add(new MediaAMEServiceJobRegistry());
        return singletons;
        		
    }
    */
}
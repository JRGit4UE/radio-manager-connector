package io.swagger.api;

import io.swagger.api.*;
import io.swagger.model.*;


import io.swagger.model.AmeJobType;

import java.util.List;
import io.swagger.api.NotFoundException;

import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-06-06T10:05:32.593+02:00")
public interface MediaAMEServiceApiService {
      Response mediaAMEServiceJobPost(AmeJobType createJobRequest,SecurityContext securityContext)
      throws NotFoundException;

      Response mediaAMEServiceUpdateModelPost(List<String> files,SecurityContext securityContext)
      throws NotFoundException;
}

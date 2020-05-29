package io.swagger.api;

import io.swagger.api.*;
import io.swagger.model.*;


import io.swagger.model.AmeJobType;
import io.swagger.model.ManagequeueRequest;

import java.util.List;
import io.swagger.api.NotFoundException;

import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-06-06T10:05:32.593+02:00")
public interface MediaAMEStatusServiceApiService {
      Response mediaAMEStatusServiceJobByJobIdGet(Integer jobId,Boolean details,SecurityContext securityContext)
      throws NotFoundException;
      Response mediaAMEStatusServiceJobGet(String jobIds,String fromDate,String toDate,Boolean includeActive,Boolean includeQueued,Boolean includeFinished,Boolean includeFailed,Integer maxNumberResults,Boolean details,SecurityContext securityContext)
      throws NotFoundException;
      Response mediaAMEStatusServiceJobManageByJobIdGet(Integer jobId,SecurityContext securityContext)
      throws NotFoundException;
      Response mediaAMEStatusServiceJobManageByJobIdPost(AmeJobType manageJobRequest,Integer jobId,SecurityContext securityContext)
      throws NotFoundException;
      Response mediaAMEStatusServiceQueueByQueueIdGet(Integer queueId,SecurityContext securityContext)
      throws NotFoundException;
      Response mediaAMEStatusServiceQueueGet(SecurityContext securityContext)
      throws NotFoundException;
      Response mediaAMEStatusServiceQueueManageByQueueIdGet(Integer queueId,SecurityContext securityContext)
      throws NotFoundException;
      Response mediaAMEStatusServiceQueueManageByQueueIdPost(ManagequeueRequest manageQueueRequest,Integer queueId,SecurityContext securityContext)
      throws NotFoundException;
      Response mediaAMEStatusServiceQueueStatusByQueueIdGet(Integer queueId,SecurityContext securityContext)
      throws NotFoundException;
}

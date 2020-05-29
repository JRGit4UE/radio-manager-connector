package io.swagger.api;

import io.swagger.api.*;
import io.swagger.model.*;


import io.swagger.model.NotifyfaultRequest;
import io.swagger.model.NotifymediaameresultRequest;

import java.util.List;
import io.swagger.api.NotFoundException;

import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-06-06T10:05:32.593+02:00")
public interface MediaAMENotificationServiceApiService {
      Response mediaAMENotificationServiceNotifyFaultPost(NotifyfaultRequest notifyFaultRequest,SecurityContext securityContext)
      throws NotFoundException;
      Response mediaAMENotificationServiceNotifymediaAMEResultPost(NotifymediaameresultRequest notifymediaAMEResultRequest,SecurityContext securityContext)
      throws NotFoundException;
}

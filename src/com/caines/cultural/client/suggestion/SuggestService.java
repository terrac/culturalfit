package com.caines.cultural.client.suggestion;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Request;
import com.google.gwt.user.client.ui.SuggestOracle.Response;

public interface SuggestService extends RemoteService {

public static class Util {
  public static SuggestServiceAsync getInstance() {
   SuggestServiceAsync instance=(SuggestServiceAsync) GWT.create(SuggestService.class);
   ServiceDefTarget target = (ServiceDefTarget) instance;
   target.setServiceEntryPoint("/culturalfit/suggest");
   return instance;
  }
}

   public SuggestOracle.Response getGroup(SuggestOracle.Request req);

   Response getTopTags(Request req);
}


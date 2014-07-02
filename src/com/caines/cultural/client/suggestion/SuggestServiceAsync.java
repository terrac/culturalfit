package com.caines.cultural.client.suggestion;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SuggestOracle.Request;
import com.google.gwt.user.client.ui.SuggestOracle.Response;

public interface SuggestServiceAsync {

   public void getGroup(Request req, AsyncCallback<Response> callback);

   void getTopTags(Request req, AsyncCallback<Response> callback);
}
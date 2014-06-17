package com.caines.cultural.client.suggestion;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SuggestOracle;

public abstract class ItemSuggestOracle extends SuggestOracle {

       public boolean isDisplayStringHTML() {
           return true;
       }

       public void onNumberSuggestions(int amount){
    	   
       }
       public class ItemSuggestCallback implements AsyncCallback {

           private SuggestOracle.Request req;
           private SuggestOracle.Callback callback;

           public ItemSuggestCallback(SuggestOracle.Request _req,
                   SuggestOracle.Callback _callback) {
               req = _req;
               callback = _callback;
           }

           public void onFailure(Throwable error) {
               callback.onSuggestionsReady(req, new SuggestOracle.Response());
           }

           public void onSuccess(Object retValue) {
               SuggestOracle.Response retValue2 = (SuggestOracle.Response) retValue;
               onNumberSuggestions(retValue2.getSuggestions().size()); 
               
			callback.onSuggestionsReady(req,
                       retValue2);
           }
       }
   }
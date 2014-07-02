package com.caines.cultural.client.suggestion;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SuggestOracle;

public abstract class ItemSuggestOracle extends SuggestOracle {

	public boolean isDisplayStringHTML() {
		return true;
	}

	public void onExists(String name) {

	}

	public class ItemSuggestCallback implements AsyncCallback<SuggestOracle.Response> {

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

		@Override
		public void onSuccess(Response result) {
			
			checkExists(result);
			callback.onSuggestionsReady(req, result);
		}


		
	}

	public void checkExists(Response retValue2) {
		
	}
}
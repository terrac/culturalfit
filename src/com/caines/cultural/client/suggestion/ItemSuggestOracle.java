package com.caines.cultural.client.suggestion;

import com.caines.cultural.shared.datamodel.Group;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Response;

public abstract class ItemSuggestOracle extends SuggestOracle {

	public boolean isDisplayStringHTML() {
		return true;
	}

	public void onExists(String name) {

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

			checkExists(retValue2);
			callback.onSuggestionsReady(req, retValue2);
		}

		
	}

	public void checkExists(Response retValue2) {
		
	}
}
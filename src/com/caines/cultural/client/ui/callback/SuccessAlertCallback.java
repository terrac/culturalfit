package com.caines.cultural.client.ui.callback;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class SuccessAlertCallback implements AsyncCallback<Void> {

	String message;

	public SuccessAlertCallback(String message) {
		this.message = message;
	}

	@Override
	public void onFailure(Throwable caught) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSuccess(Void result) {
		Window.alert(message);
	}

}

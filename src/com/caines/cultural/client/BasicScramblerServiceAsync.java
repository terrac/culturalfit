package com.caines.cultural.client;

import com.caines.cultural.shared.container.ScramblerQuestion;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface BasicScramblerServiceAsync {

	void addCodePage(String url, String tags, AsyncCallback<Void> callback);

	void answerQuestion(String id, AsyncCallback<Void> callback);

	void getNextQuestion(AsyncCallback<ScramblerQuestion> callback);

}

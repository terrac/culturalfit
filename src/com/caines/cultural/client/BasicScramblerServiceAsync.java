package com.caines.cultural.client;

import java.util.List;

import com.caines.cultural.shared.container.ScramblerQuestion;
import com.caines.cultural.shared.datamodel.codingscramble.CodeUserDetails;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface BasicScramblerServiceAsync {

	void addCodePage(String url, String tags, AsyncCallback<Void> callback);

	void answerQuestion(String id, AsyncCallback<Void> callback);

	void getNextQuestion(AsyncCallback<ScramblerQuestion> callback);

	void getProfileContent(AsyncCallback<List<CodeUserDetails>> callback);

	

}

package com.caines.cultural.client;

import java.util.List;

import com.caines.cultural.shared.datamodel.Question;
import com.caines.cultural.shared.datamodel.Tag;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void getNextQuestion(AsyncCallback<Question> question);

	void getTopTags(String text,AsyncCallback<List<Tag>> question);

	void addQuestion(String text, String text2, String text3, String text4, AsyncCallback<String> callback);

	void answerQuestion(String answer, AsyncCallback<Void> callback);

	void addGroup(String value,AsyncCallback<Void> callback);

}

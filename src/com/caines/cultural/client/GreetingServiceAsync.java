package com.caines.cultural.client;

import java.util.List;

import com.caines.cultural.shared.datamodel.Group;
import com.caines.cultural.shared.datamodel.Question;
import com.caines.cultural.shared.datamodel.Tag;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void getNextQuestion(AsyncCallback<Question> question);

	void addQuestion( String question, String rAnswer, String wAnswer,String tags, AsyncCallback<String> callback);

	void answerQuestion(String answer, AsyncCallback<Void> callback);

	void addGroup(String value,AsyncCallback<Void> callback);

	void getTopGroups(AsyncCallback<List<Group>> callback);

	void setCurrentGroup(String text, AsyncCallback<Void> callback);

	void getCurrentGroup(AsyncCallback<Group> callback);

	void getQuestionToEdit(Long questionKey, AsyncCallback<Question> question);

	
	void updateQuestion(Long questionKey, String value,
			AsyncCallback<Void> asyncCallback);

}

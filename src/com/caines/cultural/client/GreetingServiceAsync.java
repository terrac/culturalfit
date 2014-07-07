package com.caines.cultural.client;

import java.util.List;

import com.caines.cultural.shared.Tuple;
import com.caines.cultural.shared.datamodel.GUser;
import com.caines.cultural.shared.datamodel.Group;
import com.caines.cultural.shared.datamodel.Location;
import com.caines.cultural.shared.datamodel.Question;
import com.caines.cultural.shared.datamodel.UserGroup;
import com.caines.cultural.shared.datamodel.clientserver.SharedUserProfile;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.googlecode.objectify.Ref;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void getNextQuestion(AsyncCallback<Question> question);

	void addQuestion(String question, String rAnswer, String wAnswer,
			String tags, AsyncCallback<String> callback);

	void answerQuestion(Long id, String answer, AsyncCallback<Void> callback);

	void addGroup(String value, AsyncCallback<Void> callback);

	void getTopGroups(AsyncCallback<List<Group>> callback);

	void setCurrentGroup(String text, AsyncCallback<Group> asyncCallback);

	void getCurrentGroup(AsyncCallback<Group> callback);

	void getQuestionToEdit(Long questionRef, AsyncCallback<Question> question);

	void updateQuestion(Long questionRef, String value,
			AsyncCallback<Void> asyncCallback);


	void getQuestionList(Ref<Group> group,
			AsyncCallback<List<Question>> callback);

	void disableQuestion(Long questionRef, AsyncCallback<Void> callback);

	void sendProfile(int salary, long lVal, AsyncCallback<Void> callback);

	void getUserProfile(AsyncCallback<SharedUserProfile> callback);

	void setLocation(long location, AsyncCallback<Void> callback);

	void getProfileData(
			AsyncCallback<Tuple<SharedUserProfile, List<Location>>> callback);


	void getLogInOutString(
			AsyncCallback<Tuple<Group, Tuple<String, Boolean>>> asyncCallback);
	
	void editGroup(String groupName, AsyncCallback<Tuple<Group,String>> callback);

	void getUserGroupList(long id,
			AsyncCallback<List<UserGroup>> callback);

	void getUserGroupList(AsyncCallback<List<UserGroup>> callback);

	void getTemporaryQuestions(AsyncCallback<List<Question>> callback);

	void addPermanentQuestion(long id, boolean shouldAdd,
			AsyncCallback<Void> callback);

	void getQuestionList(AsyncCallback<List<Question>> asyncCallback);

}

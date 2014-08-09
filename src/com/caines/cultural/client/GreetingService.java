package com.caines.cultural.client;

import java.util.List;

import com.caines.cultural.shared.Tuple;
import com.caines.cultural.shared.datamodel.GUser;
import com.caines.cultural.shared.datamodel.Group;
import com.caines.cultural.shared.datamodel.Location;
import com.caines.cultural.shared.datamodel.Question;
import com.caines.cultural.shared.datamodel.UserGroup;
import com.caines.cultural.shared.datamodel.clientserver.SharedUserProfile;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.googlecode.objectify.Ref;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {

	Tuple<Group, Question> getNextQuestion();


	String addQuestion(String question, String rAnswer, String wAnswer,
			String tags);


	Group addGroup(String value);

	List<Group> getTopGroups();

	Group setCurrentGroup(String text);


	Group getCurrentGroup();




	Question getQuestionToEdit(Long questionRef);



	void updateQuestion(Long questionRef, String value);


	void answerQuestion(Long id, String answer);




	void disableQuestion(Long questionRef);


	
	SharedUserProfile getUserProfile();


	



	Tuple<SharedUserProfile, List<Location>> getProfileData();





	Tuple<Group, Tuple<String, Boolean>> getLogInOutString();


	Tuple<Group, String> editGroup(String groupName);


	void sendProfile(int salary, long lVal);


	void setLocation(long location);


	List<UserGroup> getUserGroupList(long id);


	List<UserGroup> getUserGroupList();


	List<Question> getTemporaryQuestions();



	void addPermanentQuestion(long id, boolean shouldAdd);


	List<Question> getQuestionList(Ref<Group> group);


	List<Question> getQuestionList();


	Group addTry(long userGroupId);




}

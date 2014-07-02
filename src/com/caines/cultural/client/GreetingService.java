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

	Question getNextQuestion();


	String addQuestion(String question, String rAnswer, String wAnswer,
			String tags);


	void addGroup(String value);

	List<Group> getTopGroups();

	void setCurrentGroup(String text);


	Group getCurrentGroup();




	Question getQuestionToEdit(Long questionRef);



	void updateQuestion(Long questionRef, String value);


	List<UserGroup> getUserGroupList();


	void answerQuestion(Long id, String answer);


	List<Question> getQuestionList();


	void disableQuestion(Long questionRef);


	
	SharedUserProfile getUserProfile();


	



	Tuple<SharedUserProfile, List<Location>> getProfileData();



	List<UserGroup> getUserGroupList(Ref<GUser> Ref);


	Tuple<String, Boolean> getLogInOutString();


	String editGroup(String groupName);


	void sendProfile(int salary, long lVal);


	void setLocation(long location);




}

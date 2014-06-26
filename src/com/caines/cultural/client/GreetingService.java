package com.caines.cultural.client;

import java.util.Collection;
import java.util.List;

import com.caines.cultural.shared.Tuple;
import com.caines.cultural.shared.datamodel.Group;
import com.caines.cultural.shared.datamodel.Location;
import com.caines.cultural.shared.datamodel.Question;
import com.caines.cultural.shared.datamodel.Tag;
import com.caines.cultural.shared.datamodel.UserGroup;
import com.caines.cultural.shared.datamodel.UserProfile;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

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




	Question getQuestionToEdit(Long questionKey);



	void updateQuestion(Long questionKey, String value);


	List<UserGroup> getUserGroupList();


	void answerQuestion(Long id, String answer);


	List<Question> getQuestionList();


	void disableQuestion(Long questionKey);


	void sendProfile(UserProfile userProfile);


	UserProfile getUserProfile();


	

	void setLocation(long locationKey);


	Tuple<UserProfile,List<Location>> getProfileData();


}

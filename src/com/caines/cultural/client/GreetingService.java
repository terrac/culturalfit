package com.caines.cultural.client;

import java.util.List;

import com.caines.cultural.shared.datamodel.Group;
import com.caines.cultural.shared.datamodel.Question;
import com.caines.cultural.shared.datamodel.Tag;
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

	void answerQuestion(String answer);

	void addGroup(String value);

	List<Group> getTopGroups();

	void setCurrentGroup(String text);


	Group getCurrentGroup();




	Question getQuestionToEdit(Long questionKey);



	void updateQuestion(Long questionKey, String value);
}

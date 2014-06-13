package com.caines.cultural.client;

import java.util.List;

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

	List<Tag> getTopTags(String text);

	String addQuestion(String text, String text2, String text3, String text4);

	void answerQuestion(String answer);

	void addGroup(String value);
}

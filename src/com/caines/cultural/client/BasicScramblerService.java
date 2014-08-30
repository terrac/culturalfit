package com.caines.cultural.client;

import java.util.List;

import com.caines.cultural.shared.Tuple;
import com.caines.cultural.shared.container.ScramblerQuestion;
import com.caines.cultural.shared.datamodel.UserGroup;
import com.caines.cultural.shared.datamodel.UserProfile;
import com.caines.cultural.shared.datamodel.clientserver.SharedUserProfile;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("employer")
public interface BasicScramblerService extends RemoteService {

	ScramblerQuestion getNextQuestion();
	void answerQuestion(String id);
	void addCodePage(String url,String tags);

}

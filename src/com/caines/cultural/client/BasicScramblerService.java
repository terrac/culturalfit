package com.caines.cultural.client;

import java.util.List;

import com.caines.cultural.shared.Tuple;
import com.caines.cultural.shared.UserInfo;
import com.caines.cultural.shared.container.SContainer;
import com.caines.cultural.shared.container.ScramblerQuestion;
import com.caines.cultural.shared.datamodel.GUser;
import com.caines.cultural.shared.datamodel.UserProfile;
import com.caines.cultural.shared.datamodel.codingscramble.CodeContainer;
import com.caines.cultural.shared.datamodel.codingscramble.CodeLinkContainer;
import com.caines.cultural.shared.datamodel.codingscramble.CodeUserDetails;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("basic")
public interface BasicScramblerService extends RemoteService {

	ScramblerQuestion getNextLines();

	
	void addCodePage(String url,String tags);
	List<CodeUserDetails> getProfileContent();

	UserInfo getUserInfo();
	
	Tuple<CodeContainer, List<CodeLinkContainer>> getContainer(String associatedUrl);

	void linkCode(String linkType);
	
	
	
}

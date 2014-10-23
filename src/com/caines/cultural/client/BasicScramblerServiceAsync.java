package com.caines.cultural.client;

import java.util.List;

import com.caines.cultural.shared.Tuple;
import com.caines.cultural.shared.UserInfo;
import com.caines.cultural.shared.container.ScramblerQuestion;
import com.caines.cultural.shared.datamodel.codingscramble.CodeContainer;
import com.caines.cultural.shared.datamodel.codingscramble.CodeLinkContainer;
import com.caines.cultural.shared.datamodel.codingscramble.CodeUserDetails;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface BasicScramblerServiceAsync {

	void addCodePage(String url, String tags, AsyncCallback<Void> callback);

	void getProfileContent(AsyncCallback<List<CodeUserDetails>> callback);

	void getUserInfo(AsyncCallback<UserInfo> callback);

	void getNextLines(String tag, AsyncCallback<ScramblerQuestion> callback);

	void linkCode(String linkType, AsyncCallback<Void> callback);

	void getContainer(
			String associatedUrl,
			AsyncCallback<Tuple<CodeContainer, List<CodeLinkContainer>>> callback);

}

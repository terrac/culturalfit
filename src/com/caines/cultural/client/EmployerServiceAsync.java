package com.caines.cultural.client;

import java.util.List;

import com.caines.cultural.shared.datamodel.UserProfile;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface EmployerServiceAsync {

	void getBasicQueryWithFilters(AsyncCallback<List<UserProfile>> callback);

}

package com.caines.cultural.client;

import java.util.List;

import com.caines.cultural.shared.Tuple;
import com.caines.cultural.shared.datamodel.UserGroup;
import com.caines.cultural.shared.datamodel.UserProfile;
import com.caines.cultural.shared.datamodel.clientserver.SharedUserProfile;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface EmployerServiceAsync {

	void getBasicQueryWithFilters(String location,
			String groupName, AsyncCallback<Tuple<List<SharedUserProfile>, List<UserGroup>>> asyncCallback);

}

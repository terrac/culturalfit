package com.caines.cultural.client;

import java.util.List;

import com.caines.cultural.shared.Tuple;
import com.caines.cultural.shared.datamodel.UserGroup;
import com.caines.cultural.shared.datamodel.UserProfile;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface EmployerServiceAsync {

	void getBasicQueryWithFilters(String location,
			AsyncCallback<Tuple<List<UserProfile>, List<UserGroup>>> callback);

}

package com.caines.cultural.server;

import java.util.ArrayList;
import java.util.List;

import com.caines.cultural.client.EmployerService;
import com.caines.cultural.server.location.RadiusBox;
import com.caines.cultural.shared.LoginInfo;
import com.caines.cultural.shared.datamodel.UserProfile;
import com.caines.cultural.shared.datamodel.ZipCode;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class EmployerImpl extends RemoteServiceServlet implements
		EmployerService {

	@Override
	public List<UserProfile> getBasicQueryWithFilters() {
		LoginInfo li = LoginService.login(null, null);
		RadiusBox rb = new RadiusBox();
		ZipCode zc = SDao.getUserProfileDao().getByProperty("user",li.gUser.getKey()).zipCode;
		rb.create(zc., longitude, 5);
		
		return null;
	}


}

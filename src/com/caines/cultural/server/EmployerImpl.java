package com.caines.cultural.server;

import java.util.ArrayList;
import java.util.List;

import com.caines.cultural.client.EmployerService;
import com.caines.cultural.server.location.RadiusBox;
import com.caines.cultural.shared.LoginInfo;
import com.caines.cultural.shared.Tuple;
import com.caines.cultural.shared.datamodel.GUser;
import com.caines.cultural.shared.datamodel.Location;
import com.caines.cultural.shared.datamodel.UserGroup;
import com.caines.cultural.shared.datamodel.UserProfile;
import com.caines.cultural.shared.datamodel.ZipCode;
import com.google.appengine.api.users.User;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class EmployerImpl extends RemoteServiceServlet implements
		EmployerService {

	@Override
	public Tuple<List<UserProfile>,List<UserGroup>> getBasicQueryWithFilters(String location) {
		LoginInfo li = LoginService.login(null, null);

		//probably will cache this query and then do other queries off of it (invalidating
		//once a day or something)
		Key<Location> key = Location.getKey(Long.parseLong(location));
		
		
		List<UserGroup> ugList = SDao.getUserGroupDao().getQuery().filter("locationMapping", key).order("correct").list();
		
		List<Key<GUser>> uList = new ArrayList<>();
		for(UserGroup ug : ugList){
			uList.add(ug.user);
		}
		
		return new Tuple(SDao.getUserProfileDao().getQuery().filter("user in", uList).list(),ugList);
		//return upList;
	}


}

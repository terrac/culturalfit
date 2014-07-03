package com.caines.cultural.server;

import java.util.ArrayList;
import java.util.List;

import com.caines.cultural.client.EmployerService;
import com.caines.cultural.shared.Tuple;
import com.caines.cultural.shared.datamodel.GUser;
import com.caines.cultural.shared.datamodel.Group;
import com.caines.cultural.shared.datamodel.Location;
import com.caines.cultural.shared.datamodel.UserGroup;
import com.caines.cultural.shared.datamodel.UserProfile;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Ref;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class EmployerImpl extends RemoteServiceServlet implements
		EmployerService {

	@Override
	public Tuple<List<UserProfile>, List<UserGroup>> getBasicQueryWithFilters(
			String location, String groupName) {
		// LoginInfo li = LoginService.login(null, null);

		// probably will cache this query and then do other queries off of it
		// (invalidating
		// once a day or something)
		Ref<Location> Ref = Location.getRef(Long.parseLong(location));

		// Ref<Group> gRef=SDao.getGroupDao().getQuery().get().getRef();
		// System.out.println(SDao.getGroupDao().getQuery().list());
		// System.out.println();

		Ref<Group> gRef = SDao.getGroupDao().getQuery()
				.filter("lowerName", groupName.toLowerCase()).first().now()
				.getRef();
		
		List<UserGroup> ugList = SDao.getUserGroupDao().getQuery()
				.filter("group", gRef).filter("locationMapping", Ref)
				.order("-correct").list();
		List<Ref<UserProfile>> uList = new ArrayList<>();
		for (UserGroup ug : ugList) {
			uList.add(ug.userProfile);
		}

		List<UserProfile> list=new ArrayList<UserProfile>(Dao.deref(new ArrayList<Ref<UserProfile>>(OService.ofy().load().entities(uList).values())));
		return new Tuple<List<UserProfile>, List<UserGroup>>(list,
				new ArrayList<>(ugList));
		// return upList;
	}

}

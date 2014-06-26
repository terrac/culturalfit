package com.caines.cultural.server;

import java.util.ArrayList;
import java.util.List;

import com.caines.cultural.client.EmployerService;
import com.caines.cultural.server.location.RadiusBox;
import com.caines.cultural.shared.LoginInfo;
import com.caines.cultural.shared.datamodel.UserProfile;
import com.caines.cultural.shared.datamodel.ZipCode;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.ObjectifyService;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class EmployerImpl extends RemoteServiceServlet implements
		EmployerService {

	@Override
	public List<UserProfile> getBasicQueryWithFilters(String zip, String radius) {
		int rad = Integer.parseInt(radius);
		
		LoginInfo li = LoginService.login(null, null);
//		RadiusBox rb = new RadiusBox();
//		ZipCode zc = SDao.getZipCodeDao().getByProperty("zip",zip);
//		rb.create(zc.lattitude, zc.longitude, Double.parseDouble(radius));
//		List<ZipCode> zcList = rb.getQuery();
//		List<UserProfile> upList = new ArrayList<UserProfile>();
//		for(ZipCode z : zcList){
//			upList.addAll(SDao.getUserProfileDao().listByProperty("zipCode", z));
//		}
		//probably will cache this query and then do other queries off of it (invalidating
		//once a day or something)
		return SDao.getUserProfileDao().listByProperty("zipCode", SDao.getZipCodeDao().getByProperty("zip",zip));
		//return upList;
	}


}

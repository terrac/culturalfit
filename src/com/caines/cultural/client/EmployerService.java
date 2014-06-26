package com.caines.cultural.client;

import java.util.List;

import com.caines.cultural.shared.datamodel.UserProfile;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("employer")
public interface EmployerService extends RemoteService {

	List<UserProfile> getBasicQueryWithFilters(String zip, String string);

}

package com.caines.cultural.shared;

import com.caines.cultural.shared.datamodel.GUser;
import com.google.appengine.api.users.User;

public class LoginInfo {

	public boolean loggedIn;
	public User user;
	public String logoutUrl;
	public String loginUrl;
	public GUser gUser;

}

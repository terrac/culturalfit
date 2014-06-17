package com.caines.cultural.server;


import java.io.PrintWriter;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServletRequest;





import com.caines.cultural.shared.LoginInfo;
import com.caines.cultural.shared.SideBar;
import com.caines.cultural.shared.datamodel.GUser;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.Key;

public class LoginService {



	public static LoginInfo login(String requestUri,PrintWriter writer
			) {
		return login(requestUri, writer, null);
	}
		public static LoginInfo login(String requestUri, PrintWriter writer
				,HttpServletRequest req) {
		
		
		GUser per = null;
		UserService userService = UserServiceFactory.getUserService();

		User user = userService.getCurrentUser();
		
		// probably should add in a cookie later that says logged in/ not logged
		// in
		
		String gkey=null;
		if(req != null){
			gkey=req.getParameter("gkey");
		}
		LoginInfo loginInfo = new LoginInfo();
		if (user != null) {
			loginInfo.loggedIn = true;
			loginInfo.user = user;
			if(requestUri != null){
				loginInfo.logoutUrl=userService.createLogoutURL(requestUri);
			}
			
			
			per = SDao.getGUserDao().getRN(new Key<GUser>(GUser.class, user.getUserId()));
			loginInfo.gUser = per;
			if (per == null) {
				per = new GUser(user.getUserId(),user.getNickname());
				SDao.getGUserDao().put(per);
			}
			if (writer != null) {
				writer.println(SideBar.getServletRep("Sign Out",loginInfo.logoutUrl,gkey));
			}
		} else {
			loginInfo.loggedIn = false;
			if(requestUri != null){
				loginInfo.loginUrl=userService.createLoginURL(requestUri);
			}
			
			if (writer != null) {
				writer.println(SideBar.getServletRep("Sign In",loginInfo.loginUrl,gkey));
			}
		}
		return loginInfo;
	}



}

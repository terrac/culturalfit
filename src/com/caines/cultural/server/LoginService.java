package com.caines.cultural.server;


import java.util.UUID;

import javax.servlet.http.HttpServletRequest;






import javax.servlet.http.HttpServletResponse;
import com.caines.cultural.shared.LoginInfo;
import com.caines.cultural.shared.datamodel.GUser;
import com.caines.cultural.shared.datamodel.UserProfile;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class LoginService {
		public static LoginInfo login(HttpServletRequest req,HttpServletResponse resp) {
		if(req == null){
			throw new IllegalArgumentException("ahh");
		}
		String requestUri = req.getRequestURI();
		String guserId = (String)req.getSession().getAttribute("userId");
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if(user != null){
			guserId = user.getUserId();
		}
		GUser per = SDao.getGUserDao().get(guserId);

		if(per == null){
			if(userService.isUserLoggedIn()){
				per = SDao.getGUserDao().get(user.getUserId());
				if (per == null) {
					per = new GUser(user.getUserId(),user.getEmail());
					
					SDao.getGUserDao().put(per);
				}
			}
			if(per == null){
				per = new GUser(UUID.randomUUID().toString(),"");
				req.getSession().setAttribute("userId", per.id);
				per.temporary = true;
				UserProfile up = new UserProfile(per);
				SDao.getUserProfileDao().put(up);
				SDao.getGUserDao().put(per);
			}
		}

		// probably should add in a cookie later that says logged in/ not logged
		// in
		
		String gRef=null;
		if(req != null){
			gRef=req.getParameter("gRef");
		}
		LoginInfo loginInfo = new LoginInfo();
		if (per != null) {
			loginInfo.loggedIn = true;
			if(requestUri != null){
				loginInfo.logoutUrl=userService.createLogoutURL(requestUri);
			}
			
			
			
			loginInfo.gUser = per;
			
		} else {
			loginInfo.loggedIn = false;
			if(requestUri != null){
				loginInfo.loginUrl=userService.createLoginURL(requestUri);
			}			
		}
		return loginInfo;
	}



}

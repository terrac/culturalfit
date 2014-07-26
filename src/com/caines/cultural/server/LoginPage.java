package com.caines.cultural.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.caines.cultural.shared.LoginInfo;
import com.caines.cultural.shared.datamodel.GUser;
import com.caines.cultural.shared.datamodel.UserProfile;
import com.google.appengine.api.users.UserServiceFactory;

public class LoginPage extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String logout = req.getParameter("logout");
		if("true".equals(logout)){
			req.getSession().invalidate();
			resp.sendRedirect(UserServiceFactory.getUserService().createLogoutURL("/"));
		}
		// UserService userService = UserServiceFactory.getUserService();
		// User user = userService.getCurrentUser(); // or
		// req.getUserPrincipal()
		LoginInfo li = LoginService.login(req, resp);
		resp.setContentType("text/html");

		if (li.loggedIn) {
			resp.sendRedirect("/c/seeker/");
		} else {
			resp.getWriter().println(formData);
		}
		
		String email = req.getParameter("emailAddress");
		String password = req.getParameter("passwordinput");
		String register = req.getParameter("register");
		if(email == null){
			return;
		}
		UserProfile up = SDao.getUserProfileDao().getByProperty("email", email);
		String redirect = "/c/seeker";
		String sred = (String) req.getSession().getAttribute("redirect");
		if(sred!= null){
			redirect = sred;
		}
		if ("on".equals(register)) {
			if (up != null) {
				resp.getWriter().write("Email already taken");
			} else {
				String uid = UUID.randomUUID().toString();
				req.getSession().setAttribute("userId", uid);
				GUser gu = new GUser(uid, email);

				SDao.getGUserDao().put(gu);
				up = new UserProfile(gu);
				up.email = email;
				up.password = password;
				SDao.getUserProfileDao().put(up);
				resp.sendRedirect(redirect);
			}
		} else {
			if (up != null) {
				if (up.password.equals(password)) {
					req.getSession().setAttribute("userId", up.user.getKey().getName());
					
					resp.sendRedirect(redirect);
					
				} else {
					resp.getWriter().write("Password incorrect");
				}
			} else {
				resp.getWriter()
						.write("This email address was not found.  If you have not registered you should");
				;
			}
		}

	}

	String formData = ""
			+ "<html>"
			+ "<head>"
			+ "<meta http-equiv=content-type content=text/html; charset=UTF-8>"
			+ "<link type=text/css rel=stylesheet href=/assets/CulturalFit.css>"
			+ "<link href=/assets/bootstrap.min.css rel=stylesheet>"
			+ "<script src=/assets/jQuery.js></script>"
			+ "<script src=/assets/bootstrap.min.js></script>"
			+ "<title>Cultural Fit: Who you are, not what you are</title>"
			+ "<script type=text/javascript language=javascript src=/culturalfit/culturalfit.nocache.js></script>"
			+ "<script type=text/javascript language=javascript src=/assets/CulturalFit.js></script>"
			+ "</head>"
			+ "<body>"
			+ "<button class='btn btn-primary' onclick=\"location='"
			+ UserServiceFactory.getUserService().createLoginURL("/c/seeker")
			+ "'\">Login With Google</Button><br>"
			+ "<form class=form-horizontal action=/loginRequired><fieldset>"
			+ "<legend>Login/Create Account</legend>"
			+ "<div class=control-group>"
			+ "<label class=control-label for=emailAddress>Email Address</label>"
			+ "<div class=controls>"
			+ "<input id=emailAddress name=emailAddress type=text placeholder=email@example.com class=input-xlarge required=>"
			+ "</div></div><div class=control-group><label class=control-label for=passwordinput>Password</label>"
			+ "<div class=controls>"
			+ "<input id=passwordinput name=passwordinput type=password placeholder=****** class=input-xlarge required=>"
			+ "<p class=help-block>Register <input type=checkbox name=register ></p></div></div>"
			+ "<div class=control-group><label class=control-label for=submit></label>"
			+ "<div class=controls><button id=submit name=submit class='btn btn-primary'>Login</button>"
			+ "</div></div></fieldset></form></body></html>";
}

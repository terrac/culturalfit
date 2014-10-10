package com.caines.cultural.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.caines.cultural.shared.LoginInfo;
import com.caines.cultural.shared.datamodel.GUser;
import com.caines.cultural.shared.datamodel.UserProfile;
import com.google.appengine.api.users.UserServiceFactory;

public class LoginPage extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String logout = req.getParameter("logout");
		if ("true".equals(logout)) {
			req.getSession().invalidate();
			resp.sendRedirect(UserServiceFactory.getUserService()
					.createLogoutURL("/"));
			return;
		}
		String checked ="";
		
		// UserService userService = UserServiceFactory.getUserService();
		// User user = userService.getCurrentUser(); // or
		// req.getUserPrincipal()
		LoginInfo li = LoginService.login(req, resp);
		resp.setContentType("text/html");

		if (li.loggedIn&&!li.gUser.temporary) {
			resp.sendRedirect("/");
			return;
		} 
		resp.getWriter().println(setupForm(checked));
		

		String email = req.getParameter("emailAddress");
		String password = req.getParameter("passwordinput");
		String register = req.getParameter("register");
		if (email == null) {
			return;
		}
		if (!isValidEmailAddress(email)) {
			resp.getWriter().write("Invalid email");
			return;
		}

		UserProfile up = SDao.getUserProfileDao().getByProperty("email", email);
		String redirect = "/c/seeker";
		String sred = (String) req.getSession().getAttribute("redirect");
		if (sred != null) {
			redirect = sred;
		}
		if ("on".equals(register)) {
			if (up != null) {
				resp.getWriter().write("Email already taken");
			} else {
				String uid = UUID.randomUUID().toString();
				req.getSession().setAttribute("userId", uid);
				GUser gu = li.gUser;
				gu.temporary = false;
				SDao.getGUserDao().put(gu);
				up = new UserProfile(gu);
				up.email = email;
				up.password = password;
				SDao.getUserProfileDao().put(up);
				resp.sendRedirect(redirect);
//				Cookie cookie = new Cookie("temporary", null); // Not necessary, but saves bandwidth.
//				cookie.setMaxAge(0); // Don't set to -1 or it will become a session cookie!
//				resp.addCookie(cookie);
			}
		} else {
			if (up != null) {
				if (up.password.equals(password)) {
					req.getSession().setAttribute("userId",
							up.user.getKey().getName());

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

	public String setupForm(String checked) {
		return ""
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
				+ "<button class='btn btn-success' onclick=\"location='"
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
				+ "<p class=help-block>Register <input type=checkbox name=register "+checked+"></p></div></div>"
				+ "<div class=control-group><label class=control-label for=submit></label>"
				+ "<div class=controls><button id=submit name=submit class='btn btn-primary'>Login</button>"
				+ "</div></div></fieldset></form></body></html>";
	};

	public boolean isValidEmailAddress(String email) {
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
		java.util.regex.Matcher m = p.matcher(email);
		return m.matches();
	}
}

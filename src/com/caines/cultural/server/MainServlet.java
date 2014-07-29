package com.caines.cultural.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.caines.cultural.shared.LoginInfo;

public class MainServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		LoginInfo li=LoginService.login(req, resp);
//		if(li.gUser.temporary){
//			Cookie cookie =
//  			new Cookie("temporary", "true");
//  			resp.addCookie(cookie);
//		}
		if(!li.loggedIn){
			try {
				req.getSession().setAttribute("redirect", req.getRequestURI());
				resp.sendRedirect("/loginRequired");
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			return;
		}
		
		String[] u= req.getPathInfo().split("/");
		if(u.length > 1){
			if(u[1].equals("profile")){
				JUtil.showList(req,resp);
				req.getRequestDispatcher("/d/profile.jsp").forward(req, resp);;
				return;
			}
			if("seeker".equals(u[1])&& u.length >2){
				new BasicServiceImpl().setCurrentGroup(li,BasicServiceImpl.getGroup(u[2]).getRef());
			}

		}
		resp.getWriter().write(page);
		//make gwt pull the url and redirect things
	}
	final static String page = 
	"<!doctype html>"
    +"<html>"
    +"<head>"
    +"<meta http-equiv=content-type content=text/html; charset=UTF-8>"
    +"<link type=text/css rel=stylesheet href=/assets/CulturalFit.css>"
    +"<link href=/assets/bootstrap.min.css rel=stylesheet>"
    +"<script src=/assets/jQuery.js></script>"
    +"<script src=/assets/bootstrap.min.js></script>"
    +"<title>Cultural Fit: Who you are, not what you are</title>"
    +"<script type=text/javascript language=javascript src=/culturalfit/culturalfit.nocache.js></script>"
    +"<script type=text/javascript language=javascript src=/assets/CulturalFit.js></script>"
  +"</head>"                                       
  +"<body>"
  	+"<div id=employerSwitch class=topright></div>"
	+"<div id=top></div>"
	+"<div id=content></div>"
  +"</body>"
+"</html>";
}

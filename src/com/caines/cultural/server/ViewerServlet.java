package com.caines.cultural.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.caines.cultural.shared.LoginInfo;
import com.caines.cultural.shared.datamodel.codingscramble.CodeContainer;

public class ViewerServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//String[] u= req.getPathInfo().split("/");
		//LoginInfo li=LoginService.login(req, resp);
		resp.getWriter().write(page);
//		if(!li.loggedIn){
//			try {
//				req.getSession().setAttribute("redirect", req.getRequestURI());
//				resp.sendRedirect("/loginRequired");
//			} catch (IOException e) {
//				throw new RuntimeException(e);
//			}
//			return;
//		}
		
		
		//take 3rd u
		//pull associated container and code pointers 
		//put them on the page and highlight and link
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
    +"<script src=/assets/pretty/prettify.js></script>"
    +"<link href=/assets/pretty/prettify.css type=text/css rel=stylesheet />"

    +"<title>Cultural Fit: Who you are, not what you are</title>"
    +"<script type=text/javascript language=javascript src=/culturalfit/culturalfit.nocache.js></script>"
    +"<script type=text/javascript language=javascript src=/assets/CulturalFit.js></script>"
  +"</head>"                                       
  +"<body>"
	+"<div id=content></div>"
  +"</body>"
+"</html>";
	
	
}

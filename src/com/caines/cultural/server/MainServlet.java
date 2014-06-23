package com.caines.cultural.server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.caines.cultural.server.datautil.TagUtil;
import com.caines.cultural.shared.LoginInfo;
import com.caines.cultural.shared.datamodel.GUser;
import com.caines.cultural.shared.datamodel.Group;
import com.caines.cultural.shared.datamodel.Question;
import com.caines.cultural.shared.datamodel.Tag;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.googlecode.objectify.Key;

public class MainServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String[] u= req.getPathInfo().split("/");
		if(u.length > 2){
			if(u[1].equals("profile")){
				req.getRequestDispatcher("/d/profile.jsp").forward(req, resp);;
				return;
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
    +"<script src=/assets/bootstrap.min.js></script>"
    +"<title>Cultural Fit: Who you are, not what you are</title>"
    +"<script type=text/javascript language=javascript src=/culturalfit/culturalfit.nocache.js></script>"
    +"<script type=text/javascript language=javascript src=/assets/CulturalFit.js></script>"
  +"</head>"                                       
  +"<body>"
  	+"<div id=employerSwitch class=topright></div>"
	+"<div id=content></div>"
  +"</body>"
+"</html>";
}

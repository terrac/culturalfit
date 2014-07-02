/*
 * Copyright 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.caines.cultural.shared;



import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public abstract class SideBar {

	
	VerticalPanel layout = new VerticalPanel();
	
	
	/**
	 * Copied from viewSelectionSource
	 * @param req 
	 * @return
	 */
	
	public static String getServletRep(String signinrep,String url, String gRef){
		
		return "<html>" +
				"<head>" +
//				"<script type=\"text/javascript\" language=\"javascript\" src=\"villagedc/villagedc.nocache.js\"></script>" +
//				"<link type=\"text/css\" rel=stylesheet href=villagedc.css>" +
				"</head>" +
				"<body>"+getClientRep(signinrep, url,gRef);
	}
	
	public static String getRep(String signinrep,String url){
		return 
				"<a href=\""+url+"\" class=\"gwt-Anchor\" tabindex=\"0\">"+signinrep+"</a>";
	}
	public static String getClientRep(String signinrep,String url,String gRef){
		String share =" "+ getRep("leaderboard", "/leaderboard");
		if(gRef!= null){
			share=" "+ getRep("share", "/leaderboard?gRef="+gRef);
		} 
		return 
		getRep(signinrep, url) + " " + getRep("gamelist", "/displaypersongames") + " " + getRep("profile", "/profile") + share;
	}


	
}

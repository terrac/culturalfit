package com.caines.cultural.server;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.caines.cultural.server.datautil.TagUtil;
import com.caines.cultural.shared.LoginInfo;
import com.caines.cultural.shared.datamodel.GUser;
import com.caines.cultural.shared.datamodel.Group;
import com.caines.cultural.shared.datamodel.GroupNameChopped;
import com.caines.cultural.shared.datamodel.Location;
import com.caines.cultural.shared.datamodel.Question;
import com.caines.cultural.shared.datamodel.Tag;
import com.caines.cultural.shared.datamodel.UserGroup;
import com.caines.cultural.shared.datamodel.UserProfile;
import com.caines.cultural.shared.datamodel.UserQuestion;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.googlecode.objectify.Ref;

public class ConsoleServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		LoginInfo li = LoginService.login(req, resp);
		li.gUser.admin = true;
		Group g=SDao.getGroupDao().getByProperty("lowerName", "welcome");
		Group tech=new BasicServiceImpl().addGroup("programming-basic technical-questions",li);
		Group web=new BasicServiceImpl().addGroup("programming-basic web-developer",li);
		Group softwareDeveloper=new BasicServiceImpl().addGroup("programming-basic software-developer",li);
		
		Group gtech=SDao.getGroupDao().getByProperty("lowerName", "programming-basic technical-questions");
		Group php=SDao.getGroupDao().getByProperty("lowerName", "programming-basic php");
		Group javascript=SDao.getGroupDao().getByProperty("lowerName", "programming-basic javascript");
		//Group ruby=SDao.getGroupDao().getByProperty("lowerName", "programming-basic ruby");
		Group html=SDao.getGroupDao().getByProperty("lowerName", "programming-basic html");
		
		new BasicServiceImpl().addQuestion("Are you interested in technical questions?", "Yes", "No",
				"programming-basic technical-questions", li,g);
		
		new BasicServiceImpl().addQuestion("Are you interested in web developer questions?", "Yes", "No",
				"programming-basic web-developer", li,tech);
		new BasicServiceImpl().addQuestion("Are you interested in software developer questions?", "Yes", "No",
				"programming-basic web-developer", li,tech);
		new BasicServiceImpl().addQuestion("Are you interested in Java?", "Yes", "No",
				"programming-basic java", li,softwareDeveloper);
//		new BasicServiceImpl().addQuestion("Are you interested in C#?", "Yes", "No",
//				"programming-basic csharp", li,softwareDeveloper);
		new BasicServiceImpl().addQuestion("Are you interested in sql?", "Yes", "No",
				"programming-basic sql", li,softwareDeveloper);
		new BasicServiceImpl().addQuestion("Are you interested in sql?", "Yes", "No",
				"programming-basic sql", li,web);
		new BasicServiceImpl().addQuestion("Are you interested in html?", "Yes", "No",
				"programming-basic html", li,web);
		new BasicServiceImpl().addQuestion("Are you interested in javascript?", "Yes", "No",
				"programming-basic html", li,web);

		
		// g.seconds = 0;
		// SDao.getGroupDao().put(g);
		//
		// List<UserQuestion> uqL=SDao.getUserQuestionDao().getQuery().list();
		//
		// for(UserQuestion uq :uqL){
		// resp.getWriter().write(uq+" <br><br><br>");
		// }
		// for(Group g : SDao.getGroupDao().getQuery().list()){
		//
		// g.name = g.name.trim();
		// g.lowerName = g.lowerName.trim();
		// SDao.getGroupDao().put(g);
		// }
		// for(GroupNameChopped gnc :
		// SDao.getGroupNameChoppedDao().getQuery().list()){
		// gnc.groupName = gnc.groupName.trim();
		// SDao.getGroupNameChoppedDao().put(gnc);
		// }

	}
}

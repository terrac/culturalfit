package com.caines.cultural.server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.caines.cultural.server.datautil.TagUtil;
import com.caines.cultural.shared.LoginInfo;
import com.caines.cultural.shared.datamodel.GUser;
import com.caines.cultural.shared.datamodel.Group;
import com.caines.cultural.shared.datamodel.Question;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class AdminServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//SDao.getUserProfileDao().getQuery().filter("zipCode >", 0).filter("zipCode <",5);
		
		SDao.getUserQuestionDao().deleteAll(SDao.getUserQuestionDao().getQuery().list());
		SDao.getQuestionDao().deleteAll(SDao.getQuestionDao().getQuery().list());
		SDao.getTagDao().deleteAll(SDao.getTagDao().getQuery().list());
		SDao.getGroupDao().deleteAll(SDao.getGroupDao().getQuery().list());
		SDao.getUserGroupDao().deleteAll(SDao.getUserGroupDao().getQuery().list());
		
		LoginInfo li = LoginService.login(null, null);
		
		JSONObject jo = (JSONObject) new JSONTokener(IOUtils.toString(new URL("http://gdata.youtube.com/feeds/api/videos/SIFL9qfmu5U?alt=json"))).nextValue();

		Gson gson = new Gson();
		ArrayList<Group> yourObjects = new ArrayList<Group>();
		URL url = new URL("/questions.json");
		JsonReader reader = new JsonReader(new InputStreamReader(url.openStream()));
		reader.
		reader.beginArray();
		while(reader.hasNext())
		{
		   Collection c = gson.fromJson(reader, Collection.class);
		   yourObjects.add(o);
		}
		reader.endArray();
		reader.close();
		
		Group g = new Group("Java Interview Questions",li.gUser);
		
		
		{
			String question = "What is the most important feature of Java?";
			String answer1 = "Java is a platform independent language.";
			String answer2 = "Java is dynamically typed";
			String tags = "Java";
			addQuestion(g, question, answer1, answer2, tags);
			
		}
		{
			String question = "What does platform independence mean in java?";
			String answer1 = "Write once, run everywhere";
			String answer2 = "Run once, write everywhere";
			String tags = "Java";
			addQuestion(g, question, answer1, answer2, tags);
			
		}
		
		{
			String question = "What is a JVM?";
			String answer1 = "JVM is the runtime environment for java";
			String answer2 = "JVM is what java uses to compile its classes";
			String tags = "Java";
			addQuestion(g, question, answer1, answer2, tags);
			
		}
		
		GUser gu=LoginService.login(null, null).gUser;
		gu.currentGroup = SDao.getGroupDao().put(g);
		SDao.getGUserDao().put(gu);
	}

	public void addQuestion(Group g, String question, String answer1,
			String answer2, String tags) {
		Question q = new Question(question, answer1, answer2, TagUtil.getTagKeys(tags));
		g.questions.add(SDao.getQuestionDao().put(q));
	}
}

package com.caines.cultural.server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.caines.cultural.server.datautil.TagUtil;
import com.caines.cultural.shared.datamodel.GUser;
import com.caines.cultural.shared.datamodel.Group;
import com.caines.cultural.shared.datamodel.Question;

public class AdminServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		SDao.getUserQuestionDao().deleteAll(SDao.getUserQuestionDao().getQuery().list());
		SDao.getQuestionDao().deleteAll(SDao.getQuestionDao().getQuery().list());
		SDao.getTagDao().deleteAll(SDao.getTagDao().getQuery().list());
		SDao.getGroupDao().deleteAll(SDao.getGroupDao().getQuery().list());
		Group g = new Group("Java Interview Questions");
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
		SDao.getGroupDao().put(g);
		GUser gu=LoginService.login(null, null).gUser;
		gu.currentGroup = g.getKey();
		SDao.getGUserDao().put(gu);
	}

	public void addQuestion(Group g, String question, String answer1,
			String answer2, String tags) {
		Question q = new Question(question, answer1, answer2, TagUtil.getTagKeys(tags));
		g.questions.add(SDao.getQuestionDao().put(q));
	}
}

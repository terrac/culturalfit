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

public class AdminServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// SDao.getUserProfileDao().getQuery().filter("zipCode >",
		// 0).filter("zipCode <",5);

		SDao.getUserQuestionDao().deleteAll(
				SDao.getUserQuestionDao().getQuery().list());
		SDao.getQuestionDao()
				.deleteAll(SDao.getQuestionDao().getQuery().list());
		SDao.getTagDao().deleteAll(SDao.getTagDao().getQuery().list());
		SDao.getGroupDao().deleteAll(SDao.getGroupDao().getQuery().list());
		SDao.getUserGroupDao().deleteAll(
				SDao.getUserGroupDao().getQuery().list());
		SDao.getGUserDao().deleteAll(SDao.getGUserDao().getQuery().list());
		LoginInfo li = LoginService.login(null, null);
	
		JsonParser jp = new JsonParser();
		String json = IOUtils.toString(new URL("http://127.0.0.1:8888/questions.txt"));
		//System.out.println(json);
		JsonElement je = jp.parse(json);
		for (Entry<String, JsonElement> b : je.getAsJsonObject().entrySet()) {
			Group g = new Group(b.getKey(), li.gUser);

			JsonArray ja = b.getValue().getAsJsonArray();
			for (int a = 0; a < ja.size(); a++) {
				JsonObject question = ja.get(a).getAsJsonObject();
				
//				List<Key<Tag>> tagKeys = TagUtil.getTagKeys(question.get("tags")
//				.getAsString());
				Question q = new Question(question.get("question")
						.getAsString(), question.get("rightAnswer")
						.getAsString(), question.get("wrongAnswer")
						.getAsString(), TagUtil.getTagKeys(""));
				g.questions.add(SDao.getQuestionDao().put(q));
			}
			SDao.getGroupDao().put(g);
		}

	}

	public void addQuestion(Group g, String question, String answer1,
			String answer2, String tags) {
		Question q = new Question(question, answer1, answer2,
				TagUtil.getTagKeys(tags));
		g.questions.add(SDao.getQuestionDao().put(q));
	}
}

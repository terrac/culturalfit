package com.caines.cultural.server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
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
import com.caines.cultural.shared.datamodel.Location;
import com.caines.cultural.shared.datamodel.Question;
import com.caines.cultural.shared.datamodel.Tag;
import com.caines.cultural.shared.datamodel.ZipCode;
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


		String[] loc = new String[] { "Not on the list", "Atlanta", "Seattle",
				"Philidelphia", "Chicago", "Los Angeles", "Dallas", "Boston",
				"Silicon Valley", "Washington DC", "New York" };
		List<Location> lList = new ArrayList<Location>();
		int order = 0;
		for (int a = loc.length-1; a >= 0; a--) {
			order++;
			lList.add(new Location(loc[a],order));
		}
		String[] states = new String[] { "Alabama", "Alaska", "Arizona",
				"Arkansas", "California", "Colorado", "Connecticut",
				"Delaware", "Florida", "Georgia", "Hawaii", "Idaho",
				"Illinois", "Indiana", "Iowa", "Kansas", "Kentucky",
				"Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan",
				"Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska",
				"Nevada", "New Hampshire", "New Jersey", "New Mexico",
				"New York", "North Carolina", "North Dakotoa", "Ohio",
				"Oklahoma", "Oregon", "Pennsylvania", "Rhode Island",
				"South Carolina", "South Dakota", "Tennessee", "Texas", "Utah",
				"Vermont", "Virginia", "Washington", "West Virginia",
				"Wisconsin" };
		for(String l : states){
			order++;
			lList.add(new Location(l, order));
		}
		SDao.getLocationDao()
				.deleteAll(SDao.getLocationDao().getQuery().list());
		SDao.getLocationDao().putAll(lList);

		URL oracle = new URL("http://127.0.0.1:8888/zips.csv");
		// BufferedReader in = new BufferedReader(
		// new InputStreamReader(oracle.openStream()));
		//
		// String inputLine;
		// List<ZipCode> zl = new ArrayList<ZipCode>();
		// while ((inputLine = in.readLine()) != null){
		// String[] line=inputLine.replace("\"","").split(",");
		// // System.out.println(line[0]);
		// // System.out.println(line[2]);
		// // System.out.println(line[3]);
		// try {
		// zl.add(new ZipCode(line[0],Double.parseDouble(line[2]),
		// Double.parseDouble(line[3])));
		// } catch (NumberFormatException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// }
		// in.close();
		// SDao.getZipCodeDao().putAll(zl);
		LoginInfo li = LoginService.login(null, null);

		JsonParser jp = new JsonParser();
		String json = IOUtils.toString(new URL(
				"http://127.0.0.1:8888/questions.txt"));
		// System.out.println(json);
		JsonElement je = jp.parse(json);
		for (Entry<String, JsonElement> b : je.getAsJsonObject().entrySet()) {
			Group g = new Group(b.getKey(), li.gUser);

			JsonArray ja = b.getValue().getAsJsonArray();
			for (int a = 0; a < ja.size(); a++) {
				JsonObject question = ja.get(a).getAsJsonObject();

				// List<Key<Tag>> tagKeys =
				// TagUtil.getTagKeys(question.get("tags")
				// .getAsString());
				Question q = new Question(question.get("question")
						.getAsString(), question.get("rightAnswer")
						.getAsString(), question.get("wrongAnswer")
						.getAsString(), TagUtil.getTagKeys(""));
				g.questions.add(SDao.getQuestionDao().put(q));
			}
			SDao.getGroupDao().put(g);
		}
		resp.getWriter().write("finished");
	}

	public void addQuestion(Group g, String question, String answer1,
			String answer2, String tags) {
		Question q = new Question(question, answer1, answer2,
				TagUtil.getTagKeys(tags));
		g.questions.add(SDao.getQuestionDao().put(q));
	}
}

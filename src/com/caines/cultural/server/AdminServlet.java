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
import com.caines.cultural.shared.datamodel.Location;
import com.caines.cultural.shared.datamodel.Question;
import com.caines.cultural.shared.datamodel.Tag;
import com.caines.cultural.shared.datamodel.UserGroup;
import com.caines.cultural.shared.datamodel.UserProfile;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.googlecode.objectify.Ref;

public class AdminServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// SDao.getUserProfileDao().getQuery().filter("zipCode >",
		// 0).filter("zipCode <",5);

		boolean setAdmin = Boolean.parseBoolean(req.getParameter("uurba"));
		if (setAdmin) {
			LoginInfo li = LoginService.login(null, null);
			li.gUser.admin = true;
			SDao.getGUserDao().put(li.gUser);
			return;
		}

		boolean setGroups = Boolean.parseBoolean(req.getParameter("setGroups"));
		if (setGroups) {
			LoginInfo li = LoginService.login(null, null);

			for (Group g : SDao.getGroupDao().getQuery().list()) {
				g.creator = li.gUser.getRef();
				SDao.getGroupDao().put(g);
			}
			return;
		}

		boolean flag = Boolean.parseBoolean(req.getParameter("dontdelete"));
		if (!flag) {
			SDao.getUserQuestionDao().deleteAll(
					SDao.getUserQuestionDao().getQuery().list());
			SDao.getQuestionDao().deleteAll(
					SDao.getQuestionDao().getQuery().list());
			SDao.getTagDao().deleteAll(SDao.getTagDao().getQuery().list());
			SDao.getGroupDao().deleteAll(SDao.getGroupDao().getQuery().list());
			SDao.getUserGroupDao().deleteAll(
					SDao.getUserGroupDao().getQuery().list());
			SDao.getGUserDao().deleteAll(SDao.getGUserDao().getQuery().list());
			SDao.getUserProfileDao().deleteAll(
					SDao.getUserProfileDao().getQuery().list());
		}
		LoginInfo li = LoginService.login(null, null);

		String[] loc = new String[] { "Atlanta", "Seattle", "Philidelphia",
				"Chicago", "Los Angeles", "Dallas", "Boston", "Silicon Valley",
				"Washington DC", "New York", "Austin", "Remote" };
		List<Location> lList = new ArrayList<Location>();
		int order = 0;
		for (int a = loc.length - 1; a >= 0; a--) {
			order++;
			lList.add(new Location(loc[a], order));
		}
//		String[] states = new String[] { "Alabama", "Alaska", "Arizona",
//				"Arkansas", "California", "Colorado", "Connecticut",
//				"Delaware", "Florida", "Georgia", "Hawaii", "Idaho",
//				"Illinois", "Indiana", "Iowa", "Kansas", "Kentucky",
//				"Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan",
//				"Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska",
//				"Nevada", "New Hampshire", "New Jersey", "New Mexico",
//				"New York", "North Carolina", "North Dakotoa", "Ohio",
//				"Oklahoma", "Oregon", "Pennsylvania", "Rhode Island",
//				"South Carolina", "South Dakota", "Tennessee", "Texas", "Utah",
//				"Vermont", "Virginia", "Washington", "West Virginia",
//				"Wisconsin" };
//		for (String l : states) {
//			order++;
//		    lList.add(new Location(l, order));
//		}
		SDao.getLocationDao()
				.deleteAll(SDao.getLocationDao().getQuery().list());
		SDao.getLocationDao().putAll(lList);

		// URL oracle = new URL("http://127.0.0.1:8888/zips.csv");
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

		JsonParser jp = new JsonParser();
		String json = IOUtils.toString(new URL(
				"http://cultural-fit.appspot.com/questions.txt"));
		// System.out.println(json);
		JsonElement je = jp.parse(json);
		for (Entry<String, JsonElement> b : je.getAsJsonObject().entrySet()) {
			Group g = new Group(b.getKey(), li.gUser);

			JsonArray ja = b.getValue().getAsJsonArray();
			for (int a = 0; a < ja.size(); a++) {
				JsonObject question = ja.get(a).getAsJsonObject();

				// List<Ref<Tag>> tagRefs =
				// TagUtil.getTagRefs(question.get("tags")
				// .getAsString());
				Question q = new Question(question.get("question")
						.getAsString(), question.get("rightAnswer")
						.getAsString(), question.get("wrongAnswer")
						.getAsString());
				q.tags =  TagUtil.getTagRefs("");
				g.questions.add(SDao.getQuestionDao().put(q));
			}
			SDao.getGroupDao().put(g);
		}
		resp.getWriter().write("finished basics");
		List<Group> listGroup = SDao.getGroupDao().getQuery().list();
		List<Location> listLocation = SDao.getLocationDao().getQuery().list();
		int[] salaryA = new int[] { 70000, 80000, 90000, 100000, 110000,
				120000, 130000 };
		Random r = new Random();

		List<UserGroup> ugList = new ArrayList<>();
		List<UserProfile> upList = new ArrayList<UserProfile>();
		int a = 0;
		for (Location l : listLocation) {
			a++;
			GUser gu = new GUser("fake-" + a, "fake-" + a);
			Ref<GUser> gRef = SDao.getGUserDao().put(gu);
			int count = 4 + r.nextInt(3);
			for (int b = 0; b < count; b++) {
				UserProfile up = new UserProfile(gu);
				up.location = l.getRef();
				up.salary = salaryA[r.nextInt(salaryA.length)];
				upList.add(up);
				SDao.getUserProfileDao().put(up);
	
				for (Group g : listGroup) {
					//+ "/" + l.name
					UserGroup ug = new UserGroup(g.name +l.name,
							l.getRef());
					ug.userProfile = up.getRef();
					ug.group = g.getRef();
					ug.total = (int) (r.nextInt(g.questions.size())) + 1;
					if (ug.total > 0) {
						ug.correct = r.nextInt(ug.total) + 1;
						ug.correctPercent = (double) ug.correct / ug.total;
					}
					ugList.add(ug);
				}
			}

		}
		SDao.getUserGroupDao().putAll(ugList);
		SDao.getUserProfileDao().putAll(upList);
		// System.out.println(upList);
		resp.getWriter().write("<br>finished fake users");

	}

	public void addQuestion(Group g, String question, String answer1,
			String answer2, String tags) {
		List<Ref<Tag>> tagRefs = TagUtil.getTagRefs(tags);
		Question q = new Question(question, answer1, answer2);
		q.tags = tagRefs;
		g.questions.add(SDao.getQuestionDao().put(q));
	}
}

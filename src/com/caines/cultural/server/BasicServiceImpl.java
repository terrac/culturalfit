package com.caines.cultural.server;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.caines.cultural.client.GreetingService;
import com.caines.cultural.server.datautil.PermissionsUtil;
import com.caines.cultural.server.datautil.TagUtil;
import com.caines.cultural.shared.LoginInfo;
import com.caines.cultural.shared.Tuple;
import com.caines.cultural.shared.datamodel.GUser;
import com.caines.cultural.shared.datamodel.Group;
import com.caines.cultural.shared.datamodel.Location;
import com.caines.cultural.shared.datamodel.Question;
import com.caines.cultural.shared.datamodel.TemporaryQuestion;
import com.caines.cultural.shared.datamodel.UserGroup;
import com.caines.cultural.shared.datamodel.UserProfile;
import com.caines.cultural.shared.datamodel.UserQuestion;
import com.caines.cultural.shared.datamodel.clientserver.SharedUserProfile;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class BasicServiceImpl extends RemoteServiceServlet implements
		GreetingService {

	public void setupQuestions(Ref<Group> gRef) {
		LoginInfo li = LoginService.login(null, null);
		List<Question> qList = getQuestionList();
		List<UserQuestion> uqList = new ArrayList<>();
		List<UserQuestion> listAnswered = SDao.getUserQuestionDao()
				.getQByProperty("user", li.gUser).filter("group", gRef)
				.filter("visited", true).list();

		for (Question q : qList) {
			if (q.disabled) {
				continue;
			}
			boolean flag = false;
			for (UserQuestion answered : listAnswered) {
				if (answered.question.equals(q.getRef())) {
					flag = true;
				}
			}
			if (flag) {
				continue;
			}
			UserQuestion userQuestion = new UserQuestion(q.getRef(), gRef);
			userQuestion.user = li.gUser.getRef();
			uqList.add(userQuestion);
		}
		SDao.getUserQuestionDao().putAll(uqList);
	}

	public UserGroup setupProfileForGroup(Ref<Group> gRef) {
		if (gRef == null) {
			return null;
		}
		LoginInfo li = LoginService.login(null, null);

		UserProfile up = SDao.getUserProfileDao().getByProperty("user",
				li.gUser.getRef());
		UserGroup g = SDao.getUserGroupDao().getQByProperty("userProfile", up)
				.filter("group", gRef).first().now();
		if (g == null) {
			g = new UserGroup(SDao.getGroupDao().get(gRef).name, up.location);
			g.group = gRef;
			g.userProfile = up.getRef();
			SDao.getUserGroupDao().put(g);
		}

		List<UserQuestion> listAnswered = SDao.getUserQuestionDao()
				.getQByProperty("user", li.gUser.getRef())
				.filter("group", gRef).filter("processed", false)
				.filter("visited", true).list();
		int total = 0;
		int correct = 0;
		for (UserQuestion answered : listAnswered) {
			total++;
			if (answered.correct) {
				correct++;
			}
			answered.processed = true;

		}
		SDao.getUserQuestionDao().putAll(listAnswered);
		g.correct += correct;
		g.total += total;
		g.correctPercent = ((double) g.correct / g.total);
		SDao.getUserGroupDao().put(g);
		return g;
	}

	@Override
	public List<UserGroup> getUserGroupList() {
		LoginInfo li = LoginService.login(null, null);
		if (li.gUser.currentGroup == null) {
			return null;
		}
		setupProfileForGroup(li.gUser.currentGroup);

		List<UserGroup> userGroupList = getUserGroupList(SDao
				.getUserProfileDao().getByProperty("user", li.gUser).id);
		return userGroupList;
	}

	@Override
	public List<UserGroup> getUserGroupList(long id) {
		List<UserGroup> gList = SDao.getUserGroupDao()
				.getQByProperty("userProfile", UserProfile.getKey(id)).list();
		return new ArrayList<>(gList);
	}

	@Override
	public List<Question> getQuestionList() {
		LoginInfo li = LoginService.login(null, null);
		return new ArrayList<>(OService.ofy().load().type(Question.class)
				.filter("group", li.gUser.currentGroup).list());
	}

	@Override
	public Question getNextQuestion() {
		LoginInfo li = LoginService.login(null, null);
		UserQuestion uq = SDao.getUserQuestionDao()
				.getQByProperty("user", li.gUser.getRef())
				.filter("visited", false).first().now();

		// set of curated questions
		// with the property not visited
		//
		if (uq == null) {
			if (li.gUser.currentGroup == null) {
				return null;
			}
			setupQuestions(li.gUser.currentGroup);
			uq = SDao.getUserQuestionDao()
					.getQByProperty("user", li.gUser.getRef())
					.filter("visited", false).first().now();
			if (uq == null) {
				return null;
			}
		}
		Question question = SDao.getQuestionDao().get(uq.question);
		uq.visited = true;
		uq.timeVisited = new Date();

		uq.group = li.gUser.currentGroup;
		SDao.getUserQuestionDao().put(uq);
		li.gUser.currentQuestion = Ref.create(uq);
		SDao.getGUserDao().put(li.gUser);
		return question;
	}

	@Override
	public void answerQuestion(Long id, String answer) {
		LoginInfo li = LoginService.login(null, null);
		UserQuestion uq = SDao.getUserQuestionDao().get(
				li.gUser.currentQuestion);
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, -2);
		if (uq.timeVisited.getTime() < c.getTimeInMillis()) {
			return;
		}
		uq.correct = answer.equals(SDao.getQuestionDao().get(id).answer1);
		SDao.getUserQuestionDao().put(uq);

	}

	@Override
	public String addQuestion(String question, String answer1, String answer2,
			String tagString) {
		LoginInfo li = LoginService.login(null, null);
		Group g = SDao.getGroupDao().get(li.gUser.currentGroup);

		Question qu = new Question(question, answer1, answer2);
		if (!PermissionsUtil.canEdit(li.gUser, g)) {
			TemporaryQuestion tq = new TemporaryQuestion();
			tq.group = g.getRef();
			tq.question = SDao.getQuestionDao().put(qu);
			SDao.getTemporaryQuestionDao().put(tq);
			return null;
		}

		qu.group = g.getRef();
		SDao.getQuestionDao().put(qu);
		qu.tags = TagUtil.getTagRefs(tagString);
		SDao.getGroupDao().put(g);
		return null;
	}

	@Override
	public void addGroup(String value) {
		LoginInfo li = LoginService.login(null, null);
		li.gUser.currentGroup = SDao.getGroupDao().put(
				new Group(value, li.gUser));
		SDao.getGUserDao().put(li.gUser);
	}

	@Override
	public List<Group> getTopGroups() {
		return new ArrayList<>(SDao.getGroupDao().getQuery().list());
	}

	@Override
	public Group setCurrentGroup(String text) {
		LoginInfo li = LoginService.login(null, null);
		Group g = SDao.getGroupDao().getByProperty("lowerName",
				text.toLowerCase());
		li.gUser.currentGroup = g.getRef();
		SDao.getGUserDao().put(li.gUser);
		return g;
	}

	@Override
	public Group getCurrentGroup() {
		LoginInfo li = LoginService.login(null, null);
		if (!li.loggedIn) {
			return null;
		}
		return SDao.getGroupDao().get(li.gUser.currentGroup);
	}

	@Override
	public Question getQuestionToEdit(Long questionRef) {
		Question q = SDao.getQuestionDao().get(Question.getRef(questionRef));

		return q;
	}

	@Override
	public void updateQuestion(Long questionRef, String tags) {
		Question q = SDao.getQuestionDao().get(Question.getRef(questionRef));
		q.tags = TagUtil.getTagRefs(tags);
		SDao.getQuestionDao().put(q);
	}

	@Override
	public void disableQuestion(Long questionRef) {
		Question q = SDao.getQuestionDao().get(Question.getRef(questionRef));
		q.disabled = true;
		SDao.getQuestionDao().put(q);
	}

	@Override
	public void sendProfile(int salary, long location) {
		LoginInfo li = LoginService.login(null, null);
		UserProfile up = SDao.getUserProfileDao().getByProperty("user",
				li.gUser);
		up.salary = salary;
		setLocation(location);
		SDao.getUserProfileDao().put(up);
	}

	@Override
	public SharedUserProfile getUserProfile() {
		LoginInfo li = LoginService.login(null, null);
		if (li.gUser == null) {
			return null;
		}
		UserProfile up = SDao.getUserProfileDao().getByProperty("user",
				li.gUser);
		if (up == null) {
			up = new UserProfile(li.gUser);
			SDao.getUserProfileDao().put(up);
		}
		return up.getShared();
	}

	@Override
	public void setLocation(long locationRef) {
		LoginInfo li = LoginService.login(null, null);
		UserProfile up = SDao.getUserProfileDao().getByProperty("user",
				li.gUser);

		up.location = Location.getRef(locationRef);
		List<UserGroup> ugList = SDao.getUserGroupDao()
				.getQByProperty("userProfile", up).list();
		for (UserGroup ug : ugList) {
			ug.locationMapping = up.location;
		}
		SDao.getUserGroupDao().putAll(ugList);
		SDao.getUserProfileDao().put(up);
	}

	@Override
	public Tuple<SharedUserProfile, List<Location>> getProfileData() {

		return new Tuple<SharedUserProfile, List<Location>>(getUserProfile(),
				new ArrayList<Location>(SDao.getLocationDao().getQuery()
						.order("order").list()));
	}

	@Override
	public Tuple<Group, Tuple<String, Boolean>> getLogInOutString() {
		UserService userService = UserServiceFactory.getUserService();
		String path = "/";
		Group group = getCurrentGroup();
		if (!userService.isUserLoggedIn()) {
			return new Tuple<Group, Tuple<String, Boolean>>(group,
					new Tuple<String, Boolean>(
							userService.createLoginURL(path), true));
		} else {
			return new Tuple<Group, Tuple<String, Boolean>>(group,
					new Tuple<String, Boolean>(
							userService.createLogoutURL(path), false));
		}
	}

	@Override
	public String editGroup(String groupName) {
		LoginInfo li = LoginService.login(null, null);
		Group g = SDao.getGroupDao().getByProperty("lowerName",
				groupName.toLowerCase());

		if (g == null) {
			addGroup(groupName);
			return "Created";
		}
		if (PermissionsUtil.canEdit(li.gUser, g)) {
			return "owner";
		}
		return "toEdit";
	}

	@Override
	public List<Question> getTemporaryQuestions() {
		LoginInfo li = LoginService.login(null, null);
		
		List<Question> ql = new ArrayList<Question>();
		if (!PermissionsUtil.canEditCurrentGroup(li.gUser)) {
			return ql;
		}
		for (TemporaryQuestion tq : SDao.getTemporaryQuestionDao()
				.listByProperty("group", li.gUser.currentGroup)) {
			ql.add(tq.question.get());
		}
		return ql;
	}

	@Override
	public void addPermanentQuestion(long id, boolean shouldAdd) {
		LoginInfo li = LoginService.login(null, null);
		if (!PermissionsUtil.canEditCurrentGroup(li.gUser)) {
			return;
		}
		TemporaryQuestion tq = SDao.getTemporaryQuestionDao()
				.getQByProperty("question", Question.getRef(id)).first().now();
		if (shouldAdd) {
			Question q = tq.question.get();
			q.group = tq.group;

			SDao.getQuestionDao().put(q);

		}
		SDao.getTemporaryQuestionDao().delete(tq);
	}
}

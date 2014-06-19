package com.caines.cultural.server;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.caines.cultural.client.GreetingService;
import com.caines.cultural.server.datautil.TagUtil;
import com.caines.cultural.shared.LoginInfo;
import com.caines.cultural.shared.datamodel.Group;
import com.caines.cultural.shared.datamodel.Question;
import com.caines.cultural.shared.datamodel.UserGroup;
import com.caines.cultural.shared.datamodel.UserQuestion;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Key;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class BasicServiceImpl extends RemoteServiceServlet implements
		GreetingService {

	public void setupQuestions(Key<Group> gKey) {
		LoginInfo li = LoginService.login(null, null);
		Group g = SDao.getGroupDao().get(gKey);
		List<UserQuestion> uqList = new ArrayList<>();
		List<UserQuestion> listAnswered = SDao.getUserQuestionDao()
				.getQByProperty("user", li.gUser.getKey())
				.filter("group", g.getKey()).filter("visited", true).list();

		for (Key<Question> q : g.questions) {
			boolean flag = false;
			for (UserQuestion answered : listAnswered) {
				if (answered.question.equals(q)) {
					flag = true;
				}
			}
			if (flag) {
				continue;
			}
			UserQuestion userQuestion = new UserQuestion(q, g.getKey());
			userQuestion.user = li.gUser.getKey();
			uqList.add(userQuestion);
		}
		SDao.getUserQuestionDao().putAll(uqList);
	}

	public void setupProfileForGroup(Key<Group> gKey) {
		LoginInfo li = LoginService.login(null, null);
		UserGroup g = SDao.getUserGroupDao().getQByProperty("user", li.gUser.getKey())
				.filter("group", gKey).get();
		if(g == null){
			g = new UserGroup("aoeu");
			g.user = li.gUser.getKey();
			SDao.getUserGroupDao().put(g);
		}
	
	
		List<UserQuestion> listAnswered = SDao.getUserQuestionDao()
				.getQByProperty("user", li.gUser.getKey())
				.filter("group", g.getKey()).filter("processed", false)
				.filter("visited", true).list();
		int total = 0;
		int correct = 0;
		for (UserQuestion answered : listAnswered) {
			total++;
			if(answered.correct){
				correct++;
			}
		}
		g.correct += correct;
		g.total += total;
		SDao.getUserGroupDao().put(g);
	}

	@Override
	public List<UserGroup> getUserGroupList() {
		LoginInfo li = LoginService.login(null, null);
		setupProfileForGroup(li.gUser.currentGroup);
		List<UserGroup> gList = SDao.getUserGroupDao().getQByProperty("user", li.gUser.getKey())
				.list();
		return gList;
	}

	@Override
	public Question getNextQuestion() {
		LoginInfo li = LoginService.login(null, null);
		UserQuestion uq = SDao.getUserQuestionDao()
				.getQByProperty("user", li.gUser.getKey())
				.filter("visited", false).get();

		// set of curated questions
		// with the property not visited
		//
		if (uq == null) {
			if (li.gUser.currentGroup == null) {
				return null;
			}
			setupQuestions(li.gUser.currentGroup);
			uq = SDao.getUserQuestionDao()
					.getQByProperty("user", li.gUser.getKey())
					.filter("visited", false).get();
			if (uq == null) {
				return null;
			}
		}
		Question question = SDao.getQuestionDao().get(uq.question);
		uq.visited = true;
		uq.timeVisited = new Date();
		SDao.getUserQuestionDao().put(uq);
		li.gUser.currentQuestion = uq.getKey();
		SDao.getGUserDao().put(li.gUser);
		return question;
	}

	@Override
	public void answerQuestion(String answer) {
		LoginInfo li = LoginService.login(null, null);
		UserQuestion uq = SDao.getUserQuestionDao().get(
				li.gUser.currentQuestion);
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, -2);
		if (uq.timeVisited.getTime() < c.getTimeInMillis()) {
			return;
		}
		uq.answer = answer;
		SDao.getUserQuestionDao().put(uq);
	}

	@Override
	public String addQuestion(String question, String answer1, String answer2,
			String tagString) {
		LoginInfo li = LoginService.login(null, null);
		Group g = SDao.getGroupDao().get(li.gUser.currentGroup);
		g.questions.add(SDao.getQuestionDao().put(
				new Question(question, answer1, answer2, TagUtil
						.getTagKeys(tagString))));
		SDao.getGroupDao().put(g);
		return null;
	}

	@Override
	public void addGroup(String value) {
		SDao.getGroupDao().put(new Group(value));
	}

	@Override
	public List<Group> getTopGroups() {
		return SDao.getGroupDao().getQuery().list();
	}

	@Override
	public void setCurrentGroup(String text) {
		LoginInfo li = LoginService.login(null, null);
		Group g = SDao.getGroupDao().getByProperty("name", text);
		li.gUser.currentGroup = g.getKey();
		SDao.getGUserDao().put(li.gUser);
	}

	@Override
	public Group getCurrentGroup() {
		LoginInfo li = LoginService.login(null, null);
		return SDao.getGroupDao().get(li.gUser.currentGroup);
	}

	@Override
	public Question getQuestionToEdit(Long questionKey) {
		Question q = SDao.getQuestionDao().get(Question.getKey(questionKey));

		return q;
	}

	@Override
	public void updateQuestion(Long questionKey, String tags) {
		Question q = SDao.getQuestionDao().get(Question.getKey(questionKey));
		q.tags = TagUtil.getTagKeys(tags);
		SDao.getQuestionDao().put(q);
	}

}

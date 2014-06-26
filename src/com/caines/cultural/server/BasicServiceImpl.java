package com.caines.cultural.server;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.caines.cultural.client.GreetingService;
import com.caines.cultural.server.datautil.PermissionsUtil;
import com.caines.cultural.server.datautil.TagUtil;
import com.caines.cultural.shared.LoginInfo;
import com.caines.cultural.shared.Tuple;
import com.caines.cultural.shared.datamodel.Group;
import com.caines.cultural.shared.datamodel.Location;
import com.caines.cultural.shared.datamodel.Question;
import com.caines.cultural.shared.datamodel.UserGroup;
import com.caines.cultural.shared.datamodel.UserProfile;
import com.caines.cultural.shared.datamodel.UserQuestion;
import com.caines.cultural.shared.datamodel.ZipCode;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class BasicServiceImpl extends RemoteServiceServlet implements
		GreetingService {

	public void setupQuestions(Key<Group> gKey) {
		LoginInfo li = LoginService.login(null, null);
		List<Question> qList=getQuestionList();
		List<UserQuestion> uqList = new ArrayList<>();
		List<UserQuestion> listAnswered = SDao.getUserQuestionDao()
				.getQByProperty("user", li.gUser.getKey())
				.filter("group", gKey).filter("visited", true).list();

		for (Question q : qList) {
			if(q.disabled){
				continue;
			}
			boolean flag = false;
			for (UserQuestion answered : listAnswered) {
				if (answered.question.equals(q.getKey())) {
					flag = true;
				}
			}
			if (flag) {
				continue;
			}
			UserQuestion userQuestion = new UserQuestion(q.getKey(), gKey);
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
			
			g = new UserGroup(SDao.getGroupDao().get(gKey).name);
			g.group = gKey;
			g.user = li.gUser.getKey();
			SDao.getUserGroupDao().put(g);
		}
	
	
		List<UserQuestion> listAnswered = SDao.getUserQuestionDao()
				.getQByProperty("user", li.gUser.getKey())
				.filter("group", gKey).filter("processed", false)
				.filter("visited", true).list();
		int total = 0;
		int correct = 0;
		for (UserQuestion answered : listAnswered) {
			total++;
			if(answered.correct){
				correct++;
			}
			answered.processed = true;
			
		}
		SDao.getUserQuestionDao().putAll(listAnswered);
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
	public List<Question> getQuestionList() {
		LoginInfo li = LoginService.login(null, null);
		Objectify o=ObjectifyService.begin();
		return new ArrayList(o.get(SDao.getGroupDao().get(li.gUser.currentGroup).questions).values());
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

		uq.group = li.gUser.currentGroup;
		SDao.getUserQuestionDao().put(uq);
		li.gUser.currentQuestion = uq.getKey();
		SDao.getGUserDao().put(li.gUser);
		return question;
	}

	@Override
	public void answerQuestion(Long id,String answer) {
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
		if(PermissionsUtil.canEdit(li.gUser, g)){
			return null;
		}
		g.questions.add(SDao.getQuestionDao().put(
				new Question(question, answer1, answer2, TagUtil
						.getTagKeys(tagString))));
		SDao.getGroupDao().put(g);
		return null;
	}

	@Override
	public void addGroup(String value) {
		LoginInfo li = LoginService.login(null, null);
		li.gUser.currentGroup=SDao.getGroupDao().put(new Group(value,li.gUser));
		SDao.getGUserDao().put(li.gUser);
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
	
	@Override
	public void disableQuestion(Long questionKey) {
		Question q = SDao.getQuestionDao().get(Question.getKey(questionKey));
		q.disabled = true;
		SDao.getQuestionDao().put(q);
	}
	
	@Override
	public void sendProfile(UserProfile userProfile) {
		LoginInfo li = LoginService.login(null, null);
		UserProfile up = SDao.getUserProfileDao().getByProperty("user", li.gUser);
		up.salary = userProfile.salary;
		up.location= userProfile.location;
		
		SDao.getUserProfileDao().put(up);
	}
	
	@Override
	public UserProfile getUserProfile(){
		LoginInfo li = LoginService.login(null, null);
		UserProfile up = SDao.getUserProfileDao().getByProperty("user", li.gUser);
		if(up == null){
			up = new UserProfile(li.gUser);
			SDao.getUserProfileDao().put(up);
		}
		return up;
	}
	
	@Override
	public void setLocation(long locationKey){
		LoginInfo li = LoginService.login(null, null);
		UserProfile up = SDao.getUserProfileDao().getByProperty("user", li.gUser);
		up.location = Location.getKey(locationKey);
		SDao.getUserProfileDao().put(up);
	}

	@Override
	public Tuple<UserProfile,List<Location>> getProfileData(){

		return new Tuple<UserProfile,List<Location>>(getUserProfile(),SDao.getLocationDao().getQuery().order("order").list());
	}
}

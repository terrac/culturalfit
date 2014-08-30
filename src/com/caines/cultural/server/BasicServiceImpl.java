package com.caines.cultural.server;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import com.googlecode.objectify.Ref;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class BasicServiceImpl extends RemoteServiceServlet implements
		GreetingService {

	public void setupQuestions(Ref<Group> gRef) {
		
		if(gRef == null){
			return;
		}
		LoginInfo li = login();
		setupProfileForGroup(li);
		
		List<Question> qList = getQuestionList(gRef);
		List<UserQuestion> uqList = new ArrayList<>();
		List<UserQuestion> listSetup = SDao.getUserQuestionDao()
				.getQByProperty("user", li.gUser).filter("group", gRef).list();

		for (Question q : qList) {			
			boolean flag = false;
			for (UserQuestion answered : listSetup) {
				if (answered.question.equals(q.getRef())) {
					flag = true;
				}
			}
			if (flag) {
				continue;
			}
			UserQuestion userQuestion = new UserQuestion(q.getRef(), gRef);//gRef.get()
			userQuestion.user = li.gUser.getRef();
			uqList.add(userQuestion);
		}
		SDao.getUserQuestionDao().putAll(uqList);
	}

	public void setupProfileForGroup(LoginInfo li) {
		

		List<UserQuestion> listAnswered = SDao.getUserQuestionDao()
				.getQByProperty("user", li.gUser.getRef()).filter("processed", false)
				.filter("visited", true).filter("answered",true).list();
		
		Map<Ref<Group>,Tuple<Integer,Integer>> tMap = new HashMap<>();
		for (UserQuestion answered : listAnswered) {
			//total
			
			Tuple<Integer, Integer> correctTotal = tMap.get(answered.group);
			if(correctTotal == null){
				correctTotal = new Tuple<Integer, Integer>(0, 0);
				tMap.put(answered.group, correctTotal);
			}
			correctTotal.b++;
			if (answered.correct) {
				//correct
				correctTotal.a++;
			}
			answered.processed = true;

		}
		UserProfile up = getUProfile(li);

		
		List<UserGroup> ugList = new ArrayList<>();
		for(Entry<Ref<Group>,Tuple<Integer,Integer>> t :tMap.entrySet()){
			UserGroup g = SDao.getUserGroupDao().getQByProperty("userProfile", up).filter("group", t.getKey()).first().now();
			if (g == null) {
				g = new UserGroup(SDao.getGroupDao().get(t.getKey()).name, up.location);
				g.group = t.getKey();
				g.userProfile = up.getRef();
			}
			g.correct += t.getValue().a;
			g.total += t.getValue().b;
			g.correctPercent = ((double) g.correct / g.total);
			ugList.add(g);
		}
		
		SDao.getUserGroupDao().putAll(ugList);		
		SDao.getUserQuestionDao().putAll(listAnswered);
		
	}

	@Override
	public List<UserGroup> getUserGroupList() {
		LoginInfo li = login();
		if (li.gUser.currentGroup == null) {
			return null;
		}
		List<UserGroup> userGroupList = getUserGroupList(li);
		return userGroupList;
	}

	public List<UserGroup> getUserGroupList(LoginInfo li) {
		if (li.gUser.currentGroup == null) {
			return null;
		}
		setupProfileForGroup(li);

		List<UserGroup> userGroupList = getUserGroupList(SDao
				.getUserProfileDao().getByProperty("user", li.gUser).id);
		return userGroupList;
	}

	@Override
	public List<UserGroup> getUserGroupList(long id) {
		List<UserGroup> gList = SDao.getUserGroupDao()
				.getQByProperty("userProfile", UserProfile.getKey(id)).order("-correct").list();
		return new ArrayList<>(gList);
	}

	@Override
	public List<Question> getQuestionList(Ref<Group> group) {
		return new ArrayList<>(OService.ofy().load().type(Question.class)
				.filter("group", group).filter("disabled",false).list());
	}
	@Override
	public List<Question> getQuestionList() {
		LoginInfo li = login();
		return getQuestionList(li.gUser.currentGroup);
	}

	@Override
	public Tuple<Group,Question> getNextQuestion() {
		LoginInfo li = login();

		Group group = li.gUser.currentGroup.get();
		if (group == null) {
			return null;
		}
		Ref<GUser> ref = li.gUser.getRef();
		
		
		
		UserQuestion uq = getNextQuestion(ref);

		// set of curated questions
		// with the property not visited
		//
		
		if (uq == null) {
			
			setupQuestions(group.getRef());
			uq = getNextQuestion(ref);
			if (uq == null) {
//				setupQuestions(group.getRandomUnansweredSibling(li.gUser));
//				uq = getNextQuestion(ref);
//				if(uq == null){
					return null;	
//				}
			}
		}
		Question question = SDao.getQuestionDao().get(uq.question);
		uq.visited = true;
		uq.timeVisited = new Date();

		//uq.group = li.gUser.currentGroup;
		SDao.getUserQuestionDao().put(uq);
		li.gUser.currentQuestion = Ref.create(uq);
		SDao.getGUserDao().put(li.gUser);
		return new Tuple<Group, Question>(group,question);
	}

	

	public UserQuestion getNextQuestion(Ref<GUser> ref) {
		return SDao.getUserQuestionDao()
				.getQByProperty("user", ref)
				.filter("visited", false).first().now();
	}

	@Override
	public void answerQuestion(Long id, String answer) {
		LoginInfo li = login();
		UserQuestion uq = SDao.getUserQuestionDao().get(
				li.gUser.currentQuestion);
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, -2);
		if (uq.timeVisited.getTime() < c.getTimeInMillis()) {
			return;
		}
		uq.answered = true;
		
		uq.correct = answer.equals(SDao.getQuestionDao().get(id).answer1);
		SDao.getUserQuestionDao().put(uq);
		
		UserGroup ug=SDao.getUserGroupDao().getByProperty("group", uq.group);
		if(uq == null||uq.timeVisited == null){
			return;
		}
		ug.milliSecondsTaken += (int) (Calendar.getInstance().getTimeInMillis() -uq.timeVisited.getTime());
		SDao.getUserGroupDao().put(ug);
		
		if(uq.question.get().correctPointer != null){
			if(uq.correct){
				setCurrentGroup(li, uq.question.get().correctPointer);
			}
		}
	}

	@Override
	public String addQuestion(String question, String answer1, String answer2,
			String lowerName) {
		LoginInfo li = login();
		Group g = SDao.getGroupDao().get(li.gUser.currentGroup);
		return addQuestion(question, answer1, answer2, lowerName, li,g);
	}

	public String addQuestion(String question, String answer1, String answer2,
			String pointerName, LoginInfo li, Group ownerGroup) {
		
		boolean addMoreQuestions = getQuestionList(ownerGroup.getRef()).size() < 25;
		if(!addMoreQuestions){
			return "No more questions";
		}
		Question qu = new Question(question, answer1, answer2);
		if (!PermissionsUtil.canEdit(li.gUser, ownerGroup)) {
			TemporaryQuestion tq = new TemporaryQuestion();
			tq.group = ownerGroup.getRef();
			tq.question = SDao.getQuestionDao().put(qu);
			SDao.getTemporaryQuestionDao().put(tq);
			return null;
		}

		qu.group = ownerGroup.getRef();
		if(!pointerName.isEmpty()){
			qu.correctPointer = SDao.getGroupDao().getByProperty("lowerName", pointerName).getRef();
		}
		SDao.getQuestionDao().put(qu);
		
		//qu.tags = TagUtil.getTagRefs("");
		
		ownerGroup.addMoreQuestions = addMoreQuestions;
		SDao.getGroupDao().put(ownerGroup);
		return null;
	}

	@Override
	public Group addGroup(String value) {
	
		LoginInfo li = login();
		Group group = addGroup(value, li);
		return group;
	}

	public Group addGroup(String value, LoginInfo li) {
		Group group = new Group(value, li.gUser);
		group.chopName();
		SDao.getGroupDao().put(group);
		setCurrentGroup(li, group.getRef());
		SDao.getGUserDao().put(li.gUser);
		return group;
	}

	@Override
	public List<Group> getTopGroups() {
		return new ArrayList<>(SDao.getGroupDao().getQuery().list());
	}

	@Override
	public Group setCurrentGroup(String text) {
		LoginInfo li = login();
		Group g = getGroup(text);
		if(g == null){
			return null;
		}
		Ref<Group> ref = g.getRef();
		setCurrentGroup(li,ref);
		return g;
	}

	public static Group getGroup(String text) {
		Group g = SDao.getGroupDao().getByProperty("lowerName",
				text.toLowerCase());
		if(g == null){
			return null;
		}
		return g;
	}

	public void setCurrentGroup(LoginInfo li,Ref<Group> ref) {
		if(ref.equals(li.gUser.currentGroup)){
			return;
		}
		
		li.gUser.currentGroup = ref;
		
		//has to be a better way
		SDao.getUserQuestionDao().deleteAll(SDao.getUserQuestionDao().getQByProperty("user", li.gUser).filter("visited", false).list());
		//SDao.getNextGroupDao().deleteAll(SDao.getNextGroupDao().getQByProperty("user", li.gUser).list());
		SDao.getGUserDao().put(li.gUser);
	}

	@Override
	public Group getCurrentGroup() {
		LoginInfo li = login();
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
		LoginInfo li = login();
		UserProfile up = SDao.getUserProfileDao().getByProperty("user",
				li.gUser);
		up.salary = salary;
		setLocation(location);
		SDao.getUserProfileDao().put(up);
	}

	@Override
	public SharedUserProfile getUserProfile() {
		LoginInfo li = login();
		UserProfile up = getUProfile(li);
		return up.getShared();
	}

	public UserProfile getUProfile(LoginInfo li) {
		if (li.gUser == null) {
			return null;
		}
		UserProfile up = SDao.getUserProfileDao().getByProperty("user",
				li.gUser);
		if (up == null) {
			up = new UserProfile(li.gUser);
			SDao.getUserProfileDao().put(up);
		}
		return up;
	}

	@Override
	public void setLocation(long locationRef) {
		LoginInfo li = login();
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
		LoginInfo li = login();
		UserService userService = UserServiceFactory.getUserService();
		String path = "/";
		if (!li.loggedIn||li.gUser.temporary) {
			return new Tuple<Group, Tuple<String, Boolean>>(null,
					new Tuple<String, Boolean>(
							"/loginRequired", true));
		} else {
			Group group = getCurrentGroup();
			return new Tuple<Group, Tuple<String, Boolean>>(group,
					new Tuple<String, Boolean>(
							"/loginRequired?logout=true", false));
		}
	}

	@Override
	public Tuple<Group,String> editGroup(String groupName) {
		LoginInfo li = login();
		Group g =setCurrentGroup(groupName);

		if (g == null) {
			g =addGroup(groupName);
			return new Tuple<Group,String>(g,"Created");
		}
		if (PermissionsUtil.canEdit(li.gUser, g)) {
			return new Tuple<Group,String>(g,"owner");
		}
		return new Tuple<Group,String>(g,"toEdit");
	}

	@Override
	public List<Question> getTemporaryQuestions() {
		LoginInfo li = login();
		
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
		LoginInfo li = login();
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
	
	@Override
	public Group addTry(long userGroupId){
		LoginInfo li = login();
		
		UserGroup ug = SDao.getUserGroupDao().get(userGroupId);
		ug.tries++;
		ug.correct = 0;
		ug.correctPercent = 0;
		ug.total = 0;
		ug.milliSecondsTaken = 0;
		List<UserQuestion> listToDelete = SDao.getUserQuestionDao()
				.getQByProperty("user", li.gUser.getRef()).filter("group", ug.group).list();
		SDao.getUserGroupDao().put(ug);
		SDao.getUserQuestionDao().deleteAll(listToDelete);
		
		setCurrentGroup(li,ug.group);
		return ug.group.get();
	}

	private LoginInfo login() {
		return LoginService.login(getThreadLocalRequest(), getThreadLocalResponse());
	}
}

package com.caines.cultural.server;

import com.caines.cultural.shared.datamodel.GUser;
import com.caines.cultural.shared.datamodel.Group;
import com.caines.cultural.shared.datamodel.Question;
import com.caines.cultural.shared.datamodel.Tag;
import com.caines.cultural.shared.datamodel.UserGroup;
import com.caines.cultural.shared.datamodel.UserProfile;
import com.caines.cultural.shared.datamodel.UserQuestion;


public class SDao {
	public static Dao<GUser> getGUserDao() {
		return new Dao<GUser>(GUser.class);
	}
	
	public static Dao<Question> getQuestionDao() {
		return new Dao<Question>(Question.class);
	}

	public static Dao<Tag> getTagDao() {
		return new Dao<Tag>(Tag.class);
	}

	public static Dao<Group> getGroupDao() {
		return new Dao<Group>(Group.class);
	}
	
	public static Dao<UserQuestion> getUserQuestionDao() {
		return new Dao<UserQuestion>(UserQuestion.class);
	}
	
	public static Dao<UserGroup> getUserGroupDao() {
		return new Dao<UserGroup>(UserGroup.class);
	}
	
	public static Dao<UserProfile> getUserProfileDao() {
		return new Dao<UserProfile>(UserProfile.class);
	}
}

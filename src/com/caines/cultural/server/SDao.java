package com.caines.cultural.server;

import com.caines.cultural.shared.datamodel.GUser;
import com.caines.cultural.shared.datamodel.Group;
import com.caines.cultural.shared.datamodel.GroupNameChopped;
import com.caines.cultural.shared.datamodel.Location;
import com.caines.cultural.shared.datamodel.NextGroup;
import com.caines.cultural.shared.datamodel.Question;
import com.caines.cultural.shared.datamodel.Tag;
import com.caines.cultural.shared.datamodel.TemporaryQuestion;
import com.caines.cultural.shared.datamodel.UserGroup;
import com.caines.cultural.shared.datamodel.UserProfile;
import com.caines.cultural.shared.datamodel.UserQuestion;
import com.caines.cultural.shared.datamodel.ZipCode;


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
	
	public static Dao<ZipCode> getZipCodeDao() {
		return new Dao<ZipCode>(ZipCode.class);
	}
	
	public static Dao<Location> getLocationDao() {
		return new Dao<Location>(Location.class);
	}
	
	public static Dao<TemporaryQuestion> getTemporaryQuestionDao() {
		return new Dao<TemporaryQuestion>(TemporaryQuestion.class);
	}
	
	public static Dao<GroupNameChopped> getGroupNameChoppedDao() {
		return new Dao<GroupNameChopped>(GroupNameChopped.class);
	}
	
	public static Dao<NextGroup> getNextGroupDao() {
		return new Dao<NextGroup>(NextGroup.class);
	}
}

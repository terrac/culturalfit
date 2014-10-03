package com.caines.cultural.server;

import com.caines.cultural.shared.datamodel.GUser;
import com.caines.cultural.shared.datamodel.UserProfile;
import com.caines.cultural.shared.datamodel.codingscramble.CodeAlgorithm;
import com.caines.cultural.shared.datamodel.codingscramble.CodeContainer;
import com.caines.cultural.shared.datamodel.codingscramble.CodePath;
import com.caines.cultural.shared.datamodel.codingscramble.CodeQuestionPointer;
import com.caines.cultural.shared.datamodel.codingscramble.CodeUserDetails;


public class SDao {
	static OService  a= new OService();
	public static Dao<GUser> getGUserDao() {
		return new Dao<GUser>(GUser.class);
	}
	
		
	public static Dao<UserProfile> getUserProfileDao() {
		return new Dao<UserProfile>(UserProfile.class);
	}
	
	
	
	public static Dao<CodeContainer> getCodeContainerDao() {
		return new Dao<CodeContainer>(CodeContainer.class);
	}
	
	public static Dao<CodePath> getCodePathDao() {
		return new Dao<CodePath>(CodePath.class);
	}
	
	public static Dao<CodeQuestionPointer> getCodeQuestionPointerDao() {
		return new Dao<CodeQuestionPointer>(CodeQuestionPointer.class);
	}
	
	public static Dao<CodeUserDetails> getCodeUserDetailsDao() {
		return new Dao<CodeUserDetails>(CodeUserDetails.class);
	}

	public static Dao<CodeAlgorithm> getCodeAlgorithmDao() {
		return new Dao<CodeAlgorithm>(CodeAlgorithm.class);
	}
}

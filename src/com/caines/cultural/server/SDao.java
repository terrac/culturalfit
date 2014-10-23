package com.caines.cultural.server;

import com.caines.cultural.server.datamodel.codingscramble.CodeAlgorithm;
import com.caines.cultural.server.datamodel.codingscramble.CodeLink;
import com.caines.cultural.server.datamodel.codingscramble.CodePath;
import com.caines.cultural.server.datamodel.codingscramble.CodePointer;
import com.caines.cultural.server.datamodel.codingscramble.CodeTag;
import com.caines.cultural.shared.datamodel.GUser;
import com.caines.cultural.shared.datamodel.UserProfile;
import com.caines.cultural.shared.datamodel.codingscramble.CodeContainer;
import com.caines.cultural.shared.datamodel.codingscramble.CodeUserDetails;
import com.googlecode.objectify.Ref;


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
	
	public static Dao<CodePointer> getCodePointerDao() {
		return new Dao<CodePointer>(CodePointer.class);
	}
	
	public static Dao<CodeUserDetails> getCodeUserDetailsDao() {
		return new Dao<CodeUserDetails>(CodeUserDetails.class);
	}

	public static Dao<CodeAlgorithm> getCodeAlgorithmDao() {
		return new Dao<CodeAlgorithm>(CodeAlgorithm.class);
	}
	
	public static Dao<CodeLink> getCodeLinkDao() {
		return new Dao<CodeLink>(CodeLink.class);
	}

	public static Dao<CodeTag> getCodeTagDao() {
		return new Dao<CodeTag>(CodeTag.class);
	}

	public static<T> Ref<T> getRef(T c) {
			return Ref.create(c);

	}
}

package com.caines.cultural.server;

import com.caines.cultural.server.datamodel.codingscramble.CodeAlgorithm;
import com.caines.cultural.server.datamodel.codingscramble.CodeLink;
import com.caines.cultural.server.datamodel.codingscramble.CodePath;
import com.caines.cultural.server.datamodel.codingscramble.CodePointer;
import com.caines.cultural.server.datamodel.codingscramble.CodeTag;
import com.caines.cultural.shared.datamodel.GUser;
import com.caines.cultural.shared.datamodel.UserProfile;
import com.caines.cultural.shared.datamodel.codingscramble.CodeContainer;
import com.caines.cultural.shared.datamodel.codingscramble.CodeContainerFile;
import com.caines.cultural.shared.datamodel.codingscramble.CodeUserDetails;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

public class OService {
	static
	{
		factory().register(GUser.class);
		factory().register(UserProfile.class);
		factory().register(CodeContainer.class);
		factory().register(CodeContainerFile.class);
		factory().register(CodePath.class);
		factory().register(CodePointer.class);
		factory().register(CodeUserDetails.class);
		factory().register(CodeAlgorithm.class);
		factory().register(CodeLink.class);
		factory().register(CodeTag.class);
		
		
	}
    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}

package com.caines.cultural.server;

import com.caines.cultural.shared.datamodel.GUser;
import com.caines.cultural.shared.datamodel.Group;
import com.caines.cultural.shared.datamodel.Location;
import com.caines.cultural.shared.datamodel.Question;
import com.caines.cultural.shared.datamodel.Tag;
import com.caines.cultural.shared.datamodel.UserGroup;
import com.caines.cultural.shared.datamodel.UserProfile;
import com.caines.cultural.shared.datamodel.UserQuestion;
import com.caines.cultural.shared.datamodel.ZipCode;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

public class OService {
	static
	{
		factory().register(GUser.class);
		factory().register(Group.class);
		factory().register(Question.class);
		factory().register(Tag.class);
		factory().register(UserQuestion.class);
		factory().register(UserGroup.class);
		factory().register(UserProfile.class);
		factory().register(ZipCode.class);
		factory().register(Location.class);
		
	}
    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}

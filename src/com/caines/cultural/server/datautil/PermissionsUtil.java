package com.caines.cultural.server.datautil;

import java.util.ArrayList;
import java.util.List;

import com.caines.cultural.server.LoginService;
import com.caines.cultural.server.SDao;
import com.caines.cultural.shared.LoginInfo;
import com.caines.cultural.shared.datamodel.GUser;
import com.caines.cultural.shared.datamodel.Group;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

public class PermissionsUtil {
	public static boolean canEdit(GUser user,Group g){
		
		return user.getKey().equals(g.creator);
		
	}
}

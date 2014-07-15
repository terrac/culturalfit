package com.caines.cultural.server.datautil;

import org.mortbay.log.Log;

import com.caines.cultural.shared.datamodel.GUser;
import com.caines.cultural.shared.datamodel.Group;

public class PermissionsUtil {
	public static boolean canEdit(GUser user,Group g){
		if(user == null){
			return false;
		}
		if(user.isAdmin()){
			return true;
		}
		return user.getRef().equals(g.creator);
		
	}

	public static boolean canEditCurrentGroup(GUser gUser) {
		return canEdit(gUser, gUser.currentGroup.get());
	}
}

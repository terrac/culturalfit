package com.caines.cultural.shared.datamodel;


import java.io.Serializable;

import javax.persistence.Id;

import com.googlecode.objectify.Key;


public class GUser implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GUser() {
	
	}
	public GUser(String userId, String nickname) {
		id = userId;
		displayName = nickname;
	}
	@Id
	public String id;
	public String displayName;
	public Key<UserQuestion> currentQuestion;
	public Key<Group> currentGroup;
	
	public String getDisplayName() {
		return displayName;
	}
	public Key<GUser> getKey(){
		return new Key(GUser.class,id);
	}
	public static Key<GUser> getKey(String userkey){
		return new Key(GUser.class,userkey);
	}
	
}

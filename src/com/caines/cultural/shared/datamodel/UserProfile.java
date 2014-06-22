package com.caines.cultural.shared.datamodel;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;















import javax.persistence.Id;

import com.caines.cultural.server.datautil.TagUtil;
import com.googlecode.objectify.Key;


public class UserProfile implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserProfile() {
	
	}
	
	@Id
	public Long id;
	
	public UserProfile(GUser gUser) {		
		user = gUser.getKey();
	}
	public Key<GUser> user;
	public int salary;
	public int zipCode;
	public Key<UserProfile> getKey(){
		return new Key(UserProfile.class,id);
	}
	
	public static Key<UserProfile> getKey(String id){
		return new Key(UserProfile.class,id);
	}
	
	
	
}

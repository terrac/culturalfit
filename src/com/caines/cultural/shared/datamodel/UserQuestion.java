package com.caines.cultural.shared.datamodel;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

















import javax.persistence.Id;

import com.caines.cultural.server.datautil.TagUtil;
import com.googlecode.objectify.Key;


public class UserQuestion implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserQuestion() {
	
	}
	
	public UserQuestion(Key<Question> q, Key<Group> key) {
		question = q;
		group = key;
	}

	@Id
	public Long id;
	
	public Key<Question> question;

	public boolean visited;

	public Date timeVisited;

	public String answer;
	public Key<GUser> user;
	public Key<Group> group;
	
	public Key<UserQuestion> getKey(){
		return new Key(UserQuestion.class,id);
	}
	
	public static Key<UserQuestion> getKey(String id){
		return new Key(UserQuestion.class,id);
	}
	
	
	
}

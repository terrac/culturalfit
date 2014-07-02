package com.caines.cultural.shared.datamodel;


import java.io.Serializable;
import java.util.Date;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class UserQuestion implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserQuestion() {
	
	}
	
	public UserQuestion(Ref<Question> q, Ref<Group> Ref) {
		question = q;
		group = Ref;
	}

	@Id
	public Long id;
	
	public Ref<Question> question;

	public boolean visited;

	public Date timeVisited;
	public boolean processed;
	public boolean correct;
	public String answer;
	public Ref<GUser> user;
	public Ref<Group> group;
	
	
	
	
}

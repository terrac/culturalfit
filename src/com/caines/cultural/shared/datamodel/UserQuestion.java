package com.caines.cultural.shared.datamodel;


import java.io.Serializable;
import java.util.Date;

import com.google.gwt.dom.client.Style.Unit;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

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
	@Index
	public boolean visited;

	public Date timeVisited;
	@Index
	public boolean processed;
	public boolean correct;
	@Index
	public Ref<GUser> user;
	@Index
	public Ref<Group> group;

	@Index
	public boolean answered;

	@Override
	public String toString() {
		return "UserQuestion [id=" + id + ", question=" + question.get().question
				+ ", visited=" + visited + ", timeVisited=" + timeVisited
				+ ", processed=" + processed + ", correct=" + correct
				+ ", user=" + user + ", group=" + group.get().name + ", answered="
				+ answered + "]";
	}
	
	
	
	
}

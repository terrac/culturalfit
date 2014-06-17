package com.caines.cultural.shared.datamodel;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;















import javax.persistence.Id;

import com.caines.cultural.server.datautil.TagUtil;
import com.googlecode.objectify.Key;


public class Question implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Question() {
	
	}
	
	@Id
	public Long id;
	
	public Question(String question, String answer1, String answer2,
			List<Key<Tag>> tag) {
		super();
		this.question = question;
		this.answer1 = answer1;
		this.answer2 = answer2;
		this.tags = tag;
	}

	public String question;
	public String answer1;
	public String answer2;
	
	public List<Key<Tag>> tags = new ArrayList();
	
	public Key<Question> getKey(){
		return new Key(Question.class,id);
	}
	
	public static Key<Question> getKey(Long id){
		return new Key(Question.class,id);
	}
	
	
	
}

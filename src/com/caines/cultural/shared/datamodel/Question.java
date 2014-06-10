package com.caines.cultural.shared.datamodel;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;












import javax.persistence.Id;

import com.googlecode.objectify.Key;


public class Question implements Serializable{
	public Question() {
	
	}
	
	@Id
	public String id;
	
	String question;
	String answer1;
	String answer2;
	
	public List<Key<Tag>> tags = new ArrayList();
	
	public Key<Question> getKey(){
		return new Key(Question.class,id);
	}
	
	public static Key<Question> getKey(String id){
		return new Key(Question.class,id);
	}
	
	
	
}

package com.caines.cultural.shared.datamodel;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;







import com.google.common.annotations.GwtIncompatible;
import com.google.gwt.user.client.rpc.GwtTransient;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Question implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Question() {
	
	}
	
	@Id
	public Long id;
	
	public Question(String question, String answer1, String answer2) {
		super();
		this.question = question;
		this.answer1 = answer1;
		this.answer2 = answer2;
	}

	public String question;
	public String answer1;
	public String answer2;
	
	@GwtTransient
	public List<Ref<Tag>> tags = new ArrayList<>();
	public boolean disabled;
	@GwtTransient
	@Index
	public Ref<Group> group;
	
	@GwtIncompatible("")
	public Ref<Question> getRef(){
		return Ref.create(this);
	}
	
	@GwtIncompatible("")
	public static Ref<Question> getRef(Long id){
		return Ref.create(Key.create(Question.class, id));
	}
	
	
	
}

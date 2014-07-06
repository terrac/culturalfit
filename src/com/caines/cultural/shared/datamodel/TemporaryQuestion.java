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
public class TemporaryQuestion implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TemporaryQuestion() {
	
	}
	
	@Id
	public Long id;
	
	@GwtTransient
	@Index
	public Ref<Group> group;
	
	@GwtTransient
	@Index
	public Ref<Question> question;
	
	@GwtIncompatible("")
	public Ref<TemporaryQuestion> getRef(){
		return Ref.create(this);
	}
	
	@GwtIncompatible("")
	public static Ref<TemporaryQuestion> getRef(Long id){
		return Ref.create(Key.create(TemporaryQuestion.class, id));
	}
	
	
	
}

package com.caines.cultural.shared.datamodel.codingscramble;


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
import com.googlecode.objectify.annotation.Parent;

@Entity
public class CodeQuestionPointer implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CodeQuestionPointer() {
	
	}
	
	@Id
	public Long id;
	
	@GwtIncompatible("")
	Ref<CodeContainer> container;
	int lineNumber;
	
	@GwtIncompatible("")
	public Ref<CodeQuestionPointer> getRef(){
		return Ref.create(this);
	}
	
	@GwtIncompatible("")
	public static Ref<CodeQuestionPointer> getRef(Long id){
		return Ref.create(Key.create(CodeQuestionPointer.class, id));
	}
	
	
	
}

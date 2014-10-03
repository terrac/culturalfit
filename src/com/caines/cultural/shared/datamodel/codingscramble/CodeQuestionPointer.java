package com.caines.cultural.shared.datamodel.codingscramble;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;












import java.util.Random;

import org.apache.commons.collections.CollectionUtils;

import com.caines.cultural.shared.Tuple;
import com.caines.cultural.shared.container.ScramblerQuestion;
import com.google.common.annotations.GwtIncompatible;
import com.google.gwt.user.client.rpc.GwtTransient;
import com.google.gwt.user.client.rpc.core.java.util.Collections;
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
	
	public CodeQuestionPointer(Ref<CodeContainer> container, int lineNumber) {
		super();
		this.container = container;
		this.lineNumber = lineNumber;
	}

	@Id
	public Long id;
	
	@GwtIncompatible("")
	public Ref<CodeContainer> container;
	int lineNumber;
	
	@GwtIncompatible("")
	public Ref<CodeQuestionPointer> getRef(){
		return Ref.create(this);
	}
	
	@GwtIncompatible("")
	public static Ref<CodeQuestionPointer> getRef(Long id){
		return Ref.create(Key.create(CodeQuestionPointer.class, id));
	}

	public Tuple<ScramblerQuestion,Boolean> getQuestion() {
		ScramblerQuestion sq = new ScramblerQuestion();
		List<String> q = new ArrayList<>();
		for(int a = 0; a < 5; a++){
			String line=container.get().file.get(lineNumber+a);
			q.add(line);
		}
		List<String> q2 = new ArrayList<>(q);
		q2.set(3, q.get(2));
		q2.set(2, q.get(3));
		boolean correctAns = new Random().nextBoolean();
		if(correctAns){
			sq.q1 = q;
		} else {
			sq.q1 = q2;
		}
	
		return new Tuple<ScramblerQuestion, Boolean>(sq, correctAns);
	}
	public boolean checkQuestion(List<String> answer){
		for(int a = 0; a < 5; a++){
			String line=container.get().file.get(lineNumber+a);
			if(!line.equals(answer.get(a))){
				return false;
			}
		}
		return true;
	}
	
	
}

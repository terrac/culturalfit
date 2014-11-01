package com.caines.cultural.server.datamodel.codingscramble;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;












import java.util.Random;

import org.apache.commons.collections.CollectionUtils;

import com.caines.cultural.server.SDao;
import com.caines.cultural.shared.Tuple;
import com.caines.cultural.shared.container.ScramblerQuestion;
import com.caines.cultural.shared.datamodel.codingscramble.CodeContainer;
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
public class CodePointer implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CodePointer() {
	
	}
	
	@GwtIncompatible("")
	public CodePointer(Ref<CodeContainer> container, int lineNumber) {
		super();
		this.container = container;
		this.lineNumber = lineNumber;
	}

	@Id
	public Long id;
	
	@GwtIncompatible("")
	@Index
	public Ref<CodeContainer> container;
	public int lineNumber;
	public String line;
	
	
	@GwtIncompatible("")
	public Ref<CodePointer> getRef(){
		return Ref.create(this);
	}
	
	@GwtIncompatible("")
	public static Ref<CodePointer> getRef(Long id){
		return Ref.create(Key.create(CodePointer.class, id));
	}


	
	
//	public Tuple<ScramblerQuestion,Boolean> getQuestion() {
//		ScramblerQuestion sq = new ScramblerQuestion();
//		
//		sq.rawFile = container.get().file;
//		sq.url = container.get().url;
//		return new Tuple<ScramblerQuestion, Boolean>(sq, isCorrect);
//	}
	@GwtIncompatible("")
	public boolean checkQuestion(List<String> answer){
		for(int a = 0; a < 5; a++){
			String line=container.get().cf.get().file.get(lineNumber+a);
			if(!line.equals(answer.get(a))){
				return false;
			}
		}
		return true;
	}

	@GwtIncompatible("")
	public static CodePointer getCodePointer(CodeContainer c, int a, String nextLink) {
		CodePointer codeP = SDao.getCodePointerDao().getQByProperty("container", c).filter("lineNumber", a).first().now();
		if(codeP == null){
			codeP = new CodePointer();
			codeP.container = SDao.getRef(c);
			codeP.line = nextLink;
			codeP.lineNumber = a;
			SDao.getCodePointerDao().put(codeP);
		}
		return codeP;
	}
	
	@Override
	public boolean equals(Object obj) {
		CodePointer cp = (CodePointer) obj;
		if(cp.container.equals(this.container)&&this.lineNumber == cp.lineNumber&&this.line.equals(cp.line)){
			return true;
		}
		return false;
	}
	
}

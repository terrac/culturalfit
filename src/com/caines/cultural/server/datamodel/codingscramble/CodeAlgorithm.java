package com.caines.cultural.server.datamodel.codingscramble;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.caines.cultural.server.SDao;
import com.google.common.annotations.GwtIncompatible;
import com.google.gwt.user.client.rpc.GwtTransient;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class CodeAlgorithm implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CodeAlgorithm() {

	}

	public CodeAlgorithm(String url, String[] tagsA) {
		// TODO Auto-generated constructor stub
	}
	
	@GwtIncompatible("")
	public CodePointer getNextQuestion(){
		List<CodePointer> list = SDao.getCodePointerDao().getQuery().list();
		if(list.size() == 0){
			return null;
		}
		return list.get(new Random().nextInt(list.size()));
		//return SDao.getCodeQuestionPointerDao().getQuery().first().now();
	}

	@Id
	public Long id;

	
	@GwtIncompatible("")
	public Ref<CodeAlgorithm> getRef() {
		return Ref.create(this);
	}

	@GwtIncompatible("")
	public static Ref<CodeAlgorithm> getRef(Long id) {
		return Ref.create(Key.create(CodeAlgorithm.class, id));
	}

}

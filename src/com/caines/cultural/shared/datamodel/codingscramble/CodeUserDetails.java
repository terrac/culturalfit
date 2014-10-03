package com.caines.cultural.shared.datamodel.codingscramble;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.caines.cultural.server.SDao;
import com.caines.cultural.shared.LoginInfo;
import com.google.common.annotations.GwtIncompatible;
import com.google.gwt.dev.util.collect.HashMap;
import com.google.gwt.user.client.rpc.GwtTransient;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class CodeUserDetails implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CodeUserDetails() {

	}

	@Id
	public Long id;

	
	public Integer tagCount =0;
	public Integer tagCorrect = 0;
	public Double tagAvgTime = 3.0;

	@Index
	public String userId;
	@Index
	public String main;
	@Index
	public String tag;
	@GwtIncompatible("")
	public Ref<CodeUserDetails> getRef() {
		return Ref.create(this);
	}

	@GwtIncompatible("")
	public static Ref<CodeUserDetails> getRef(Long id) {
		return Ref.create(Key.create(CodeUserDetails.class, id));
	}

	@GwtIncompatible("")
	public static CodeUserDetails getByTag(String t, String main,LoginInfo li) {

		CodeUserDetails cud = SDao.getCodeUserDetailsDao().getQuery().filter("tag", t).filter("main",main).filter("userId",li.gUser.id).first().now();
		if(cud == null){
			cud = new CodeUserDetails();
			cud.userId = li.gUser.id;
			cud.main = main;
			cud.tag = t;
			SDao.getCodeUserDetailsDao().put(cud);
		}
		return cud;
	}

	@GwtIncompatible("")
	public static List<CodeUserDetails> getByUser(LoginInfo login) {
		return SDao.getCodeUserDetailsDao().listByProperty("userId", login.gUser.id);
	}
	

}

package com.caines.cultural.server.datamodel.codingscramble;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.caines.cultural.server.SDao;
import com.caines.cultural.shared.datamodel.codingscramble.CodeContainer;
import com.google.common.annotations.GwtIncompatible;
import com.google.gwt.user.client.rpc.GwtTransient;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class CodeTag implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CodeTag() {
		// TODO Auto-generated constructor stub
	}
	public CodeTag(String t) {
		tag = t;
		main = !tag.contains("/");
	}

	@Id
	public String tag;

	@Index
	boolean main;
	public List<Ref<CodeContainer>> codeContainerList = new ArrayList<>();

}

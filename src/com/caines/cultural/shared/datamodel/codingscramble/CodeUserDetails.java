package com.caines.cultural.shared.datamodel.codingscramble;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

	Map<String,Integer> mapCount = new HashMap<>();
	
	@GwtIncompatible("")
	public Ref<CodeUserDetails> getRef() {
		return Ref.create(this);
	}

	@GwtIncompatible("")
	public static Ref<CodeUserDetails> getRef(Long id) {
		return Ref.create(Key.create(CodeUserDetails.class, id));
	}

}

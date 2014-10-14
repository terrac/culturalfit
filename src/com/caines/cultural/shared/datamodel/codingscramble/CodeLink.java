package com.caines.cultural.shared.datamodel.codingscramble;

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
public class CodeLink implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CodeLink() {

	}

	public CodePointer cp1;
	public CodePointer cp2;
	public boolean isNext;
	
	@Id
	public Long id;

	
	@GwtIncompatible("")
	public Ref<CodeLink> getRef() {
		return Ref.create(this);
	}

	@GwtIncompatible("")
	public static Ref<CodeLink> getRef(Long id) {
		return Ref.create(Key.create(CodeLink.class, id));
	}

}

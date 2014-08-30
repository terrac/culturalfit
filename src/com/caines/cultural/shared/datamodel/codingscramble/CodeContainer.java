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
public class CodeContainer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CodeContainer() {

	}

	@Id
	public Long id;

	Ref<CodePath> associatedCodePath;
	
	
	@GwtIncompatible("")
	public Ref<CodeContainer> getRef() {
		return Ref.create(this);
	}

	@GwtIncompatible("")
	public static Ref<CodeContainer> getRef(Long id) {
		return Ref.create(Key.create(CodeContainer.class, id));
	}

}

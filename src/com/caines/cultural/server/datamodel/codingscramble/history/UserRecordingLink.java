package com.caines.cultural.server.datamodel.codingscramble.history;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.caines.cultural.server.SDao;
import com.caines.cultural.server.datamodel.codingscramble.CodeLink;
import com.caines.cultural.shared.datamodel.GUser;
import com.google.common.annotations.GwtIncompatible;
import com.google.gwt.user.client.rpc.GwtTransient;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class UserRecordingLink implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public UserRecordingLink() {
		// TODO Auto-generated constructor stub
	}


	Ref<CodeLink> ref;
	Ref<GUser> guserRef;
	boolean isHighlyLinked;
	boolean isLinked;
	@Id
	public Long id;

	

}

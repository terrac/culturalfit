package com.caines.cultural.shared.datamodel;


import java.io.Serializable;






import com.google.common.annotations.GwtIncompatible;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class GUser implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GUser() {
	
	}
	public GUser(String userId, String nickname) {
		id = userId;
		displayName = nickname;
	}
	@Id
	public String id;
	public String displayName;
	public Ref<UserQuestion> currentQuestion;
	public Ref<Group> currentGroup;
	public boolean admin;
	
	public String getDisplayName() {
		return displayName;
	}
	
	public boolean isAdmin() {
		//return "test@example.com".equals(displayName);
		return admin;
	}
	
	public Ref<GUser> getRef(){
		return Ref.create(this);
	}
	@GwtIncompatible("")
	public static Key<GUser> getKey(String id2) {
		return Key.create(GUser.class, id2);
	}
}

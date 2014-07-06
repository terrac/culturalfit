package com.caines.cultural.shared.datamodel;


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

@Entity
public class NextGroup implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NextGroup() {
	
	}
	
	@Id
	public Long id;
	
	@GwtTransient
	public Ref<Group> group;
	
	public NextGroup(Ref<Group> group, Ref<GUser> user) {
		super();
		this.group = group;
		this.user = user;
	}

	@GwtTransient
	@Index
	public Ref<GUser> user;
	
	@GwtIncompatible("")
	public Ref<NextGroup> getRef(){
		return Ref.create(this);
	}
	
	@GwtIncompatible("")
	public static Ref<NextGroup> getRef(Long id){
		return Ref.create(Key.create(NextGroup.class, id));
	}
	
	
	
}

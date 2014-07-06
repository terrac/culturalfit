package com.caines.cultural.shared.datamodel;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
























import com.google.common.annotations.GwtIncompatible;
import com.google.gwt.user.client.rpc.GwtTransient;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class GroupNameChopped implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GroupNameChopped() {
	
	}
	
	@Id
	public Long id;
	
	@Index
	public String section;

	public String groupName;

	public GroupNameChopped(String section, String n) {
		super();
		this.section = section;
		this.groupName = n;
	}

	@GwtIncompatible("")
	public Ref<GroupNameChopped> getRef(){
		return Ref.create(this);
	}
	
	
}

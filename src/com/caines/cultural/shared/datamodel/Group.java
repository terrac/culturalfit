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
public class Group implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Group() {
	
	}
	
	@Id
	public Long id;
	
	@GwtIncompatible("")
	public Group(String groupname,GUser gUser) {
		if(gUser != null){
			creator = gUser.getRef();	
		}
		//id = groupname;
		name = groupname;
		lowerName = name.toLowerCase();
	}
	@GwtTransient
	public Ref<GUser> creator;
	public String name;
	@Index
	public String lowerName;
	public int seconds = 60 ;
	

	@Override
	public String toString() {
		return "Group [id=" + id + ", name=" + name + "]";
	}

	@GwtIncompatible("")
	public Ref<Group> getRef(){
		return Ref.create(this);
	}
	
	
}

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
public class UserGroup implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserGroup() {
	
	}
	
	@Id
	public Long id;
	
	@GwtIncompatible("ref")
	public UserGroup(String groupname,Ref<Location> location) {
		name = groupname;
		locationMapping = location;
	}
	public String name;
//	@GwtTransient
//	public List<Ref<Question>> questions = new ArrayList<>();
	public int total;
	@Index
	public int correct;
	public double correctPercent;
	//includes amount of questions answered
	public double score;
	@GwtTransient
	@Index
	public Ref<UserProfile> userProfile;
	
	@GwtTransient
	@Index
	public Ref<Group> group;
	@GwtTransient
	@Index
	public Ref<Location> locationMapping;
	
	public int tries= 1;
	public int milliSecondsTaken;
	
	public int getPercent(){
		if(total == 0){
			return 0;
		}
		return (int) (100* correctPercent);
	}

	@Override
	public String toString() {
		return "UserGroup [total=" + total + ", correct=" + correct + ", user="
				+ userProfile + ", group=" + group + ", locationMapping="
				+ locationMapping + "]";
	}
	
}

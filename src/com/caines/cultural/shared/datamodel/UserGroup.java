package com.caines.cultural.shared.datamodel;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
	
	public UserGroup(String groupname,Ref<Location> location) {
		name = groupname;
		locationMapping = location;
	}
	public String name;
	public List<Ref<Question>> questions = new ArrayList<>();
	public int total;
	@Index
	public int correct;
	public double correctPercent;
	//includes amount of questions answered
	public double score;	
	public Ref<GUser> user;
	public Ref<Group> group;
	public Ref<Location> locationMapping;
	
	public int getPercent(){
		if(total == 0){
			return 0;
		}
		return (int) (100* correctPercent);
	}

	@Override
	public String toString() {
		return "UserGroup [total=" + total + ", correct=" + correct + ", user="
				+ user + ", group=" + group + ", locationMapping="
				+ locationMapping + "]";
	}
	
}

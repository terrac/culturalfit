package com.caines.cultural.shared.datamodel;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;















import javax.persistence.Id;

import com.caines.cultural.server.datautil.TagUtil;
import com.googlecode.objectify.Key;


public class UserGroup implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserGroup() {
	
	}
	
	@Id
	public Long id;
	
	public UserGroup(String groupname,Key<Location> location) {
		name = groupname;
		locationMapping = location;
	}
	public String name;
	public List<Key<Question>> questions = new ArrayList<>();
	public int total;
	public int correct;
	public double correctPercent;
	public Key<GUser> user;
	public Key<Group> group;
	public Key<Location> locationMapping;
	public Key<UserGroup> getKey(){
		return new Key(UserGroup.class,id);
	}
	
	public static Key<UserGroup> getKey(String id){
		return new Key(UserGroup.class,id);
	}
	
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

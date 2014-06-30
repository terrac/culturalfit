package com.caines.cultural.shared.datamodel;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;















import javax.persistence.Id;

import com.caines.cultural.server.datautil.TagUtil;
import com.googlecode.objectify.Key;


public class Group implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Group() {
	
	}
	
	@Id
	public Long id;
	
	public Group(String groupname,GUser gUser) {
		if(gUser != null){
			creator = gUser.getKey();	
		}
		//id = groupname;
		name = groupname;
		lowerName = name.toLowerCase();
	}
	public Key<GUser> creator;
	public String name;
	public String lowerName;
	public List<Key<Question>> questions = new ArrayList<>();
	public int seconds = 60 ;
	public Key<Group> getKey(){
		return new Key(Group.class,id);
	}
	
	public static Key<Group> getKey(String id){
		return new Key(Group.class,id);
	}

	@Override
	public String toString() {
		return "Group [id=" + id + ", name=" + name + "]";
	}

	
	
	
}

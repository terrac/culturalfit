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
	public String id;
	
	public Group(String groupname) {
		id = groupname;
		name = groupname;
	}
	public String name;
	public List<Key<Question>> questions = new ArrayList<>();
	
	public Key<Group> getKey(){
		return new Key(Group.class,id);
	}
	
	public static Key<Group> getKey(String id){
		return new Key(Group.class,id);
	}
	
	
	
}

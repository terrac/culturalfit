package com.caines.cultural.shared.datamodel;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;




















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
	
	public Group(String groupname,GUser gUser) {
		if(gUser != null){
			creator = gUser.getRef();	
		}
		//id = groupname;
		name = groupname;
		lowerName = name.toLowerCase();
	}
	public Ref<GUser> creator;
	public String name;
	@Index
	public String lowerName;
	public List<Ref<Question>> questions = new ArrayList<>();
	public int seconds = 60 ;
	

	@Override
	public String toString() {
		return "Group [id=" + id + ", name=" + name + "]";
	}

	public Ref<Group> getRef(){
		return Ref.create(this);
	}
	
	
}

package com.caines.cultural.shared.datamodel;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Id;

import com.googlecode.objectify.Key;


public class Tag implements Serializable {
	public Tag() {
	
	}
	public Tag(String name) {
		//eventually might have categories and stuff too.
		this.id = name;
		
	}
	@Id
	public String id;
	public String name;
	public int amount;
	
	
	public Key<Tag> getKey(){
		return new Key(Tag.class,id);
	}
	public static List<String> toString(List<Tag> list) {
		List<String> a = new ArrayList();
		for(Tag b : list){
			a.add(b.getName());
		}
		return a;
	}
	public String getName() {
		return name;
	}
	
	
}

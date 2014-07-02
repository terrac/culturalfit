package com.caines.cultural.shared.datamodel;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Tag implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public Tag() {
	
	}
	public Tag(String name) {
		//eventually might have categories and stuff too.
		this.id = name;
		
	}
	@Id
	public String id;
	public String name;
	@Index
	public int amount;
	
	
	
	public static List<String> toString(List<Tag> list) {
		List<String> a = new ArrayList<>();
		for(Tag b : list){
			a.add(b.getName());
		}
		return a;
	}
	public String getName() {
		return name;
	}
	
	
}

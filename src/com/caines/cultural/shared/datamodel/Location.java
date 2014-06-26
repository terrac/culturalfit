package com.caines.cultural.shared.datamodel;

import java.io.Serializable;
import javax.persistence.Id;

import com.googlecode.objectify.Key;

public class Location implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Location() {

	}

	public Location(String name,int order) {
		this.name = name;
		this.order = order;
	}

	@Id
	public Long id;

	public String name;
	public int order;
	public Key<Location> getKey() {
		return new Key(Location.class, id);
	}

	public static Key<Location> getKey(long id) {
		return new Key(Location.class, id);
	}

}

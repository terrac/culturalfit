package com.caines.cultural.shared.datamodel;

import java.io.Serializable;

import com.google.common.annotations.GwtIncompatible;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
@Entity
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
	@Index
	public int order;
	public Ref<Location> getRef() {
		return Ref.create(this);
	}
	
	@GwtIncompatible("REF")
	public static Ref<Location> getRef(long r) {
		return Ref.create(Key.create(Location.class, r));
	}

	


}

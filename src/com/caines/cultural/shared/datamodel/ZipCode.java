package com.caines.cultural.shared.datamodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Id;

import com.caines.cultural.server.datautil.TagUtil;
import com.googlecode.objectify.Key;

public class ZipCode implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ZipCode() {

	}

	public ZipCode(int zip, double lat,double lon) {
		this.zip = zip;
		lattitude = lat;
		longitude = lon;
	}

	@Id
	public Long id;

	public int zip;
	public double lattitude;
	public double longitude;

	public Key<ZipCode> getKey() {
		return new Key(ZipCode.class, id);
	}

	public static Key<ZipCode> getKey(String id) {
		return new Key(ZipCode.class, id);
	}

}

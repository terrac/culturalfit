package com.caines.cultural.shared.datamodel;

import java.io.Serializable;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
@Entity
public class ZipCode implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ZipCode() {

	}

	public ZipCode(String zip, double lat,double lon) {
		this.zip = zip;
		lattitude = lat;
		longitude = lon;
	}

	@Id
	public Long id;

	public String zip;
	public double lattitude;
	public double longitude;



}

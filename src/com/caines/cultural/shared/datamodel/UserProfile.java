package com.caines.cultural.shared.datamodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Id;

import com.caines.cultural.server.datautil.TagUtil;
import com.google.gwt.i18n.client.NumberFormat;
import com.googlecode.objectify.Key;

public class UserProfile implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserProfile() {

	}

	@Id
	public Long id;

	public UserProfile(GUser gUser) {
		user = gUser.getKey();
		name = gUser.displayName;
	}

	public Key<GUser> user;
	public int salary;
	public Key<Location> location;
	public String name;
	public int vacation;

	public String getSalaryDisplay() {

		return NumberFormat.getCurrencyFormat().format(salary);

	}

	public String getVacationDisplay() {

		return vacation+" Weeks";

	}

	public Key<UserProfile> getKey() {
		return new Key(UserProfile.class, id);
	}

	public static Key<UserProfile> getKey(String id) {
		return new Key(UserProfile.class, id);
	}

	@Override
	public String toString() {
		return "UserProfile [user=" + user + ", location=" + location
				+ ", name=" + name + "]";
	}

}

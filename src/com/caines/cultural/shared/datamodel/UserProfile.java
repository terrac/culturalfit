package com.caines.cultural.shared.datamodel;

import java.io.Serializable;

import com.caines.cultural.shared.datamodel.clientserver.SharedUserProfile;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.rpc.GwtTransient;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
@Entity
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
		user = gUser.getRef();
		name = gUser.displayName;
	}
	@GwtTransient
	public Ref<GUser> user;
	public int salary;
	@GwtTransient
	public Ref<Location> location;
	public String name;
	public int vacation;

	public String getSalaryDisplay() {

		return NumberFormat.getCurrencyFormat().format(salary);

	}

	public String getVacationDisplay() {
		if(vacation == 0){
			return "";
		}
		return "Vacation:" +vacation+" Weeks";

	}

	public Ref<UserProfile> getRef() {
		return Ref.create(this);
	}

	

	@Override
	public String toString() {
		return "UserProfile [user=" + user + ", location=" + location
				+ ", name=" + name + "]";
	}

	public SharedUserProfile getShared(){
		long lId = 0;
		if(location != null){
			lId = location.getKey().getId();
		}
		return new SharedUserProfile(lId, salary, vacation);
	}
}

package com.caines.cultural.shared.datamodel.clientserver;

import java.io.Serializable;

public class SharedUserProfile implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public SharedUserProfile() {
		// TODO Auto-generated constructor stub
	}
	public SharedUserProfile(long location, int salary, int vacation) {
		super();
		this.location = location;
		this.salary = salary;
		this.vacation = vacation;
	}
	public long location;
	public int salary;
	public int vacation;
}

package com.caines.cultural.shared.datamodel.clientserver;

import java.io.Serializable;

import com.google.gwt.i18n.client.NumberFormat;

public class SharedUserProfile implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public long id;
	public String userid;
	public SharedUserProfile() {
		// TODO Auto-generated constructor stub
	}
	
	public SharedUserProfile(long location, int salary, int vacation, long id, String key) {
		super();
		this.location = location;
		this.salary = salary;
		this.vacation = vacation;
		this.id = id;
		this.userid = key;
	}

	public long location;
	public int salary;
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
}

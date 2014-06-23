package com.caines.cultural.server;

import java.util.List;

import com.caines.cultural.shared.datamodel.UserGroup;

public class JUtil {
	public static String showList(){
		List<UserGroup> ugList=new BasicServiceImpl().getUserGroupList();
		String a = "";
		for(UserGroup ug : ugList){
			a +="<li class='list-group-item'>"+ug.name
					+ "<span class='badge'>" + ug.getPercent() 
					+ "%<span class='label'>Answered:"
					+ ug.total + "</span></span></li>";
		}
		return a;
	}
}

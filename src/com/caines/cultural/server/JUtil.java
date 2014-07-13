package com.caines.cultural.server;

import java.util.List;

import com.caines.cultural.client.ui.ProfileGroups;
import com.caines.cultural.shared.datamodel.UserGroup;

public class JUtil {
	public static String showList(){
		List<UserGroup> ugList=new BasicServiceImpl().getUserGroupList();
		String a = "";
		for(UserGroup ug : ugList){

			String label = getColorLabel(ug);
			a +="<li class='list-group-item list-group-item-"+label+"'>"+ug.name
					+ "<span class='badge'>" + ug.getPercent() 
					+ "%<span class='label'>Answered:"
					+ ug.total + " Tries:"+ug.tries+"</span></span></li>";
		}
		return a;
	}
	public static String getColorLabel(UserGroup ug) {
		String label = "danger";
		if(ug.getPercent() > 75){
			label = "success";
		}
		else if(ug.getPercent() > 50){
			label = "info";							
		}
		else if(ug.getPercent() > 25){
			label = "warning";
		}
		return label;
	}

}

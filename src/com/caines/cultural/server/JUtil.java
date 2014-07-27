package com.caines.cultural.server;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.caines.cultural.client.ui.ProfileGroups;
import com.caines.cultural.shared.datamodel.UserGroup;

public class JUtil {
	public static String showList(HttpServletRequest req, HttpServletResponse resp){
		List<UserGroup> ugList=new BasicServiceImpl().getUserGroupList(LoginService.login(req, resp));
		String a = "";
		for(UserGroup ug : ugList){
			if(ug.total == 0){
				continue;
			}
			String label = getColorLabel(ug);
			a +="<li class='list-group-item list-group-item-"+label+"'>"+ug.name
					+ "<span class='badge'>" + ug.getPercent() 
					+ "%<span class='label'>Answered:"
					+ ug.total + " Tries:"+ug.tries+" Average seconds:"+(int)ug.milliSecondsTaken/ug.total *.001+"</span></span></li>";
		}
		req.setAttribute("person", a);
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

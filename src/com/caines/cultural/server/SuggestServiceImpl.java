package com.caines.cultural.server;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.caines.cultural.client.suggestion.ItemAmountSuggestion;
import com.caines.cultural.client.suggestion.ItemSuggestion;
import com.caines.cultural.client.suggestion.SuggestService;
import com.caines.cultural.server.datautil.PermissionsUtil;
import com.caines.cultural.shared.LoginInfo;
import com.caines.cultural.shared.datamodel.Group;
import com.caines.cultural.shared.datamodel.GroupNameChopped;
import com.caines.cultural.shared.datamodel.Tag;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class SuggestServiceImpl extends RemoteServiceServlet implements
		SuggestService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SuggestOracle.Response getGroup(SuggestOracle.Request req) {
		String lowerCase = req.getQuery().toLowerCase();
		
		List<GroupNameChopped> gList = SDao.getGroupNameChoppedDao()
				.fieldStartsWith("section", lowerCase).limit(40).list();
		SuggestOracle.Response resp = new SuggestOracle.Response();
		List<Suggestion> suggestions = new ArrayList<Suggestion>();
		Set<String> set = new HashSet<>();
		for (GroupNameChopped g : gList) {
			set.add(g.groupName);
		}
		int count = 0;
		for (String s : set) {
			count++;
			if(count > 10){
				break;
			}
			suggestions.add(new ItemSuggestion(s));			
		}
		resp.setSuggestions(suggestions);
		return resp;
	}

	@Override
	public SuggestOracle.Response getTopTags(SuggestOracle.Request req) {
		String[] s = req.getQuery().split(",");
		List<Tag> tList = SDao.getTagDao()
				.fieldStartsWith("lowerName", s[s.length-1].trim()).limit(20)
				.order("amount").list();
		SuggestOracle.Response resp = new SuggestOracle.Response();
		List<Suggestion> suggestions = new ArrayList<Suggestion>();

		for (Tag t : tList) {
			suggestions.add(new ItemAmountSuggestion(t.name, t.amount));
		}
		resp.setSuggestions(suggestions);
		return resp;
	}
}
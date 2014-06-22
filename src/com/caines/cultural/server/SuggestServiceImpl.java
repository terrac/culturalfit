package com.caines.cultural.server;

import java.util.ArrayList;
import java.util.List;

import com.caines.cultural.client.suggestion.ItemAmountSuggestion;
import com.caines.cultural.client.suggestion.ItemSuggestion;
import com.caines.cultural.client.suggestion.SuggestService;
import com.caines.cultural.server.datautil.PermissionsUtil;
import com.caines.cultural.shared.LoginInfo;
import com.caines.cultural.shared.datamodel.Group;
import com.caines.cultural.shared.datamodel.Tag;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class SuggestServiceImpl extends RemoteServiceServlet implements
		SuggestService {

	public SuggestOracle.Response getGroup(SuggestOracle.Request req) {
		LoginInfo li = LoginService.login(null, null);
		
		List<Group> gList = SDao.getGroupDao()
				.fieldStartsWith("lowerName", req.getQuery().toLowerCase()).limit(10).list();
		SuggestOracle.Response resp = new SuggestOracle.Response();
		List<Suggestion> suggestions = new ArrayList<Suggestion>();

		for (Group g : gList) {
			suggestions.add(new ItemSuggestion(g.name,PermissionsUtil.canEdit(li.gUser,g)));			
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
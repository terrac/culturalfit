package com.caines.cultural.client;

import com.caines.cultural.client.suggestion.ItemSuggestOracle;
import com.caines.cultural.client.suggestion.SuggestService;
import com.caines.cultural.client.suggestion.ItemSuggestOracle.ItemSuggestCallback;

public class TagSelect extends ItemSuggestOracle {

	@Override
	public void requestSuggestions(Request request, Callback callback) {
	    SuggestService.Util.getInstance().getTopTags(request, new ItemSuggestCallback(request, callback));
	}
	
}

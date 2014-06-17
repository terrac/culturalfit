package com.caines.cultural.server.datautil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.caines.cultural.server.SDao;
import com.caines.cultural.shared.datamodel.Tag;
import com.googlecode.objectify.Key;

public class TagUtil {

	public static List<Key<Tag>> getTagKeys(String tags) {
		List<Key<Tag>> tagKeyList = new ArrayList<>();
		if(tags.isEmpty()){
			return tagKeyList;
		}
		for(String t: tags.split(",")){
			tagKeyList.add(getKey(t));
		}
		Map<Key<Tag>, Tag> map = SDao.getTagDao().ofy().get(tagKeyList);
		for(Tag t : map.values()){
			t.amount++;
		}
		SDao.getTagDao().putAll(map.values());
		return tagKeyList;
	}

	public static Key<Tag> getKey(String t) {
		return new Tag(t).getKey();
	}

}

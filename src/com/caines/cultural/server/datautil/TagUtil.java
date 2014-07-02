package com.caines.cultural.server.datautil;

import java.util.List;
import com.caines.cultural.shared.datamodel.Tag;
import com.googlecode.objectify.Ref;

public class TagUtil {

	public static List<Ref<Tag>> getTagRefs(String tags) {
//		List<Ref<Tag>> tagRefList = new ArrayList<>();
//		if(tags.isEmpty()){
//			return tagRefList;
//		}
//		for(String t: tags.split(",")){
//			tagRefList.add(Key.create(TagUtil.class,t));
//		}
//		OService.ofy().load().
//		Map<Ref<Ref<Tag>>, Ref<Tag>> map = OService.ofy().load().entities(tagRefList);
//		for(Ref<Tag> t : map.values()){
//			t.get().amount++;
//			OService.ofy().save().entity(t);
//		}
//		SDao.getTagDao().putAll(map.values());
		return null;
	}

	

}

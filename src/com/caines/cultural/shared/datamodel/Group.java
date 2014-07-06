package com.caines.cultural.shared.datamodel;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;























import java.util.Random;

import com.caines.cultural.server.SDao;
import com.google.common.annotations.GwtIncompatible;
import com.google.gwt.user.client.rpc.GwtTransient;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Group implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Group() {
	
	}
	
	@Id
	public Long id;
	
	@GwtIncompatible("")
	public Group(String groupname,GUser gUser) {
		if(gUser != null){
			creator = gUser.getRef();	
		}
		//id = groupname;
		name = groupname;
		lowerName = name.toLowerCase();
		
	}
	@GwtTransient
	public Ref<GUser> creator;
	public String name;
	@Index
	public String lowerName;
	public int seconds = 60 ;
	@GwtTransient
	@Index
	public Ref<Group> parent;

	@Override
	public String toString() {
		return "Group [id=" + id + ", name=" + name + "]";
	}

	@GwtIncompatible("")
	public Ref<Group> getRef(){
		return Ref.create(this);
	}

	@GwtIncompatible("")
	public void chopName() {
		for(String n : lowerName.split(" ")){
			SDao.getGroupNameChoppedDao().put(new GroupNameChopped(n, name));
		}
		SDao.getGroupNameChoppedDao().put(new GroupNameChopped(lowerName, name));
		
		Group g = SDao.getGroupDao().getByProperty("lowerName", lowerName.split(" ")[0]);
		if(g != null){
			parent = g.getRef();
		}
	}

	

	@GwtIncompatible("")
	public Ref<Group> getRandomUnansweredSibling(GUser gUser) {
	
		if(parent == null){
			return null;
		}
		List<NextGroup> ng=SDao.getNextGroupDao().listByProperty("user", gUser);
		if(ng.size() == 0){
			for(Group g : SDao.getGroupDao().listByProperty("parent", parent)){
				if(g.getRef().equals(this.getRef())){
					continue;
				}
				SDao.getNextGroupDao().put(new NextGroup(g.getRef(),gUser.getRef()));
			}
			ng=SDao.getNextGroupDao().listByProperty("user", gUser);
			
		}
		NextGroup n =ng.get(new Random().nextInt(ng.size()));
		SDao.getNextGroupDao().delete(n);
		//n.group.get()
		return n.group;
	}
	
	
}

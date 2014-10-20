package com.caines.cultural.shared.datamodel.codingscramble;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.common.annotations.GwtIncompatible;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class CodeContainer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CodeContainer() {

	}
	
	public List<String> file = new ArrayList<>();
	
	public List<String> hs = new ArrayList<>();
	
	public List<String> tags = new ArrayList<>();
	public int nextLink = 0;
	
	
	@Id
	public Long id;
	
	
	@Index
	public String url;
	public int nextLine;
	
	

	
	
	
	public String getRawFile(){
		StringBuilder c = new StringBuilder();				
		for(String a : file){
			c.append(a);
			c.append("\n");
		}
		return c.toString();
	}
}

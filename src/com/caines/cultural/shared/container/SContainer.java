package com.caines.cultural.shared.container;

import java.io.Serializable;
import java.util.List;

import com.google.gwt.safehtml.shared.SafeUri;

public class SContainer implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public List<String> rawFile;
	public List<String> hs;
	
	public String getRawFile(){
		StringBuilder c = new StringBuilder();				
		for(String a : rawFile){
			c.append(a);
			c.append("\n");
		}
		return c.toString();
	}	
	
}

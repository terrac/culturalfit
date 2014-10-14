package com.caines.cultural.shared.container;

import java.io.Serializable;
import java.util.List;

import com.google.gwt.safehtml.shared.SafeUri;

public class ScramblerQuestion implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String code1;
	public String code2;
	public List<String> rawFile;
	public List<String> rawFile2;
	public String url;
	public String linkedText;
	
	public String getRawFile(){
		StringBuilder c = new StringBuilder();				
		for(String a : rawFile){
			c.append(a);
			c.append("\n");
		}
		return c.toString();
	}
	public String getRawFile2(){
		StringBuilder c = new StringBuilder();				
		for(String a : rawFile2){
			c.append(a);
			c.append("\n");
		}
		return c.toString();
	}
}

package com.caines.cultural.server.datamodel.codingscramble;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;








import com.google.common.annotations.GwtIncompatible;
import com.google.gwt.user.client.rpc.GwtTransient;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class CodePath implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CodePath() {
	
	}
	//holdes a string
	//code containers are associated with
	String pathName;
	
	
	@Id
	public Long id;
		
	
	
	
	
}

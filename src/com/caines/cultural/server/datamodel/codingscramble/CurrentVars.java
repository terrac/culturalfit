package com.caines.cultural.server.datamodel.codingscramble;


import java.io.Serializable;














import com.google.common.annotations.GwtIncompatible;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class CurrentVars implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CurrentVars() {
	
	}
	
	public CodePointer cp1;
	public CodePointer cp2;
	public CodeAlgorithm ca;
}

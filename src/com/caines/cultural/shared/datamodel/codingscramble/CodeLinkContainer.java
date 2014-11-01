package com.caines.cultural.shared.datamodel.codingscramble;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.caines.cultural.server.datamodel.codingscramble.CodePointer;
import com.google.common.annotations.GwtIncompatible;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class CodeLinkContainer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CodeLinkContainer() {

	}

	public List<Integer> linkedPointers = new ArrayList<>();
	public List<Integer> highlyLinkedPointers = new ArrayList<>();
	public List<Integer> notLinkedPointers = new ArrayList<>();
	public String name;

	@Id
	public Long id;

	
}

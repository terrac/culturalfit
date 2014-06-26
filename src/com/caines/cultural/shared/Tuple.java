package com.caines.cultural.shared;

import java.io.Serializable;

public class Tuple<K,V> implements Serializable {
	public K a;
	public V b;
	public Tuple() {
		// TODO Auto-generated constructor stub
	}
	public Tuple(K a, V b) {
		super();
		this.a = a;
		this.b = b;
	}
}

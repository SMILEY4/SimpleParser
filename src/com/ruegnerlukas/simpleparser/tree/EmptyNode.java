package com.ruegnerlukas.simpleparser.tree;



public class EmptyNode extends Node {

	
	@Override
	public String toString() {
		return Integer.toHexString(this.hashCode()) + ": EMPTY";
	}
	
}

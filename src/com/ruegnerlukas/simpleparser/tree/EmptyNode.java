package com.ruegnerlukas.simpleparser.tree;



public class EmptyNode extends Node {

	
	@Override
	public String toString() {
		return "\"" + "Empty:"+Integer.toHexString(this.hashCode()) + ": EMPTY" + "\"";
	}
	
}

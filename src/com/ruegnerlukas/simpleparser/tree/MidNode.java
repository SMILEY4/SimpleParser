package com.ruegnerlukas.simpleparser.tree;

public class MidNode extends Node {

	public String name;
	
	public MidNode(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "\"" + "Mid:"+Integer.toHexString(this.hashCode()) + ": " + name + "\"";
	}
	
}

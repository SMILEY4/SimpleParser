package com.ruegnerlukas.simpleparser.tree;

public class PlaceholderNode extends Node {

	public String name;




	public PlaceholderNode() {
		this("");
	}


	public PlaceholderNode(String name) {
		this.name = name;
	}




	@Override
	public String toString() {
		return "\"" + "Placeholder:"+Integer.toHexString(this.hashCode()) + (name.isEmpty() ? "" : ": " + name) + "\"";
	}
	
}

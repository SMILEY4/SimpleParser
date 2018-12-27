package com.ruegnerlukas.simpleparser.tree;

import com.ruegnerlukas.simpleparser.expressions.Result;

import java.util.List;

public class PlaceholderNode extends Node {


	public PlaceholderNode() {
	}


	public PlaceholderNode(Node... children) {
		for(Node n : children) {
			this.addChild(n);
		}
	}


	public PlaceholderNode(List<Result> results) {
		for(Result r : results) {
			this.addChild(r.node);
		}
	}


	@Override
	public String toString() {
		return "\"" + "[PH] " + Integer.toHexString(this.hashCode()) + "-" + getExpression().toString().substring(1, getExpression().toString().length()-1) + "\"";
	}
	
}
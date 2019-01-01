package com.ruegnerlukas.v1.simpleparser.tree;

import com.ruegnerlukas.v1.simpleparser.expressions.Result;

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
		return "PH:" + Integer.toHexString(this.hashCode()) + "-" + (getExpression() == null ? "null" : getExpression().toString());
	}
	
}
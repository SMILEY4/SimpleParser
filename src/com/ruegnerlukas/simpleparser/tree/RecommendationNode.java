package com.ruegnerlukas.simpleparser.tree;

public class RecommendationNode extends Node {

	public RecommendationNode(Node... children) {
		for(Node node : children) {
			this.children.add(node);
		}
	}




	@Override
	public String toString() {
		return "\"" + "Recommendation:"+Integer.toHexString(this.hashCode()) + "\"";
	}

}

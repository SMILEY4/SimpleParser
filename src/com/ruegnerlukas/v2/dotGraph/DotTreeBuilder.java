package com.ruegnerlukas.v2.dotGraph;

import com.ruegnerlukas.v2.simpleparser.Node;

public class DotTreeBuilder {



	public static String build(Node root) {

		StringBuilder builder = new StringBuilder();
		builder.append("digraph G {").append(System.lineSeparator());
		builder.append("    node [style=filled];").append(System.lineSeparator());

		build(builder, root);

		builder.append("}");

		return builder.toString();
	}




	private static void build(StringBuilder builder, Node node) {
		for(Node n : node.children) {
			DotUtils.appendConnection(builder, asString(node), asString(n));
			build(builder, n);
		}
	}




	private static String asString(Node node) {
		String str = "\"" + "NullNode:" + Integer.toHexString(node.hashCode()) + "\"";
		if(node.expression != null) {
			str = "\"" + "Node:" + Integer.toHexString(node.hashCode()) + " - " + node.expression.toString() + "\"";
		}
		return str;
	}


}

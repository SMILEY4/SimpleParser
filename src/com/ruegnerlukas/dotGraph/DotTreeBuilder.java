package com.ruegnerlukas.dotGraph;

import com.ruegnerlukas.simpleparser.ErrorNode;
import com.ruegnerlukas.simpleparser.Node;

public class DotTreeBuilder {


	/**
	 * @return the tree starting with the given root-node as a string in the dot-language
	 */
	public static String build(Node root) {
		StringBuilder builder = new StringBuilder();
		builder.append("digraph G {").append(System.lineSeparator());
		builder.append("    node [style=filled];").append(System.lineSeparator());
		build(builder, root);
		builder.append("}");
		return builder.toString();
	}




	/**
	 * appends the subtree starting with the given node to the given StringBuilder
	 */
	private static void build(StringBuilder builder, Node node) {
		for (Node n : node.children) {
			DotUtils.appendConnection(builder, asString(node), asString(n));
			build(builder, n);
		}
		if (node instanceof ErrorNode) {
			DotUtils.appendStyle(builder, asString(node), 250, 0, 0);
		}
	}




	/**
	 * @return the given node as a (quoted) string
	 */
	private static String asString(Node node) {
		String str = "\"" + "NullNode:" + Integer.toHexString(node.hashCode()) + "\"";
		if (node instanceof ErrorNode) {
			if (node.expression != null) {
				str = "\"" + "Error: " + ((ErrorNode) node).error + " - " + Integer.toHexString(node.hashCode()) + " - " + node.expression.toString() + "\"";
			} else {
				str = "\"" + "Error:" + Integer.toHexString(node.hashCode()) + "\"";
			}
		} else {
			if (node.expression != null) {
				str = "\"" + "Node:" + Integer.toHexString(node.hashCode()) + " - " + node.expression.toString() + "\"";
			}
		}
		return str;
	}


}

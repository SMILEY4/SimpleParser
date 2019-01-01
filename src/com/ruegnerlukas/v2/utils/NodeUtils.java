package com.ruegnerlukas.v2.utils;

import com.ruegnerlukas.v2.simpleparser.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class NodeUtils {


	public static List<Node> flattenTree(Node root) {

		List<Node> list = new ArrayList<>();

		Stack<Node> stack = new Stack<>();
		stack.push(root);

		while(!stack.isEmpty()) {
			Node node = stack.pop();
			list.add(node);

			for(int i=node.children.size()-1; i>=0; i--) {
				stack.push(node.children.get(i));
			}
		}

		return list;
	}






	public List<Node> collectTerminals() {
		return null;
	}


}

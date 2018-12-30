package com.ruegnerlukas.simpleparser.systems;

import com.ruegnerlukas.simpleparser.grammar.Rule;
import com.ruegnerlukas.simpleparser.tree.Node;
import com.ruegnerlukas.simpleparser.tree.PlaceholderNode;
import com.ruegnerlukas.simpleparser.tree.RuleNode;
import com.ruegnerlukas.simpleparser.tree.TerminalNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NodeProcessor {


	public static List<TerminalNode> collectTerminals(Node root) {
		List<TerminalNode> terminals = new ArrayList<>();
		collectTerminals(root, terminals);
		return terminals;
	}


	private static void collectTerminals(Node node, List<TerminalNode> terminals) {
		if(node instanceof TerminalNode) {
			terminals.add((TerminalNode)node);
		} else {
			for(Node child : node.getChildren()) {
				collectTerminals(child, terminals);
			}
		}
	}




	public static Node eliminatePlaceholders(Node root) {
		List<Node> roots = eliminatePlaceholdersInternal(root);
		if(roots.size() == 0) {
			return null;
		} else if(roots.size() == 1) {
			return roots.get(0);
		} else {
			return new PlaceholderNode().addChildren(roots);
		}
	}

	private static List<Node> eliminatePlaceholdersInternal(Node root) {

		List<Node> newChildren = new ArrayList<>();
		for(Node child : root.getChildren()) {
			newChildren.addAll(eliminatePlaceholdersInternal(child));
		}

		if(root instanceof PlaceholderNode) {
			return newChildren;
		} else {
			root.getChildren().clear();
			root.getChildren().addAll(newChildren);
			return Collections.singletonList(root);
		}


//		for(Node child : root.getChildren()) {
//			eliminatePlaceholders(child);
//		}
//
//		List<Node> newChildren = new ArrayList<>();
//
//		for(Node child : root.getChildren()) {
//			if(child instanceof PlaceholderNode) {
//				newChildren.addAll(child.getChildren());
//			} else {
//				newChildren.add(child);
//			}
//		}
//
//		root.getChildren().clear();
//		root.getChildren().addAll(newChildren);

	}




	public static Node reduceRules(Node root, String... rules) {

		if(root instanceof RuleNode) {

			if(root.getChildren().size() == 1) {
				Rule rule =((RuleNode)root).rule;

				boolean foundName = false;
				for(String strRule : rules) {
					if(strRule.equalsIgnoreCase(rule.getName())) {
						foundName = true;
						break;
					}
				}

				if(foundName) {
					Node child = root.getChildren().get(0);
					return reduceRules(child, rules);

				} else {
					Node child = root.getChildren().get(0);
					root.getChildren().clear();
					root.getChildren().add(reduceRules(child, rules));
					return root;
				}

			} else {
				List<Node> list = new ArrayList<>();
				for(Node child : root.getChildren()) {
					list.add(reduceRules(child, rules));
				}
				root.getChildren().clear();
				root.getChildren().addAll(list);
				return root;
			}

		} else {
			List<Node> newChildren = new ArrayList<>();
			for(Node child : root.getChildren()) {
				newChildren.add(reduceRules(child, rules));
			}
			root.getChildren().clear();
			root.getChildren().addAll(newChildren);
			return root;
		}

	}



}

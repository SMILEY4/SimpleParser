package com.ruegnerlukas.simpleparser.tree;

import com.ruegnerlukas.simpleparser.grammar.Rule;

import java.util.ArrayList;
import java.util.List;

public abstract class Node {


	public List<Node> children = new ArrayList<>();




	/**
	 * Removes all {@link PlaceholderNode} from the subtree with this node as the root.
	 * */
	protected void eliminatePlaceholders() {
		
		for(Node child : children) {
			child.eliminatePlaceholders();
		}
		
		List<Node> newChildren = new ArrayList<>();
		
		for(Node child : children) {
			if(child instanceof PlaceholderNode) {
				newChildren.addAll(child.children);
			} else {
				newChildren.add(child);
			}
		}
		
		this.children = newChildren;
	}




	/**
	 * Removes specific {@link RuleNode} from the subtree with this node as the root and returns the (new) root of this subtree.
	 * @param rules the list of names of rules that will be removed from the subtree
	 * @return the new root of the subtree
	 * */
	protected Node collapseTree(String[] rules) {
		if(this instanceof RuleNode) {

			if(this.children.size() == 1) {
				Rule rule = ((RuleNode)this).rule;

				boolean foundName = false;
				for(String strRule : rules) {
					if(strRule.equalsIgnoreCase(rule.getName())) {
						foundName = true;
						break;
					}
				}

				if(foundName) {
					return this.children.get(0).collapseTree(rules);

				} else {
					Node child = children.get(0);
					children.clear();
					children.add(child.collapseTree(rules));
					return this;
				}

			} else {
				List<Node> list = new ArrayList<>();
				for(Node child : children) {
					list.add(child.collapseTree(rules));
				}
				this.children = list;
				return this;
			}

		} else {
			return this;
		}

	}




	/**
	 * Removes specific tokens/terminals from the subtree with this node as the root and returns the (new) root of this subtree.
	 * @param terminals the list of symbols that will be removed from the subtree
	 * @return the new root of the subtree
	 * */
	public Node removeTerminals(String[] terminals) {
		if(this instanceof RuleNode) {

			List<Node> toRemove = new ArrayList<>();
			for(Node child : children) {
				if(child instanceof TerminalNode) {
					TerminalNode node = (TerminalNode)child;

					for(String terminal : terminals) {
						if(node.token.getSymbol().equals(terminal)) {
							toRemove.add(child);
							break;
						}
					}

				} else {
					child.removeTerminals(terminals);
				}
			}
			children.removeAll(toRemove);
		}
		return this;
	}




	/**
	 * @return the subtree with this node as root as a tree in the DOT-format
	 * */
	public String createDotTree() {
		StringBuilder builder = new StringBuilder();
		this.createDotTree(true, builder);
		return builder.toString();
	}




	/**
	 * Creates a tree in the DOT-Format and appends it to the given StringBuilder.
	 * @param isRoot whether this node is the root of the complete tree
	 * */
	protected void createDotTree(boolean isRoot, StringBuilder builder) {
		
		if(isRoot) {
			builder.append("digraph G {").append(System.lineSeparator());
			builder.append("    node [style=filled];").append(System.lineSeparator());
		}
		
		for(Node child : children) {
			builder.append("    ").append(this).append(" -> ").append(child).append(';').append(System.lineSeparator());
		}
		for(Node child : children) {
			child.createDotTree(false, builder);
		}

		if(isRoot) {
			builder.append('}').append(System.lineSeparator());
		}
	}

}
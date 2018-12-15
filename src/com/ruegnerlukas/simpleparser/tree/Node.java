package com.ruegnerlukas.simpleparser.tree;

import com.ruegnerlukas.simpleparser.grammar.Rule;

import java.util.ArrayList;
import java.util.List;

public abstract class Node {


	public List<Node> children = new ArrayList<Node>();




	protected void eliminateMidNodes() {
		
		for(Node child : children) {
			child.eliminateMidNodes();
		}
		
		List<Node> newChildren = new ArrayList<Node>();
		
		for(Node child : children) {
			if(child instanceof MidNode) {
				newChildren.addAll(child.children);
			} else {
				newChildren.add(child);
			}
		}
		
		this.children = newChildren;
	}



	protected Node collapseTree(String[] rules) {
		if(this instanceof RuleNode) {

			if(this.children.size() == 1) {
				Rule rule = ((RuleNode)this).rule;

				boolean foundName = false;
				for(String strRule : rules) {
					if(strRule.equalsIgnoreCase(rule.name)) {
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




	public Node removeTerminals(String[] terminals) {
		if(this instanceof RuleNode) {

			List<Node> toRemove = new ArrayList<>();
			for(Node child : children) {
				if(child instanceof TerminalNode) {
					TerminalNode node = (TerminalNode)child;

					for(String terminal : terminals) {
						if(node.token.symbol.equals(terminal)) {
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




	public void printGraphViz(boolean root) {
		
		if(root) {
			System.out.println("digraph G {");
			System.out.println("    node [style=filled];");
		}
		
		for(Node child : children) {
			System.out.println("    " + this + " -> " + child + ";");
		}
		for(Node child : children) {
			child.printGraphViz(false);
		}

		if(this instanceof RecommendationNode) {
			System.out.println("    " + this + " [color=\"1.0 1.0 1.0\"];");
		}

		if(root) {
			System.out.println("}");
		}
		
	}




}

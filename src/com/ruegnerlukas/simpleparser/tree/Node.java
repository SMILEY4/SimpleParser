package com.ruegnerlukas.simpleparser.tree;

import com.ruegnerlukas.sandbox.debug.DebugManager;
import com.ruegnerlukas.simpleparser.expressions.Expression;
import com.ruegnerlukas.simpleparser.expressions.Result;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Node {


	private List<Node> children = new ArrayList<>();
	private Node parent;
	private Expression expression;

	public Result.State state = Result.State.MATCH;



	public Node setError() {
		this.state = Result.State.ERROR;
		return this;
	}

	public Node setState(Result.State state) {
		this.state = state;
		return this;
	}


	public Node setExpression(Expression expression) {
		this.expression = expression;
		return this;
	}




	public Expression getExpression() {
		return expression;
	}




	public void addChild(Node node) {
		node.setParent(this);
		DebugManager.nodeAdded(node);
		this.children.add(node);
	}




	public Node addChildren(Collection<Node> nodes) {
		for (Node n : nodes) {
			n.setParent(this);
			DebugManager.nodeAdded(n);
		}
		this.children.addAll(nodes);
		return this;
	}




	public void removeChild(Node node) {
		this.children.remove(node);
	}



	public List<Node> getChildren() {
		return children;
	}




	private void setParent(Node parent) {
		this.parent = parent;
	}




	public Node getParent() {
		return this.parent;
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

}
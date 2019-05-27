package com.ruegnerlukas.simpleparser;

import com.ruegnerlukas.simpleparser.expressions.Expression;
import com.ruegnerlukas.simpleparser.expressions.ExpressionType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Node {


	public List<Node> children = new ArrayList<>();
	public Expression expression;




	public Node() {
	}




	public Node(List<Node> children) {
		this.children.addAll(children);
	}




	public Node setExpression(Expression expression) {
		this.expression = expression;
		return this;
	}




	@Override
	public String toString() {
		return "Node:" + expression + "  ";
	}




	/**
	 * removes all error-nodes in the subtree with this node as its root
	 *
	 * @return this node
	 */
	public Node eliminateErrorNodes() {
		List<Node> nodes = new ArrayList<>();
		for (Node child : children) {
			Node n = child.eliminateErrorNodes();
			if (n != null) {
				nodes.add(n);
			}
		}
		this.children = nodes;
		return this;
	}




	/**
	 * removes all nodes that are not a rule or a token from the subtree with this node as its root.
	 *
	 * @return the (new) root node of the subtree
	 */
	public List<Node> eliminateNonRuleNodes() {
		List<Node> nodes = new ArrayList<>();
		for (Node child : children) {
			nodes.addAll(child.eliminateNonRuleNodes());
		}
		if (this.expression != null && (this.expression.getType() == ExpressionType.RULE || this.expression.getType() == ExpressionType.TOKEN || this.expression.getType() == ExpressionType.VARIABLE)) {
			this.children = nodes;
			return new ArrayList<>(Collections.singletonList(this));
		} else {
			return nodes;
		}
	}




	/**
	 * removes all leaf-nodes that are not a rule or a token from the subtree with this node as its root.
	 *
	 * @return the (new) root node of the subtree
	 */
	public Node eliminateNonRuleLeafs() {
		if (this.children.isEmpty()) {
			if (this.expression.getType() == ExpressionType.TOKEN || this.expression.getType() == ExpressionType.RULE|| this.expression.getType() == ExpressionType.VARIABLE || (this instanceof ErrorNode)) {
				return this;
			} else {
				return null;
			}

		} else {
			List<Node> nodes = new ArrayList<>();
			for (Node child : children) {
				Node n = child.eliminateErrorNodes();
				if (n != null) {
					nodes.add(n);
				}
			}
			this.children = nodes;
			return this;
		}
	}




	/**
	 * removes all leaf-nodes that are not a token/terminal from the subtree with this node as its root.
	 *
	 * @return the (new) root node of the subtree
	 */
	public Node eliminateNonTerminalLeafs() {

		if (this.children.isEmpty()) {
			if (this.expression.getType() == ExpressionType.TOKEN || this.expression.getType() == ExpressionType.VARIABLE) {
				return this;
			} else {
				return null;
			}

		} else {
			List<Node> nodes = new ArrayList<>();
			for (Node child : children) {
				Node n = child.eliminateNonTerminalLeafs();
				if (n != null) {
					nodes.add(n);
				}
			}
			this.children = nodes;
			if (this.children.isEmpty()) {
				return this.eliminateNonTerminalLeafs();
			} else {
				return this;
			}
		}
	}




	/**
	 * @return a list of all leaf-nodes of the subtree with this node as its root
	 */
	public List<Node> collectLeafNodes() {
		List<Node> leafs = new ArrayList<>();
		collectLeafNodes(leafs);
		return leafs;
	}




	/**
	 * adds all leaf-nodes of the subtree with this node as its root to the supplied list
	 */
	private void collectLeafNodes(List<Node> leafs) {
		if (children.isEmpty()) {
			leafs.add(this);
		} else {
			for (Node child : children) {
				child.collectLeafNodes(leafs);
			}
		}
	}


}

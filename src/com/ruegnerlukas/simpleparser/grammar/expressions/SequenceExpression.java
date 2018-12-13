package com.ruegnerlukas.simpleparser.grammar.expressions;

import java.util.ArrayList;
import java.util.List;

import com.ruegnerlukas.simpleparser.grammar.Token;
import com.ruegnerlukas.simpleparser.tree.EmptyNode;
import com.ruegnerlukas.simpleparser.tree.MidNode;
import com.ruegnerlukas.simpleparser.tree.Node;

/**
 * X -> E0 E1 ... EN
 * */
public class SequenceExpression extends Expression {

	public List<Expression> expressions = new ArrayList<Expression>();

	public SequenceExpression(Expression... expressions) {
		for(Expression expr : expressions) {
			this.expressions.add(expr);
		}
	}

	


	@Override
	public Node apply(List<Token> tokens) {
		Node node = new MidNode(Integer.toHexString(this.hashCode()));
		for(Expression expr : expressions) {
			Node n = expr.apply(tokens);
			if(n instanceof EmptyNode) {
				break;
			} else {
				node.children.add(n);
			}
		}
		if(node.children.isEmpty()) {
			return new EmptyNode();
		} else {
			return node;
		}
	}
	
	
}







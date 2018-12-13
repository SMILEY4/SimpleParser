package com.ruegnerlukas.simpleparser.grammar.expressions;

import java.util.List;

import com.ruegnerlukas.simpleparser.grammar.Token;
import com.ruegnerlukas.simpleparser.tree.EmptyNode;
import com.ruegnerlukas.simpleparser.tree.Node;

/**
 * X -> [E]
 *  => none or one E
 * */
public class OptionalExpression extends Expression {

	
	public Expression expression = null;
	
	
	
	
	public OptionalExpression(Expression expression) {
		this.expression = expression;
	}

	
	

	@Override
	public Node apply(List<Token> tokens) {
		if(tokens.isEmpty()) {
			return new EmptyNode();
		} else {
			Node n = expression.apply(tokens);
			if(n instanceof EmptyNode) {
				return new EmptyNode();
			} else {
				return n;
			}
		}
	}


}

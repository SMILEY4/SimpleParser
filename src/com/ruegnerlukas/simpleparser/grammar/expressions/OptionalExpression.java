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

	public Expression op = null;
	
	public OptionalExpression(Expression op) {
		this.op = op;
	}


	@Override
	public Node apply(List<Token> tokens, int level) {
		if(tokens.isEmpty()) {
			return new EmptyNode();
		} else {
			Node n = op.apply(tokens, level+1);
			if(n instanceof EmptyNode) {
				return new EmptyNode();
			} else {
				return n;
			}
		}
	}
	
}

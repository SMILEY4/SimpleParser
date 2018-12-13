package com.ruegnerlukas.simpleparser.grammar.expressions;

import java.util.ArrayList;
import java.util.List;

import com.ruegnerlukas.simpleparser.grammar.Token;
import com.ruegnerlukas.simpleparser.tree.EmptyNode;
import com.ruegnerlukas.simpleparser.tree.Node;

/**
 * X -> E0 | E1 | ... | En
 * */
public class AlternativeExpression extends Expression {

	
	public List<Expression> expressions = new ArrayList<Expression>();

	
	
	
	public AlternativeExpression(Expression... expressions) {
		for(Expression expr : expressions) {
			this.expressions.add(expr);
		}
	}

	
	

	@Override
	public Node apply(List<Token> tokens) {
		for(Expression expr : expressions) {
			Node n = expr.apply(tokens);
			if( !(n instanceof EmptyNode) ) {
				return n;
			}
		}
		return new EmptyNode();
	}


}

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

	
	public List<Expression> ops = new ArrayList<Expression>();

	
	public AlternativeExpression(Expression... ops) {
		for(Expression op : ops) {
			this.ops.add(op);
		}
	}

	
	

	@Override
	public Node apply(List<Token> tokens, int level) {
		for(Expression op : ops) {
			Node n = op.apply(tokens, level+1);
			if( !(n instanceof EmptyNode) ) {
				return n;
			}
		}
		return new EmptyNode();
	}
	
}

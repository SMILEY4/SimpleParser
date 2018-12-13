package com.ruegnerlukas.simpleparser.grammar.expressions;

import java.util.List;

import com.ruegnerlukas.simpleparser.grammar.Token;
import com.ruegnerlukas.simpleparser.tree.EmptyNode;
import com.ruegnerlukas.simpleparser.tree.MidNode;
import com.ruegnerlukas.simpleparser.tree.Node;

/**
 * X -> E E*
 *  => one or more
 * */
public class RepetitionExpression extends Expression {

	
	public Expression expression = null;
	
	
	
	
	public RepetitionExpression(Expression expression) {
		this.expression = expression;
	}

	
	

	@Override
	public Node apply(List<Token> tokens) {
		
		if(tokens.isEmpty()) {
			return new EmptyNode();
			
		} else {
			Node node = new MidNode(Integer.toHexString(this.hashCode()));
			while(true) {
				Node n = expression.apply(tokens);
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

	
}

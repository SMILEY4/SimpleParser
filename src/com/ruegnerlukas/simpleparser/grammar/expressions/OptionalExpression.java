package com.ruegnerlukas.simpleparser.grammar.expressions;

import com.ruegnerlukas.simpleparser.grammar.Token;
import com.ruegnerlukas.simpleparser.tree.EmptyNode;
import com.ruegnerlukas.simpleparser.tree.Node;

import java.util.List;
import java.util.Set;

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




	@Override
	public String toString() {
		return "\"" + "OPTIONAL:"+Integer.toHexString(this.hashCode()) + "\"";
	}




	@Override
	public void printAsDotGraph(Set<Expression> visited) {
		if(visited.contains(this)) {
			return;
		}
		visited.add(this);


		System.out.println("    " + this + " -> " + expression + ";");
		expression.printAsDotGraph(visited);

	}


}

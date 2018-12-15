package com.ruegnerlukas.simpleparser.grammar.expressions;

import com.ruegnerlukas.simpleparser.grammar.Token;
import com.ruegnerlukas.simpleparser.tree.EmptyNode;
import com.ruegnerlukas.simpleparser.tree.MidNode;
import com.ruegnerlukas.simpleparser.tree.Node;

import java.util.List;
import java.util.Set;

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
			while(!tokens.isEmpty()) {
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




	@Override
	public String toString() {
		return "\"" + "REPETITION:"+Integer.toHexString(this.hashCode()) + "\"";
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

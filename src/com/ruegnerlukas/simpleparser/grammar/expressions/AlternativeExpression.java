package com.ruegnerlukas.simpleparser.grammar.expressions;

import com.ruegnerlukas.simpleparser.grammar.Token;
import com.ruegnerlukas.simpleparser.tree.EmptyNode;
import com.ruegnerlukas.simpleparser.tree.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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




	@Override
	public String toString() {
		return "\"" + "ALTERNATIVE:"+Integer.toHexString(this.hashCode())+"\"";
	}




	@Override
	public void printAsDotGraph(Set<Expression> visited) {
		if(visited.contains(this)) {
			return;
		}
		visited.add(this);


		for(Expression e : expressions) {
			System.out.println("    " + this + " -> " + e + ";");
		}
		for(Expression e : expressions) {
			e.printAsDotGraph(visited);
		}

	}

}

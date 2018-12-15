package com.ruegnerlukas.simpleparser.grammar.expressions;

import com.ruegnerlukas.simpleparser.grammar.Token;
import com.ruegnerlukas.simpleparser.tree.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
			if(tokens.isEmpty()) {
				RecommendationNode recommendation = new RecommendationNode();
				if(expr instanceof TokenExpression) {
					recommendation.children.add(new TerminalNode( ((TokenExpression)expr).token ));
				}
				if(expr instanceof RuleExpression) {
					recommendation.children.add(new RuleNode( ((RuleExpression)expr).rule ));
				}
				node.children.add(recommendation);
			} else {
				Node n = expr.apply(tokens);
				if(n instanceof EmptyNode) {
					break;
				} else {
					node.children.add(n);
				}
			}
		}
		if(node.children.isEmpty()) {
			return new EmptyNode();
		} else {
			return node;
		}
	}




	@Override
	public String toString() {
		return "\"" + "SEQUENCE:"+Integer.toHexString(this.hashCode())+"\"";
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







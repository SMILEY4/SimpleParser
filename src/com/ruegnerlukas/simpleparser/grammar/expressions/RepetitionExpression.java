package com.ruegnerlukas.simpleparser.grammar.expressions;

import com.ruegnerlukas.simpleparser.grammar.Token;
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
	public Result apply(List<Token> consumed, List<Token> tokens) {

		System.out.println("APPLY " + this);

		Node node = new MidNode(Integer.toHexString(this.hashCode()));

		while (!tokens.isEmpty()) {
			Result resultExpr = expression.apply(consumed, tokens);

			if (resultExpr.state == Result.State.SUCCESS) {
				node.children.add(resultExpr.node);
			}
			if (resultExpr.state == Result.State.END_OF_STREAM) {
				return new Result(Result.State.SUCCESS, node);
			}
			if (resultExpr.state == Result.State.UNEXPECTED_SYMBOL) {
				return new Result(Result.State.SUCCESS, node);
			}
			if (resultExpr.state == Result.State.ERROR) {
				break;
//				return new Result(Result.State.ERROR, null, resultExpr.message);
			}
		}

		return new Result(Result.State.SUCCESS, node);
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

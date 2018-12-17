package com.ruegnerlukas.simpleparser.grammar.expressions;

import com.ruegnerlukas.simpleparser.grammar.Token;
import com.ruegnerlukas.simpleparser.tree.PlaceholderNode;
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
	public Result apply(List<Token> consumed, List<Token> tokens, List<Expression> trace) {
		if(trace != null) {
			trace.add(this);
		}

		Node node = new PlaceholderNode(Integer.toHexString(this.hashCode()));

		while (!tokens.isEmpty()) {
			Result resultExpr = expression.apply(consumed, tokens, trace);

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
	public void createDotGraph(Set<Expression> visited, StringBuilder builder) {
		if(visited.contains(this)) {
			return;
		}
		visited.add(this);
		builder.append("    ").append(this).append(" -> ").append(expression).append(';').append(System.lineSeparator());
		expression.createDotGraph(visited, builder);
	}

	
}

package com.ruegnerlukas.simpleparser.grammar.expressions;

import com.ruegnerlukas.simpleparser.ErrorMessages;
import com.ruegnerlukas.simpleparser.grammar.Token;
import com.ruegnerlukas.simpleparser.tree.PlaceholderNode;

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
	public Result apply(List<Token> consumed, List<Token> tokens, List<Expression> trace) {
		if(trace != null) {
			trace.add(this);
		}

		if(tokens.isEmpty()) {
			return new Result(Result.State.SUCCESS, new PlaceholderNode());

		} else {

			Result resultExpr = expression.apply(consumed, tokens, trace);

			if(resultExpr.state == Result.State.SUCCESS) {
				return new Result(Result.State.SUCCESS, resultExpr.node);
			}
			if(resultExpr.state == Result.State.END_OF_STREAM) {
				return new Result(Result.State.SUCCESS, resultExpr.node == null ? new PlaceholderNode() : resultExpr.node);
			}
			if(resultExpr.state == Result.State.UNEXPECTED_SYMBOL) {
				return new Result(Result.State.SUCCESS, new PlaceholderNode());
			}
			if(resultExpr.state == Result.State.ERROR) {
				return new Result(Result.State.SUCCESS, new PlaceholderNode());
//				return new Result(Result.State.ERROR, null, resultExpr.message);
			}

			return new Result(Result.State.ERROR, null, ErrorMessages.genMessage_endOfStream());
		}
	}




	@Override
	public String toString() {
		return "\"" + "OPTIONAL:"+Integer.toHexString(this.hashCode()) + "\"";
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

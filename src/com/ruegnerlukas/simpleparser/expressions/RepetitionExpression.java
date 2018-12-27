package com.ruegnerlukas.simpleparser.expressions;

import com.ruegnerlukas.simpleparser.tokens.Token;
import com.ruegnerlukas.simpleparser.tree.Node;
import com.ruegnerlukas.simpleparser.tree.PlaceholderNode;
import com.ruegnerlukas.simpleparser.tree.TraceElement;

import java.util.List;
import java.util.Set;

public class RepetitionExpression extends Expression {

	
	public Expression expression;




	/**
	 * X -> E E*
	 *  => one or more
	 * */
	public RepetitionExpression(Expression expression) {
		super(ExpressionType.REPETITION);
		this.expression = expression;
	}




	public boolean isOptionalExpression() {
		return true;
	}




	public boolean collectPossibleTokens(Expression start, Set<Token> tokens) {
		if(start != null) {
			collectPossibleTokens(tokens);
		}
		return true;
	}




	public void collectPossibleTokens(Set<Token> tokens) {
		expression.collectPossibleTokens(tokens);
	}


	

	@Override
	public Result apply(List<Token> consumed, List<Token> tokens, List<TraceElement> trace) {

		// handle trace
		TraceElement traceElement = null;
		if(trace != null) {
			traceElement = new TraceElement(this, Result.State.MATCH);
			trace.add(traceElement);
		}

		// create node
		Node node = new PlaceholderNode().setExpression(this);

		// repeat until end (if possible)
		while (!tokens.isEmpty()) {

			// apply expression
			Result result = expression.apply(consumed, tokens, trace);

			// matching -> add result and continue
			if (result.state == Result.State.MATCH) {
				node.addChild(result.node);
				continue;
			}

			// does not match -> return matching nodes
			if (result.state == Result.State.NO_MATCH) {
				return Result.match( node );
			}

			// encountered error -> return error/result
			if (result.state == Result.State.ERROR) {
				if(traceElement != null) { traceElement.state = Result.State.ERROR; }
				return result;
			}
		}

		// no tokens remaining -> return matching nodes
		return Result.match( node );
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

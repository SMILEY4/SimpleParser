package com.ruegnerlukas.simpleparser.expressions;

import com.ruegnerlukas.simpleparser.errors.UndefinedStateError;
import com.ruegnerlukas.simpleparser.tokens.Token;
import com.ruegnerlukas.simpleparser.tree.PlaceholderNode;
import com.ruegnerlukas.simpleparser.tree.TraceElement;

import java.util.List;
import java.util.Set;

public class OptionalExpression extends Expression {

	
	public Expression expression;




	/**
	 * X -> [E]
	 *  => none or one E
	 * */
	public OptionalExpression(Expression expression) {
		super(ExpressionType.OPTIONAL);
		this.expression = expression;
	}



	public boolean isOptionalExpression() {
		return true;
	}



	public boolean collectPossibleTokens(Expression start, Set<Token> tokens) {
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

		// no tokens remaining -> MATCH
		if(tokens.isEmpty()) {
			return Result.match(
				new PlaceholderNode().setExpression(this)
			);


		// tokens remaining
		} else {

			// apply expression
			Result result = expression.apply(consumed, tokens, trace);

			// is matching
			if(result.state == Result.State.MATCH) {
				return result;
			}

			// does not match
			if(result.state == Result.State.NO_MATCH) {
				return Result.match( result.node );
			}

			// encountered error
			if(result.state == Result.State.ERROR) {
				if(traceElement != null) { traceElement.state = Result.State.ERROR; }
				return result;
			}

			// none of the above
			if(traceElement != null) { traceElement.state = Result.State.ERROR; }
			return Result.error(
					new PlaceholderNode().setExpression(this).setError(),
					new UndefinedStateError(this, consumed)
			);
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

package com.ruegnerlukas.v1.simpleparser.systems;

import com.ruegnerlukas.v1.simpleparser.expressions.Expression;
import com.ruegnerlukas.v1.simpleparser.expressions.Result;

public class TraceElement {

	public Expression expression	= null;
	public Result.State state		= Result.State.MATCH;


	public TraceElement setExpression(Expression expression) {
		this.expression = expression;
		return this;
	}

	public TraceElement setState(Result.State state) {
		this.state = state;
		return this;
	}

	@Override
	public String toString() {
		return expression.toString() + ": " + state.toString();
	}

}

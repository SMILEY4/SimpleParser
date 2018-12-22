package com.ruegnerlukas.simpleparser.tree;

import com.ruegnerlukas.simpleparser.expressions.Expression;
import com.ruegnerlukas.simpleparser.expressions.Result;

public class TraceElement {

	public Expression expression;
	public Result.State state;


	public TraceElement(Expression expression, Result.State state) {
		this.expression = expression;
		this.state = state;
	}


	@Override
	public String toString() {
		return "[Trace]: " + expression + " -> " + state;
	}

}

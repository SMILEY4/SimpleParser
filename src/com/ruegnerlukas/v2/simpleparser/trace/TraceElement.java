package com.ruegnerlukas.v2.simpleparser.trace;

import com.ruegnerlukas.v2.simpleparser.expressions.Expression;
import com.ruegnerlukas.v2.simpleparser.grammar.State;

public class TraceElement {


	public Expression expression;
	public State state = null;




	public TraceElement(Expression expression) {
		this.expression = expression;
	}


	public TraceElement setState(State state) {
		this.state = state;
		return this;
	}


}

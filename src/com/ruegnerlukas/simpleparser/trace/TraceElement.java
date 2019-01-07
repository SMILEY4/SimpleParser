package com.ruegnerlukas.simpleparser.trace;

import com.ruegnerlukas.simpleparser.expressions.Expression;
import com.ruegnerlukas.simpleparser.grammar.State;

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

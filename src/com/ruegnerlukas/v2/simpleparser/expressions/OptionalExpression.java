package com.ruegnerlukas.v2.simpleparser.expressions;

import com.ruegnerlukas.v2.simpleparser.Node;
import com.ruegnerlukas.v2.simpleparser.Token;
import com.ruegnerlukas.v2.simpleparser.grammar.State;
import com.ruegnerlukas.v2.simpleparser.trace.Trace;
import com.ruegnerlukas.v2.simpleparser.trace.TraceElement;

import java.util.List;

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




	@Override
	public boolean isOptional() {
		return true;
	}




	@Override
	public State apply(Node root, List<Token> tokens, Trace trace) {

		TraceElement traceElement = new TraceElement(this);
		trace.add(traceElement);

		if(tokens.isEmpty()) {
			traceElement.setState(State.MATCH);
			return State.MATCH;

		} else {
			State state = expression.apply(root, tokens, trace);
			if(state == State.ERROR) {
				traceElement.setState(State.ERROR);
				return State.ERROR;
			} else {
				traceElement.setState(State.MATCH);
				return State.MATCH;
			}
		}

	}




	@Override
	public String toString() {
		return "OPTIONAL:"+Integer.toHexString(this.hashCode());
	}


}

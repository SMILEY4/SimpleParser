package com.ruegnerlukas.v2.simpleparser.expressions;

import com.ruegnerlukas.v2.simpleparser.Node;
import com.ruegnerlukas.v2.simpleparser.Token;
import com.ruegnerlukas.v2.simpleparser.grammar.State;
import com.ruegnerlukas.v2.simpleparser.trace.Trace;
import com.ruegnerlukas.v2.simpleparser.trace.TraceElement;

import java.util.List;

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




	@Override
	public boolean isOptional() {
		return true;
	}




	@Override
	public State apply(Node root, List<Token> tokens, Trace trace) {

		TraceElement traceElement = new TraceElement(this);
		trace.add(traceElement);

		Node node = new Node().setExpression(this);
		root.children.add(node);

		while(!tokens.isEmpty()) {

			State state = expression.apply(node, tokens, trace);

			if(state == State.MATCH) {
				continue;
			}

			if(state == State.NO_MATCH) {
				traceElement.setState(State.MATCH);
				return State.MATCH;
			}

			if(state == State.ERROR) {
				traceElement.setState(State.ERROR);
				return State.ERROR;
			}

		}

		traceElement.setState(State.MATCH);
		return State.MATCH;
	}




	@Override
	public String toString() {
		return "REPETITION:"+Integer.toHexString(this.hashCode());
	}


}

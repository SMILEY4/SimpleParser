package com.ruegnerlukas.simpleparser.expressions;

import com.ruegnerlukas.simpleparser.CharStream;
import com.ruegnerlukas.simpleparser.Node;
import com.ruegnerlukas.simpleparser.TokenStream;
import com.ruegnerlukas.simpleparser.grammar.State;
import com.ruegnerlukas.simpleparser.trace.Trace;
import com.ruegnerlukas.simpleparser.trace.TraceElement;

public class OptionalExpression extends Expression {


	public Expression expression;




	/**
	 * X -> [E]
	 * => none or one E
	 */
	public OptionalExpression(Expression expression) {
		super(ExpressionType.OPTIONAL);
		this.expression = expression;
	}




	@Override
	public boolean isOptional() {
		return true;
	}




	@Override
	public State apply(Node root, TokenStream tokenStream, Trace trace) {

		TraceElement traceElement = new TraceElement(this);
		trace.add(traceElement);

		if (!tokenStream.hasNext()) {
			traceElement.setState(State.MATCH);
			return State.MATCH;

		} else {
			State state = expression.apply(root, tokenStream, trace);
			if (state == State.ERROR) {
				traceElement.setState(State.ERROR);
				return State.ERROR;
			} else {
				traceElement.setState(State.MATCH);
				return State.MATCH;
			}
		}

	}




	@Override
	public State apply(Node root, CharStream charStream, boolean ignoreWhitespace, Trace trace) {

		TraceElement traceElement = new TraceElement(this);
		trace.add(traceElement);

		if (!charStream.hasNext()) {
			traceElement.setState(State.MATCH);
			return State.MATCH;

		} else {

			Node tmpParent = new Node().setExpression(this);
			State state = expression.apply(tmpParent, charStream, ignoreWhitespace, trace);

			if (state == State.ERROR) {
				root.children.addAll(tmpParent.children);
				traceElement.setState(State.ERROR);
				return State.ERROR;

			} else {
				if(state == State.MATCH) {
					root.children.addAll(tmpParent.children);
				}
				traceElement.setState(State.MATCH);
				return State.MATCH;
			}
		}

	}




	@Override
	public String toString() {
		return "OPTIONAL:" + Integer.toHexString(this.hashCode());
	}


}

package com.ruegnerlukas.v2.simpleparser.expressions;

import com.ruegnerlukas.v2.simpleparser.Node;
import com.ruegnerlukas.v2.simpleparser.Token;
import com.ruegnerlukas.v2.simpleparser.errors.ErrorStack;
import com.ruegnerlukas.v2.simpleparser.grammar.State;
import com.ruegnerlukas.v2.simpleparser.parser.Parser;
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
	public State apply(Node root, List<Token> tokens, Parser parser) {

		Trace trace = parser.getTrace();
		ErrorStack errorStack = parser.getErrorStack();

		TraceElement traceElement = new TraceElement(this);
		trace.add(traceElement);

		if(tokens.isEmpty()) {
			traceElement.setState(State.MATCH);
			return State.MATCH;

		} else {
			ErrorStack tmpErrorStack = new ErrorStack();
			State state = expression.apply(root, tokens, parser);
			if(state == State.ERROR) {
				traceElement.setState(State.ERROR);
				errorStack.addError(tmpErrorStack);
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

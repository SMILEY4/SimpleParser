package com.ruegnerlukas.simpleparser.expressions;

import com.ruegnerlukas.simpleparser.CharStream;
import com.ruegnerlukas.simpleparser.Node;
import com.ruegnerlukas.simpleparser.TokenStream;
import com.ruegnerlukas.simpleparser.grammar.State;
import com.ruegnerlukas.simpleparser.trace.Trace;
import com.ruegnerlukas.simpleparser.trace.TraceElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SequenceExpression extends Expression {


	public List<Expression> expressions = new ArrayList<>();




	/**
	 * X -> E0 E1 ... EN
	 */
	public SequenceExpression(Expression... expressions) {
		super(ExpressionType.SEQUENCE);
		this.expressions.addAll(Arrays.asList(expressions));
	}




	@Override
	public boolean isOptional() {
		for (Expression e : expressions) {
			if (!e.isOptional()) {
				return false;
			}
		}
		return true;
	}




	@Override
	public State apply(Node root, TokenStream tokenStream, Trace trace) {

		TraceElement traceElement = new TraceElement(this);
		trace.add(traceElement);

		if (!tokenStream.hasNext() && !this.isOptional()) {
//			root.children.add(new ErrorNode(ErrorType.UNEXPECTED_END_OF_INPUT, tokenStream.getIndex()).setExpression(this));
		}

		Node node = new Node().setExpression(this);
		root.children.add(node);

		for (Expression e : expressions) {
			State state = e.apply(node, tokenStream, trace);

			if (state == State.MATCH) {
				continue;
			}
			if (state == State.NO_MATCH) {
				traceElement.setState(State.NO_MATCH);
				return State.NO_MATCH;
			}
			if (state == State.ERROR) {
				traceElement.setState(State.ERROR);
				return State.ERROR;
			}

		}

		traceElement.setState(State.MATCH);
		return State.MATCH;
	}




	@Override
	public State apply(Node root, CharStream charStream, Trace trace) {

		TraceElement traceElement = new TraceElement(this);
		trace.add(traceElement);

		if (!charStream.hasNext() && !this.isOptional()) {
//			root.children.add(new ErrorNode(ErrorType.UNEXPECTED_END_OF_INPUT, tokenStream.getIndex()).setExpression(this));
		}

		Node node = new Node().setExpression(this);
		root.children.add(node);

		for (Expression e : expressions) {
			State state = e.apply(node, charStream, trace);

			if (state == State.MATCH) {
				continue;
			}
			if (state == State.NO_MATCH) {
				traceElement.setState(State.NO_MATCH);
				return State.NO_MATCH;
			}
			if (state == State.ERROR) {
				traceElement.setState(State.ERROR);
				return State.ERROR;
			}

		}

		traceElement.setState(State.MATCH);
		return State.MATCH;
	}




	@Override
	public String toString() {
		return "SEQUENCE:" + Integer.toHexString(this.hashCode());
	}


}
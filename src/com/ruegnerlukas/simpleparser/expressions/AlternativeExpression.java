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

public class AlternativeExpression extends Expression {


	public List<Expression> expressions = new ArrayList<>();




	/**
	 * E0 | E1 | ... | En
	 */
	public AlternativeExpression(Expression... expressions) {
		super(ExpressionType.ALTERNATIVE);
		this.expressions.addAll(Arrays.asList(expressions));
	}




	@Override
	public boolean isOptional() {
		for (Expression e : expressions) {
			if (e.isOptional()) {
				return true;
			}
		}
		return false;
	}




	@Override
	public State apply(Node root, TokenStream tokenStream, Trace trace) {

		TraceElement traceElement = new TraceElement(this);
		trace.add(traceElement);

		Node[] nodes = new Node[expressions.size()];
		State[] states = new State[expressions.size()];
		int[] nConsumed = new int[expressions.size()];

		// apply all expressions
		for (int i = 0; i < expressions.size(); i++) {
			Expression e = expressions.get(i);

			Node tmpParent = new Node().setExpression(this);
			TokenStream tmpStream = new TokenStream(tokenStream.getIndex(), tokenStream.getRemaining());

			State state = e.apply(tmpParent, tmpStream, trace);

			nodes[i] = tmpParent;
			states[i] = state;
			nConsumed[i] = tmpStream.getIndexWithoutOffset();
		}

		Node node = null;
		int n = -1;

		for (int i = 0; i < expressions.size(); i++) {
			if (states[i] == State.MATCH) {
				if (nConsumed[i] > n) {
					n = nConsumed[i];
					node = nodes[i];
				}
			}
		}

		if (node != null) {
			for (int i = 0; i < n; i++) {
				tokenStream.consume();
			}
			root.children.add(node);
			traceElement.setState(State.MATCH);
			return State.MATCH;

		} else {
			traceElement.setState(State.ERROR);

			int maxConsumed = 0;
			for (int i = 0; i < expressions.size(); i++) {
				if (nodes[i] != null) {
					maxConsumed = Math.max(maxConsumed, nConsumed[i]);
				}
			}

			Node altNode = new Node().setExpression(this);
			root.children.add(altNode);
			for (int i = 0; i < expressions.size(); i++) {
				if (nConsumed[i] == maxConsumed) {
					altNode.children.add(nodes[i]);
				}
			}

			if (altNode.children.isEmpty()) {
				altNode.children.addAll(Arrays.asList(nodes));
			}

			return State.ERROR;
		}

	}




	@Override
	public State apply(Node root, CharStream charStream, boolean ignoreWhitespace, Trace trace) {

		TraceElement traceElement = new TraceElement(this);
		trace.add(traceElement);

		Node[] nodes = new Node[expressions.size()];
		State[] states = new State[expressions.size()];
		int[] nConsumed = new int[expressions.size()];

		// apply all expressions
		for (int i = 0; i < expressions.size(); i++) {
			Expression e = expressions.get(i);

			Node tmpParent = new Node().setExpression(this);
			CharStream tmpStream = new CharStream(charStream.getIndex(), charStream.getRemaining());

			State state = e.apply(tmpParent, tmpStream, ignoreWhitespace, trace);

			nodes[i] = tmpParent;
			states[i] = state;
			nConsumed[i] = tmpStream.getIndexWithoutOffset();
		}

		Node node = null;
		int n = -1;

		for (int i = 0; i < expressions.size(); i++) {
			if (states[i] == State.MATCH) {
				if (nConsumed[i] > n) {
					n = nConsumed[i];
					node = nodes[i];
				}
			}
		}

		if (node != null) {
			charStream.consume(n);
			root.children.add(node);
			traceElement.setState(State.MATCH);
			return State.MATCH;

		} else {
			traceElement.setState(State.ERROR);

			int maxConsumed = 0;
			for (int i = 0; i < expressions.size(); i++) {
				if (nodes[i] != null) {
					maxConsumed = Math.max(maxConsumed, nConsumed[i]);
				}
			}

			Node altNode = new Node().setExpression(this);
			root.children.add(altNode);
			for (int i = 0; i < expressions.size(); i++) {
				if (nConsumed[i] == maxConsumed) {
					altNode.children.add(nodes[i]);
				}
			}

			if (altNode.children.isEmpty()) {
				altNode.children.addAll(Arrays.asList(nodes));
			}

			return State.ERROR;
		}

	}




	@Override
	public String toString() {
		return "ALTERNATIVE:" + Integer.toHexString(this.hashCode());
	}


}

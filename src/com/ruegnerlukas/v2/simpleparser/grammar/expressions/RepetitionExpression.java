package com.ruegnerlukas.v2.simpleparser.grammar.expressions;

import com.ruegnerlukas.v2.simpleparser.Node;
import com.ruegnerlukas.v2.simpleparser.Token;
import com.ruegnerlukas.v2.simpleparser.grammar.State;
import com.ruegnerlukas.v2.simpleparser.grammar.trace.Trace;

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

		Node node = new Node().setExpression(this);
		root.children.add(node);

		while(!tokens.isEmpty()) {

			State state = expression.apply(node, tokens, trace);

			if(state == State.MATCH) {
				continue;
			}

			if(state == State.NO_MATCH) {
				return State.MATCH;
			}

			if(state == State.ERROR) {

				return State.ERROR;
			}

		}

		return State.MATCH;
	}




	@Override
	public String toString() {
		return "REPETITION:"+Integer.toHexString(this.hashCode());
	}


}

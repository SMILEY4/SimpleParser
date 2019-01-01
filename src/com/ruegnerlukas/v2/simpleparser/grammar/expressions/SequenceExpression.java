package com.ruegnerlukas.v2.simpleparser.grammar.expressions;

import com.ruegnerlukas.v2.simpleparser.Node;
import com.ruegnerlukas.v2.simpleparser.Token;
import com.ruegnerlukas.v2.simpleparser.grammar.State;
import com.ruegnerlukas.v2.simpleparser.grammar.trace.Trace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SequenceExpression extends Expression {


	public List<Expression> expressions = new ArrayList<>();




	/**
	 * X -> E0 E1 ... EN
	 * */
	public SequenceExpression(Expression... expressions) {
		super(ExpressionType.SEQUENCE);
		this.expressions.addAll(Arrays.asList(expressions));
	}




	@Override
	public boolean isOptional() {
		for(Expression e : expressions) {
			if(!e.isOptional()) {
				return false;
			}
		}
		return true;
	}




	@Override
	public State apply(Node root, List<Token> tokens, Trace trace) {

		Node node = new Node().setExpression(this);
		root.children.add(node);

		for(Expression e : expressions) {
			State state = e.apply(node, tokens, trace);

			if(state == State.MATCH) {
				continue;
			}
			if(state == State.NO_MATCH) {
				return State.NO_MATCH;
			}
			if(state == State.ERROR) {
				return State.ERROR;
			}

		}

		return State.MATCH;
	}




	@Override
	public String toString() {
		return "SEQUENCE:"+Integer.toHexString(this.hashCode());
	}


}
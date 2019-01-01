package com.ruegnerlukas.v2.simpleparser.grammar.expressions;

import com.ruegnerlukas.v2.simpleparser.ErrorType;
import com.ruegnerlukas.v2.simpleparser.Node;
import com.ruegnerlukas.v2.simpleparser.Token;
import com.ruegnerlukas.v2.simpleparser.grammar.State;
import com.ruegnerlukas.v2.simpleparser.grammar.trace.Trace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AlternativeExpression extends Expression {

	
	public List<Expression> expressions = new ArrayList<>();




	/**
	 * E0 | E1 | ... | En
	 * */
	public AlternativeExpression(Expression... expressions) {
		super(ExpressionType.ALTERNATIVE);
		this.expressions.addAll(Arrays.asList(expressions));
	}




	@Override
	public boolean isOptional() {
		for(Expression e : expressions) {
			if(e.isOptional()) {
				return true;
			}
		}
		return false;
	}




	@Override
	public State apply(Node root, List<Token> tokens, Trace trace) {

		Node[] nodes = new Node[expressions.size()];
		State[] states = new State[expressions.size()];
		int[] nConsumed = new int[expressions.size()];

		// apply all expressions
		for(int i=0; i<expressions.size(); i++) {
			Expression e = expressions.get(i);

			Node tmpParent = new Node().setExpression(this);
			List<Token> tmpTokens = new ArrayList<>(tokens);

			State state = e.apply(tmpParent, tmpTokens, trace);

			nodes[i] = tmpParent;
			states[i] = state;
			nConsumed[i] = tokens.size() - tmpTokens.size();

		}

		Node node = null;
		int n = -1;

		for(int i=0; i<expressions.size(); i++) {
			if(states[i] == State.MATCH) {
				if(nConsumed[i] > n) {
					n = nConsumed[i];
					node = nodes[i];
				}
			}
		}

		if(node != null) {
			for(int i=0; i<n; i++) {
				tokens.remove(0);
			}
			root.children.add(node);
			return State.MATCH;

		} else {
			errors.add(ErrorType.UNKNOWN_ERROR);
			return State.ERROR;
		}

	}




	@Override
	public String toString() {
		return "ALTERNATIVE:"+Integer.toHexString(this.hashCode());
	}


}

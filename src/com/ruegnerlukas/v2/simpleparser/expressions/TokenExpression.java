package com.ruegnerlukas.v2.simpleparser.expressions;

import com.ruegnerlukas.v2.simpleparser.Node;
import com.ruegnerlukas.v2.simpleparser.Token;
import com.ruegnerlukas.v2.simpleparser.grammar.State;
import com.ruegnerlukas.v2.simpleparser.trace.Trace;
import com.ruegnerlukas.v2.simpleparser.trace.TraceElement;

import java.util.List;

public class TokenExpression extends Expression {


	public Token token;




	public TokenExpression(Token token) {
		super(ExpressionType.TOKEN);
		this.token = token;
	}




	@Override
	public boolean isOptional() {
		return false;
	}




	@Override
	public State apply(Node root, List<Token> tokens, Trace trace) {


		if(tokens.isEmpty()) {
			trace.add(new TraceElement(this).setState(State.NO_MATCH));
			return State.NO_MATCH;


		} else {
			Token next = tokens.get(0);

			if(next.equals(token)) {
				tokens.remove(0);
				Node node = new Node().setExpression(this);
				root.children.add(node);
				trace.add(new TraceElement(this).setState(State.MATCH));
				return State.MATCH;

			} else {
				trace.add(new TraceElement(this).setState(State.NO_MATCH));
				return State.NO_MATCH;
			}

		}
	}




	@Override
	public String toString() {
		return "TOKEN:"+Integer.toHexString(this.hashCode())+ ": " + token.getSymbol();
	}

}
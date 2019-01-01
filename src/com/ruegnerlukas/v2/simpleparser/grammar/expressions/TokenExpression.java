package com.ruegnerlukas.v2.simpleparser.grammar.expressions;

import com.ruegnerlukas.v2.simpleparser.Node;
import com.ruegnerlukas.v2.simpleparser.Token;
import com.ruegnerlukas.v2.simpleparser.grammar.State;
import com.ruegnerlukas.v2.simpleparser.grammar.trace.Trace;

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
			return State.NO_MATCH;


		} else {
			Token next = tokens.get(0);

			if(next.equals(token)) {
				tokens.remove(0);
				Node node = new Node().setExpression(this);
				root.children.add(node);
				return State.MATCH;

			} else {
				return State.NO_MATCH;
			}

		}
	}




	@Override
	public String toString() {
		return "TOKEN:"+Integer.toHexString(this.hashCode())+ ": " + token.getSymbol();
	}

}
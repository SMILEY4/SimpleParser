package com.ruegnerlukas.v2.simpleparser.expressions;

import com.ruegnerlukas.v2.simpleparser.ErrorNode;
import com.ruegnerlukas.v2.simpleparser.Node;
import com.ruegnerlukas.v2.simpleparser.Token;
import com.ruegnerlukas.v2.simpleparser.TokenStream;
import com.ruegnerlukas.v2.simpleparser.errors.ErrorType;
import com.ruegnerlukas.v2.simpleparser.grammar.State;
import com.ruegnerlukas.v2.simpleparser.trace.Trace;
import com.ruegnerlukas.v2.simpleparser.trace.TraceElement;

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
	public State apply(Node root, TokenStream tokenStream, Trace trace) {

		if(!tokenStream.hasNext()) {
			trace.add(new TraceElement(this).setState(State.NO_MATCH));
			root.children.add(new ErrorNode(ErrorType.UNEXPECTED_END_OF_INPUT, tokenStream.getIndex()).setExpression(this));
			return State.NO_MATCH;

		} else {
			Token next = tokenStream.peek();

			if(next.equals(token)) {
				tokenStream.consume();
				Node node = new Node().setExpression(this);
				root.children.add(node);
				trace.add(new TraceElement(this).setState(State.MATCH));
				return State.MATCH;

			} else {
				trace.add(new TraceElement(this).setState(State.NO_MATCH));
				root.children.add(new ErrorNode(ErrorType.UNEXPECTED_SYMBOL, tokenStream.getIndex()).setExpression(this));
				return State.NO_MATCH;
			}

		}
	}




	@Override
	public String toString() {
		return "TOKEN:"+Integer.toHexString(this.hashCode())+ ": " + token.getSymbol();
	}

}
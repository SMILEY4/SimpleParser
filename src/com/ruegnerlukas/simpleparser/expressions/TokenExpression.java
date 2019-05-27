package com.ruegnerlukas.simpleparser.expressions;

import com.ruegnerlukas.simpleparser.*;
import com.ruegnerlukas.simpleparser.errors.ErrorType;
import com.ruegnerlukas.simpleparser.grammar.State;
import com.ruegnerlukas.simpleparser.trace.Trace;
import com.ruegnerlukas.simpleparser.trace.TraceElement;

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

		if (!tokenStream.hasNext()) {
			trace.add(new TraceElement(this).setState(State.NO_MATCH));
			root.children.add(new ErrorNode(ErrorType.UNEXPECTED_END_OF_INPUT, tokenStream.getIndex()).setExpression(this));
			return State.NO_MATCH;

		} else {
			Token next = tokenStream.peek();

			if (next.equals(token)) {
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
	public State apply(Node root, CharStream charStream, boolean ignoreWhitespace, Trace trace) {

		if (!charStream.hasNext()) {
			trace.add(new TraceElement(this).setState(State.NO_MATCH));
			root.children.add(new ErrorNode(ErrorType.UNEXPECTED_END_OF_INPUT, charStream.getIndex()).setExpression(this));
			return State.NO_MATCH;

		} else {

			if(ignoreWhitespace) {
				while(charStream.peek(token.getSymbol()) == null && charStream.peek() == ' ') {
					charStream.consume(1);
				}
			}

			Token next = charStream.peek(token.getSymbol());

			if (next != null) {
				charStream.consume(token.getSymbol());
				Node node = new Node().setExpression(this);
				root.children.add(node);
				trace.add(new TraceElement(this).setState(State.MATCH));
				return State.MATCH;

			} else {
				trace.add(new TraceElement(this).setState(State.NO_MATCH));
				root.children.add(new ErrorNode(ErrorType.UNEXPECTED_SYMBOL, charStream.getIndex()).setExpression(this));
				return State.NO_MATCH;
			}

		}
	}




	@Override
	public String toString() {
		return "TOKEN:" + Integer.toHexString(this.hashCode()) + ": '" + token.getSymbol() + "'";
	}

}
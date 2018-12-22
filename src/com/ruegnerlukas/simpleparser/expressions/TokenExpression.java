package com.ruegnerlukas.simpleparser.expressions;

import com.ruegnerlukas.simpleparser.errors.EndOfStreamError;
import com.ruegnerlukas.simpleparser.errors.UndefinedSymbolError;
import com.ruegnerlukas.simpleparser.errors.UnexpectedSymbolError;
import com.ruegnerlukas.simpleparser.tokens.Token;
import com.ruegnerlukas.simpleparser.tokens.TokenType;
import com.ruegnerlukas.simpleparser.tree.TerminalNode;
import com.ruegnerlukas.simpleparser.tree.TraceElement;

import java.util.List;
import java.util.Set;

public class TokenExpression extends Expression {


	public Token token;
	private Expression parent;




	public TokenExpression(Token token) {
		this.token = token;
	}




	@Override
	public Result apply(List<Token> consumed, List<Token> tokens, List<TraceElement> trace) {
		TraceElement traceElement = null;
		if(trace != null) {
			traceElement = new TraceElement(this, Result.State.MATCH);
			trace.add(traceElement);
		}

		if(tokens.isEmpty()) {
			if(traceElement != null) { traceElement.state = Result.State.NO_MATCH; }
			return new Result(Result.State.NO_MATCH, null, new EndOfStreamError(this, consumed.size()));

		} else {

			if(tokens.get(0).getType() == TokenType.UNDEFINED) {
				if(traceElement != null) { traceElement.state = Result.State.ERROR; }
				return new Result(new UndefinedSymbolError(this, consumed, tokens.get(0).getSymbol(), token.getSymbol()));

			} else if(tokens.get(0).getType() == TokenType.IGNORABLE) {
				consumed.add(tokens.remove(0));
				Result result =  this.apply(consumed, tokens, trace);
				if(traceElement != null) { traceElement.state = result.state; }
				return result;

			} else if(tokens.get(0) == token) {
				consumed.add(tokens.remove(0));
				return new Result(new TerminalNode(token));

			} else {
				if(traceElement != null) { traceElement.state = Result.State.ERROR; }
				return new Result(Result.State.NO_MATCH, null, new UnexpectedSymbolError(this, consumed, tokens.get(0).getSymbol(), token.getSymbol()));
			}

		}
	}




	@Override
	public boolean collectPossibleTokens(Set<Expression> visited, Set<Token> possibleTokens) {
		if(visited.contains(this)) {
			return false;
		} else {
			visited.add(this);
			possibleTokens.add(token);
			return false;
		}
	}




	@Override
	public String toString() {
		return "\"" + "TOKEN:"+Integer.toHexString(this.hashCode())+ ": " + token.getSymbol() + "\"";
	}




	@Override
	public void createDotGraph(Set<Expression> visited, StringBuilder builder) {
		if(visited.contains(this)) {
			return;
		}
		visited.add(this);
	}
	
}
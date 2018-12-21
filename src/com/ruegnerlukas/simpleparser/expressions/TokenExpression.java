package com.ruegnerlukas.simpleparser.expressions;

import com.ruegnerlukas.simpleparser.errors.ErrorMessages;
import com.ruegnerlukas.simpleparser.tokens.Token;
import com.ruegnerlukas.simpleparser.tokens.TokenType;
import com.ruegnerlukas.simpleparser.tree.TerminalNode;

import java.util.List;
import java.util.Set;

public class TokenExpression extends Expression {


	public Token token;
	private Expression parent;




	public TokenExpression(Token token) {
		this.token = token;
	}




	@Override
	public Result apply(List<Token> consumed, List<Token> tokens, List<Expression> trace) {

		if(trace != null) {
			trace.add(this);
		}

		if(tokens.isEmpty()) {
			return new Result(Result.State.NO_MATCH, null, ErrorMessages.genMessage_endOfStream(this), consumed.size());

		} else {

			if(tokens.get(0).getType() == TokenType.UNDEFINED) {
				return new Result(Result.State.ERROR, null, ErrorMessages.genMessage_undefinedSymbol(this, token.getSymbol(), consumed, tokens), consumed.size());

			} else if(tokens.get(0).getType() == TokenType.IGNORABLE) {
				consumed.add(tokens.remove(0));
				return this.apply(consumed, tokens, trace);

			} else if(tokens.get(0) == token) {
				consumed.add(tokens.remove(0));
				return new Result(new TerminalNode(token));

			} else {
				return new Result(Result.State.NO_MATCH, null, ErrorMessages.genMessage_unexpectedSymbol(this, token.getSymbol(), consumed, tokens), consumed.size());
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
package com.ruegnerlukas.simpleparser.grammar.expressions;

import com.ruegnerlukas.simpleparser.error.ErrorMessages;
import com.ruegnerlukas.simpleparser.grammar.Token;
import com.ruegnerlukas.simpleparser.tree.TerminalNode;

import java.util.List;
import java.util.Set;

public class TokenExpression extends Expression {

	public Token token;
	
	public TokenExpression(Token token) {
		this.token = token;
	}


	@Override
	public Result apply(List<Token> consumed, List<Token> tokens, List<Expression> trace) {
		trace.add(this);

		if(tokens.isEmpty()) {
			return new Result(Result.State.END_OF_STREAM, null);

		} else {

			if(tokens.get(0) == token) {
				consumed.add(tokens.remove(0));
				return new Result(Result.State.SUCCESS, new TerminalNode(token));

			} else {
				return new Result(Result.State.UNEXPECTED_SYMBOL, null, ErrorMessages.genMessage_unexpectedSymbol(token.symbol, consumed, tokens));
			}

		}
	}




	@Override
	public String toString() {
		return "\"" + "TOKEN:"+Integer.toHexString(this.hashCode())+ ": " + token.symbol + "\"";
	}




	@Override
	public void printAsDotGraph(Set<Expression> visited) {
		if(visited.contains(this)) {
			return;
		}
		visited.add(this);
	}
	
}

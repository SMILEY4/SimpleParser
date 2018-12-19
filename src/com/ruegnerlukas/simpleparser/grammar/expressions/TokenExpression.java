package com.ruegnerlukas.simpleparser.grammar.expressions;

import com.ruegnerlukas.simpleparser.ErrorMessages;
import com.ruegnerlukas.simpleparser.grammar.Token;
import com.ruegnerlukas.simpleparser.grammar.UndefinedToken;
import com.ruegnerlukas.simpleparser.tree.TerminalNode;

import java.util.List;
import java.util.Set;

public class TokenExpression extends Expression {

	public Token token;
	private Expression parent;


	protected TokenExpression(Token token) {
		this.token = token;
	}




	@Override
	public void setParent(Expression parent) {
		this.parent = parent;
	}


	@Override
	public Expression getParent() {
		return parent;
	}




	@Override
	public Result apply(List<Token> consumed, List<Token> tokens, List<Expression> trace) {
		if(trace != null) {
			trace.add(this);
		}

		if(tokens.isEmpty()) {
			return new Result(Result.State.NO_MATCH, null, ErrorMessages.genMessage_endOfStream(this));

		} else {

			if(tokens.get(0) instanceof UndefinedToken) {
				return new Result(Result.State.ERROR, null, ErrorMessages.genMessage_undefinedSymbol(this, token.symbol, consumed, tokens));

			} else if(tokens.get(0) == token) {
				consumed.add(tokens.remove(0));
				return new Result(Result.State.MATCH, new TerminalNode(token));

			} else {
				return new Result(Result.State.NO_MATCH, null, ErrorMessages.genMessage_unexpectedSymbol(this, token.symbol, consumed, tokens));
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
		return "\"" + "TOKEN:"+Integer.toHexString(this.hashCode())+ ": " + token.symbol + "\"";
	}




	@Override
	public void createDotGraph(Set<Expression> visited, StringBuilder builder) {
		if(visited.contains(this)) {
			return;
		}
		visited.add(this);
	}
	
}

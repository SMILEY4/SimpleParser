package com.ruegnerlukas.simpleparser.grammar.expressions;

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
	public Result apply(List<Token> tokens) {

		if(tokens.isEmpty()) {
			return new Result(Result.State.END_OF_STREAM, null);

		} else {

			if(tokens.get(0) == token) {
				tokens.remove(0);
				return new Result(Result.State.SUCCESS, new TerminalNode(token));

			} else {
				return new Result(Result.State.UNEXPECTED_SYMBOL, null,
						this + ": Unexpected symbol: '" + tokens.get(0).symbol + "'. Expected '" + token.symbol + "'.");
			}

		}

//		if(!tokens.isEmpty() && tokens.get(0) == token) {
//			TerminalNode node = new TerminalNode(token);
//			node.token = this.token;
//			tokens.remove(0);
//			return node;
//
//		} else {
//			return new EmptyNode();
//		}
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

package com.ruegnerlukas.simpleparser.expressions;

import com.ruegnerlukas.simpleparser.errors.EndOfStreamError;
import com.ruegnerlukas.simpleparser.errors.UndefinedSymbolError;
import com.ruegnerlukas.simpleparser.errors.UnexpectedSymbolError;
import com.ruegnerlukas.simpleparser.tokens.Token;
import com.ruegnerlukas.simpleparser.tokens.TokenType;
import com.ruegnerlukas.simpleparser.tree.PlaceholderNode;
import com.ruegnerlukas.simpleparser.tree.TerminalNode;
import com.ruegnerlukas.simpleparser.tree.TraceElement;

import java.util.List;
import java.util.Set;

public class TokenExpression extends Expression {


	public Token token;




	public TokenExpression(Token token) {
		super(ExpressionType.TOKEN);
		this.token = token;
	}



	public boolean isOptionalExpression() {
		return false;
	}



	public boolean collectPossibleTokens(Expression start, Set<Token> tokens) {
		return true;
	}




	public void collectPossibleTokens(Set<Token> tokens) {
		tokens.add(token);
	}




	@Override
	public Result apply(List<Token> consumed, List<Token> tokens, List<TraceElement> trace) {

		// handle trace
		TraceElement traceElement = null;
		if(trace != null) {
			traceElement = new TraceElement(this, Result.State.MATCH);
			trace.add(traceElement);
		}


		// no tokens remaining
		if(tokens.isEmpty()) {
			if(traceElement != null) { traceElement.state = Result.State.NO_MATCH; }

			return Result.noMatch(
					new PlaceholderNode().setExpression(this).setError(),
					new EndOfStreamError(this, consumed.size())
			);


		// tokens remaining
		} else {

			// get next
			Token next = tokens.get(0);


			// next is undefined -> ERROR: undefined symbol
			if(next.getType() == TokenType.UNDEFINED) {
				if(traceElement != null) { traceElement.state = Result.State.ERROR; }

				return Result.error(
						new PlaceholderNode().setExpression(this).setError(),
						new UndefinedSymbolError(this, consumed, tokens.get(0).getSymbol(), token.getSymbol())
				);


			// next is ignorable -> consume + apply again
			} else if(next.getType() == TokenType.IGNORABLE) {
				consumed.add(tokens.remove(0));
				Result result = this.apply(consumed, tokens, trace);
				if (traceElement != null) {
					traceElement.state = result.state;
				}
				return result;


			// next is matching token -> MATCH
			} else if(next == token) {
				consumed.add(tokens.remove(0));

				return Result.match(
						new TerminalNode(token).setExpression(this)
				);


			// next is not matching token -> NO_MATCH: unexpected symbol
			} else {
				if(traceElement != null) { traceElement.state = Result.State.ERROR; }

				return Result.noMatch(
						new PlaceholderNode().setExpression(this).setError(),
						new UnexpectedSymbolError(this, consumed, tokens.get(0).getSymbol(), token.getSymbol())
				);

			}


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
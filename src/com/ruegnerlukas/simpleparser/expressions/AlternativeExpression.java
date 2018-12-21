package com.ruegnerlukas.simpleparser.expressions;

import com.ruegnerlukas.simpleparser.errors.ErrorMessages;
import com.ruegnerlukas.simpleparser.tokens.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AlternativeExpression extends Expression {

	
	public List<Expression> expressions = new ArrayList<>();




	/**
	 * E0 | E1 | ... | En
	 * */
	public AlternativeExpression(Expression... expressions) {
		for(Expression expr : expressions) {
			this.expressions.add(expr);
		}
	}




	@Override
	public Result apply(List<Token> consumed, List<Token> tokens, List<Expression> trace) {
		if(trace != null) {
			trace.add(this);
		}

		for(Expression expr : expressions) {
			Result resultExpr = expr.apply(consumed, tokens, trace);

			if(resultExpr.state == Result.State.MATCH) {
				return new Result(resultExpr.node);
			}
			if(resultExpr.state == Result.State.NO_MATCH) {
				continue;
			}
			if(resultExpr.state == Result.State.ERROR) {
				return resultExpr;
			}

		}

		Expression.printPossible(this);

		if(tokens.isEmpty()) {
			return new Result(Result.State.ERROR, null, ErrorMessages.genMessage_endOfStream(this), consumed.size());
		} else {
			return new Result(Result.State.ERROR, null, ErrorMessages.genMessage_unexpectedSymbol(this, consumed, tokens), consumed.size());
		}
	}




	@Override
	public boolean collectPossibleTokens(Set<Expression> visited, Set<Token> possibleTokens) {

		if(visited.contains(this)) {
			return false;

		} else {
			visited.add(this);
			boolean isOptional = true;
			for(Expression expression : expressions) {
				boolean opt = expression.collectPossibleTokens(visited, possibleTokens);
				if(!opt) {
					isOptional = false;
				}
			}
			return isOptional;
		}

	}




	@Override
	public String toString() {
		return "\"" + "ALTERNATIVE:"+Integer.toHexString(this.hashCode())+"\"";
	}




	@Override
	public void createDotGraph(Set<Expression> visited, StringBuilder builder) {
		if(visited.contains(this)) {
			return;
		}
		visited.add(this);

		for(Expression e : expressions) {
			builder.append("    ").append(this).append(" -> ").append(e).append(';').append(System.lineSeparator());
		}
		for(Expression e : expressions) {
			e.createDotGraph(visited, builder);
		}
	}

}

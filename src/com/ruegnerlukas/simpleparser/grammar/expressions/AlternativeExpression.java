package com.ruegnerlukas.simpleparser.grammar.expressions;

import com.ruegnerlukas.simpleparser.ErrorMessages;
import com.ruegnerlukas.simpleparser.grammar.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * X -> E0 | E1 | ... | En
 * */
public class AlternativeExpression extends Expression {

	
	public List<Expression> expressions = new ArrayList<Expression>();
	private Expression parent;
	
	
	
	protected AlternativeExpression(Expression... expressions) {
		for(Expression expr : expressions) {
			this.expressions.add(expr);
			expr.setParent(this);
		}
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

		for(Expression expr : expressions) {
			Result resultExpr = expr.apply(consumed, tokens, trace);

			if(resultExpr.state == Result.State.MATCH) {
				return new Result(Result.State.MATCH, resultExpr.node);
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
			return new Result(Result.State.ERROR, null, ErrorMessages.genMessage_endOfStream(this));
		} else {
			return new Result(Result.State.ERROR, null, ErrorMessages.genMessage_unexpectedSymbol(this, consumed, tokens));
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

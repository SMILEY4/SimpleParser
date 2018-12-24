package com.ruegnerlukas.simpleparser.expressions;

import com.ruegnerlukas.simpleparser.errors.EndOfStreamError;
import com.ruegnerlukas.simpleparser.errors.UnexpectedSymbolError;
import com.ruegnerlukas.simpleparser.tokens.Token;
import com.ruegnerlukas.simpleparser.tree.PlaceholderNode;
import com.ruegnerlukas.simpleparser.tree.TraceElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class AlternativeExpression extends Expression {

	
	public List<Expression> expressions = new ArrayList<>();




	/**
	 * E0 | E1 | ... | En
	 * */
	public AlternativeExpression(Expression... expressions) {
		this.expressions.addAll(Arrays.asList(expressions));
	}




	public boolean isOptionalExpression() {
		for(Expression expression : expressions) {
			if(expression.isOptionalExpression()) {
				return true;
			}
		}
		return false;
	}


	public void collectPossibleTokens(Expression start, Set<Token> tokens) {
	}




	@Override
	public Result apply(List<Token> consumed, List<Token> tokens, List<TraceElement> trace) {
		TraceElement traceElement = null;
		if(trace != null) {
			traceElement = new TraceElement(this, Result.State.MATCH);
			trace.add(traceElement);
		}

		if(tokens.isEmpty()) {
			if(this.isOptionalExpression()) {
				return new Result(new PlaceholderNode().setExpression(this));
			} else {
				return new Result(new EndOfStreamError(this, consumed));
			}
		}

		Token tokenStart = tokens.get(0);
		List<Result> resultsMatched = new ArrayList<>();

		for(Expression expr : expressions) {

			Result resultExpr = expr.apply(consumed, tokens, trace);

			if(resultExpr.state == Result.State.MATCH) {
				resultsMatched.add(resultExpr);

				if(tokens.isEmpty() || tokenStart != tokens.get(0)) {
					return resultExpr;
				} else {
					continue;
				}

			} else if(resultExpr.state == Result.State.NO_MATCH) {
				continue;

			} else if(resultExpr.state == Result.State.ERROR) {
				return resultExpr;
			}
		}

		if(resultsMatched.size() > 0) {
			return resultsMatched.get(0);
		}

		if(traceElement != null) { traceElement.state = Result.State.ERROR; }
		if(tokens.isEmpty()) {
			return new Result(new EndOfStreamError(this, consumed));
		} else {
			return new Result(new UnexpectedSymbolError(this, consumed));
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

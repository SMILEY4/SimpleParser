package com.ruegnerlukas.simpleparser.expressions;

import com.ruegnerlukas.simpleparser.errors.EndOfStreamError;
import com.ruegnerlukas.simpleparser.errors.UnexpectedSymbolError;
import com.ruegnerlukas.simpleparser.tokens.Token;
import com.ruegnerlukas.simpleparser.tree.Node;
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
		super(ExpressionType.ALTERNATIVE);
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




	public boolean collectPossibleTokens(Expression start, Set<Token> tokens) {
		return true;
	}




	public void collectPossibleTokens(Set<Token> tokens) {
		for(Expression expression : expressions) {
			expression.collectPossibleTokens(tokens);
		}
	}



	@Override
	public Result apply(List<Token> consumed, List<Token> tokens, List<TraceElement> trace) {

		// handle trace
		TraceElement traceElement = null;
		if(trace != null) {
			traceElement = new TraceElement(this, Result.State.MATCH);
			trace.add(traceElement);
		}

		// no tokens remaining -> MATCH if optional or ERROR: end-of-stream
		if(tokens.isEmpty()) {
			if(this.isOptionalExpression()) {
				return Result.match(
						new PlaceholderNode().setExpression(this)
				);

			} else {
				return Result.error(
						new PlaceholderNode().setExpression(this).setError(),
						new EndOfStreamError(this, consumed)
				);
			}
		}

		// prepare
		Token tokenStart = tokens.get(0);
		List<Result> resultsMatched = new ArrayList<>();
		Result result = null;

		// apply each expression
		for(Expression expr : expressions) {
			result = expr.apply(consumed, tokens, trace);

			// expression matches next token(s) -> consumed token: return result -> did not consume: continue
			if(result.state == Result.State.MATCH) {
				resultsMatched.add(result);
				if(tokens.isEmpty() || tokenStart != tokens.get(0)) {
					return result;
				} else {
					continue;
				}

			// expression does not match token(s)
			} else if(result.state == Result.State.NO_MATCH) {
				continue;

			// expression encountered error -> return error/result
			} else if(result.state == Result.State.ERROR) {
				return result;
			}
		}

		// return if matched
		if(resultsMatched.size() > 0) {
			return resultsMatched.get(0);

		// nothing matched
		} else {
			if(traceElement != null) { traceElement.state = Result.State.ERROR; }

			// ... because no tokens remaining
			if(tokens.isEmpty()) {
				return Result.error(
						new PlaceholderNode(result == null ? new Node[]{} : new Node[]{result.node}).setExpression(this).setError(),
						new EndOfStreamError(this, consumed)
				);

			// ... because unexpected symbol
			} else {
				return Result.error(
						new PlaceholderNode().setExpression(this).setError(),
						new UnexpectedSymbolError(this, consumed)
				);
			}
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

package com.ruegnerlukas.simpleparser.expressions;

import com.ruegnerlukas.simpleparser.tokens.Token;
import com.ruegnerlukas.simpleparser.tree.Node;
import com.ruegnerlukas.simpleparser.tree.PlaceholderNode;
import com.ruegnerlukas.simpleparser.tree.TraceElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class SequenceExpression extends Expression {


	public List<Expression> expressions = new ArrayList<>();




	/**
	 * X -> E0 E1 ... EN
	 * */
	public SequenceExpression(Expression... expressions) {
		super(ExpressionType.SEQUENCE);
		this.expressions.addAll(Arrays.asList(expressions));
	}


	public boolean isOptionalExpression() {
		for(Expression expression : expressions) {
			if(!expression.isOptionalExpression()) {
				return false;
			}
		}
		return true;
	}




	public boolean collectPossibleTokens(Expression start, Set<Token> tokens) {
		int index = start == null ? -1 : expressions.indexOf(start);

		SequenceExpression sequenceExpression = new SequenceExpression();
		boolean allOptional = true;

		for(int i=index+1; i<expressions.size(); i++) {
			Expression expression = expressions.get(i);
			sequenceExpression.expressions.add(expression);
			if(!expression.isOptionalExpression()) {
				allOptional = false;
			}
		}

		sequenceExpression.collectPossibleTokens(tokens);

		return allOptional;
	}



	public void collectPossibleTokens(Set<Token> tokens) {
		for(Expression expression : expressions) {
			expression.collectPossibleTokens(tokens);
			if(!expression.isOptionalExpression()) {
				break;
			}
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

		// create node
		Node node = new PlaceholderNode().setExpression(this);

		// apply each expression
		for (Expression expr : expressions) {
			Result result = expr.apply(consumed, tokens, trace);

			// matching -> add node
			if (result.state == Result.State.MATCH) {
				node.addChild(result.node);
			}

			// does not match -> return nomatch and matched nodes
			if (result.state == Result.State.NO_MATCH) {
				node.addChild(result.node);
				if(traceElement != null) { traceElement.state = Result.State.NO_MATCH; }
				return Result.noMatch(node.setError());
			}

			// encountered error
			if (result.state == Result.State.ERROR) {
				node.addChild(result.node);
				if(traceElement != null) { traceElement.state = Result.State.ERROR; }
				return Result.error(node.setError(), result.error);
			}

		}

		return Result.match(node);
	}



	@Override
	public String toString() {
		return "\"" + "SEQUENCE:"+Integer.toHexString(this.hashCode())+"\"";
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
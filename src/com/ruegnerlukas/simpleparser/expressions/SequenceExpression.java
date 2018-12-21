package com.ruegnerlukas.simpleparser.expressions;

import com.ruegnerlukas.simpleparser.tokens.Token;
import com.ruegnerlukas.simpleparser.tree.Node;
import com.ruegnerlukas.simpleparser.tree.PlaceholderNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SequenceExpression extends Expression {


	public List<Expression> expressions = new ArrayList<>();




	/**
	 * X -> E0 E1 ... EN
	 * */
	public SequenceExpression(Expression... expressions) {
		for(Expression expr : expressions) {
			this.expressions.add(expr);
		}
	}




	@Override
	public Result apply(List<Token> consumed, List<Token> tokens, List<Expression> trace) {
		if(trace != null) {
			trace.add(this);
		}

		Node node = new PlaceholderNode(Integer.toHexString(this.hashCode()));

		for (Expression expr : expressions) {

			Result resultExpr = expr.apply(consumed, tokens, trace);

			if (resultExpr.state == Result.State.MATCH) {
				node.children.add(resultExpr.node);
			}
			if (resultExpr.state == Result.State.NO_MATCH) {
				if(tokens.isEmpty()) {
					SequenceExpression sequenceExpression = new SequenceExpression();
					for(int i=expressions.indexOf(expr); i<expressions.size(); i++) {
						sequenceExpression.expressions.add(expressions.get(i));
					}
					Expression.printPossible(sequenceExpression, this);
				}
				return resultExpr;
			}
			if (resultExpr.state == Result.State.ERROR) {
				return resultExpr;
			}

		}

		return new Result(node);
	}




	@Override
	public boolean collectPossibleTokens(Set<Expression> visited, Set<Token> possibleTokens) {
		if(visited.contains(this)) {
			return false;
		} else {
			visited.add(this);
			for(Expression expression : expressions) {
				boolean opt = expression.collectPossibleTokens(visited, possibleTokens);
				if(!opt) {
					return false;
				}
			}
			return true;
		}
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
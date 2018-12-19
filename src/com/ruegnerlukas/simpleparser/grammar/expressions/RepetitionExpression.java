package com.ruegnerlukas.simpleparser.grammar.expressions;

import com.ruegnerlukas.simpleparser.grammar.Token;
import com.ruegnerlukas.simpleparser.tree.PlaceholderNode;
import com.ruegnerlukas.simpleparser.tree.Node;

import java.util.List;
import java.util.Set;

/**
 * X -> E E*
 *  => one or more
 * */
public class RepetitionExpression extends Expression {

	
	public Expression expression = null;
	private Expression parent;



	protected RepetitionExpression(Expression expression) {
		this.expression = expression;
		expression.setParent(this);
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

		Node node = new PlaceholderNode(Integer.toHexString(this.hashCode()));

		while (!tokens.isEmpty()) {
			Result resultExpr = expression.apply(consumed, tokens, trace);

			if (resultExpr.state == Result.State.MATCH) {
				node.children.add(resultExpr.node);
			}
			if (resultExpr.state == Result.State.NO_MATCH) {
				return new Result(Result.State.MATCH, node);
			}
			if (resultExpr.state == Result.State.ERROR) {
				return resultExpr;
			}
		}

		Expression.printPossible(this);
		return new Result(Result.State.MATCH, node);
	}




	@Override
	public boolean collectPossibleTokens(Set<Expression> visited, Set<Token> possibleTokens) {
		if(visited.contains(this)) {
			return false;
		} else {
			visited.add(this);
			expression.collectPossibleTokens(visited, possibleTokens);
			return true;
		}
	}




	@Override
	public String toString() {
		return "\"" + "REPETITION:"+Integer.toHexString(this.hashCode()) + "\"";
	}




	@Override
	public void createDotGraph(Set<Expression> visited, StringBuilder builder) {
		if(visited.contains(this)) {
			return;
		}
		visited.add(this);
		builder.append("    ").append(this).append(" -> ").append(expression).append(';').append(System.lineSeparator());
		expression.createDotGraph(visited, builder);
	}

	
}

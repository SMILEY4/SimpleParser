package com.ruegnerlukas.simpleparser.grammar.expressions;

import com.ruegnerlukas.simpleparser.ErrorMessages;
import com.ruegnerlukas.simpleparser.grammar.Token;
import com.ruegnerlukas.simpleparser.tree.PlaceholderNode;

import java.util.List;
import java.util.Set;

/**
 * X -> [E]
 *  => none or one E
 * */
public class OptionalExpression extends Expression {

	
	public Expression expression = null;
	private Expression parent;



	protected OptionalExpression(Expression expression) {
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

		if(tokens.isEmpty()) {
			return new Result(Result.State.MATCH, new PlaceholderNode());

		} else {

			Result resultExpr = expression.apply(consumed, tokens, trace);

			if(resultExpr.state == Result.State.MATCH) {
				return new Result(Result.State.MATCH, resultExpr.node);
			}
			if(resultExpr.state == Result.State.NO_MATCH) {
				return new Result(Result.State.MATCH, resultExpr.node == null ? new PlaceholderNode() : resultExpr.node);
			}
			if(resultExpr.state == Result.State.ERROR) {
				return resultExpr;
			}

			return new Result(Result.State.ERROR, null, ErrorMessages.genMessage_undefinedState(this));
		}
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
		return "\"" + "OPTIONAL:"+Integer.toHexString(this.hashCode()) + "\"";
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

package com.ruegnerlukas.simpleparser.expressions;

import com.ruegnerlukas.simpleparser.tokens.Token;
import com.ruegnerlukas.simpleparser.tree.TraceElement;

import java.util.List;
import java.util.Set;

public abstract class Expression {


	private final ExpressionType type;

	Expression(ExpressionType type) {
		this.type = type;
	}



	public ExpressionType getType() {
		return this.type;
	}




	public abstract boolean collectPossibleTokens(Expression start, Set<Token> tokens);


	public abstract void collectPossibleTokens(Set<Token> tokens);



	public abstract boolean isOptionalExpression();


	/**
	 * applies this Expression to the given list of tokens.
	 * @param consumed the list of consumed tokens
	 * @param tokens the list of remaining tokens
	 * @param trace the list of applied expressions
	 * @return the result of the operation
	 * */
	public abstract Result apply(List<Token> consumed, List<Token> tokens, List<TraceElement> trace);




	/**
	 * builds this expression (and its children) in the DOT-format and appends it to the given StringBuilder.
	 * @param visited the expressions that have already been visited
	 * */
	public abstract void createDotGraph(Set<Expression> visited, StringBuilder builder);


}

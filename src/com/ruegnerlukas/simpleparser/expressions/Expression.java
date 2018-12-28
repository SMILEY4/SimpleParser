package com.ruegnerlukas.simpleparser.expressions;

import com.ruegnerlukas.simpleparser.tokens.Token;

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


}

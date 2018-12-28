package com.ruegnerlukas.simpleparser.expressions;

import com.ruegnerlukas.simpleparser.tokens.Token;

import java.util.Set;

public class RepetitionExpression extends Expression {

	
	public Expression expression;




	/**
	 * X -> E E*
	 *  => one or more
	 * */
	public RepetitionExpression(Expression expression) {
		super(ExpressionType.REPETITION);
		this.expression = expression;
	}



	public boolean collectPossibleTokens(Expression start, Set<Token> tokens) {
		if(start != null) {
			collectPossibleTokens(tokens);
		}
		return true;
	}




	public void collectPossibleTokens(Set<Token> tokens) {
		expression.collectPossibleTokens(tokens);
	}


	



	@Override
	public String toString() {
		return "REPETITION:"+Integer.toHexString(this.hashCode());
	}


}

package com.ruegnerlukas.simpleparser.expressions;

import com.ruegnerlukas.simpleparser.tokens.Token;

import java.util.Set;

public class OptionalExpression extends Expression {

	
	public Expression expression;




	/**
	 * X -> [E]
	 *  => none or one E
	 * */
	public OptionalExpression(Expression expression) {
		super(ExpressionType.OPTIONAL);
		this.expression = expression;
	}




	public boolean collectPossibleTokens(Expression start, Set<Token> tokens) {
		return true;
	}




	public void collectPossibleTokens(Set<Token> tokens) {
		expression.collectPossibleTokens(tokens);
	}





	@Override
	public String toString() {
		return "OPTIONAL:"+Integer.toHexString(this.hashCode());
	}


}

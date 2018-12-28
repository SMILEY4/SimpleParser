package com.ruegnerlukas.simpleparser.expressions;

import com.ruegnerlukas.simpleparser.tokens.Token;

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






	public boolean collectPossibleTokens(Expression start, Set<Token> tokens) {
		return true;
	}




	public void collectPossibleTokens(Set<Token> tokens) {
		for(Expression expression : expressions) {
			expression.collectPossibleTokens(tokens);
		}
	}





	@Override
	public String toString() {
		return "ALTERNATIVE:"+Integer.toHexString(this.hashCode());
	}


}

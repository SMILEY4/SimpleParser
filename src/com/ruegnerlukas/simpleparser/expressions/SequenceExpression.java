package com.ruegnerlukas.simpleparser.expressions;

import com.ruegnerlukas.simpleparser.systems.Optional;
import com.ruegnerlukas.simpleparser.tokens.Token;

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




	public boolean collectPossibleTokens(Expression start, Set<Token> tokens) {
		int index = start == null ? -1 : expressions.indexOf(start);

		SequenceExpression sequenceExpression = new SequenceExpression();
		boolean allOptional = true;

		for(int i=index+1; i<expressions.size(); i++) {
			Expression expression = expressions.get(i);
			sequenceExpression.expressions.add(expression);
			if(!Optional.isOptional(expression)) {
				allOptional = false;
			}
		}

		sequenceExpression.collectPossibleTokens(tokens);

		return allOptional;
	}



	public void collectPossibleTokens(Set<Token> tokens) {
		for(Expression expression : expressions) {
			expression.collectPossibleTokens(tokens);
			if(!Optional.isOptional(expression)) {
				break;
			}
		}
	}






	@Override
	public String toString() {
		return "SEQUENCE:"+Integer.toHexString(this.hashCode());
	}


}
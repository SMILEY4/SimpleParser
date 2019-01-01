package com.ruegnerlukas.v1.simpleparser.systems;

import com.ruegnerlukas.v1.simpleparser.expressions.*;

public class OptionalCheck {


	public static boolean isOptional(Expression expression) {

		if(ExpressionType.ALTERNATIVE == expression.getType()) {
			AlternativeExpression alternativeExpression = (AlternativeExpression)expression;
			for(Expression e : alternativeExpression.expressions) {
				if(isOptional(e)) {
					return true;
				}
			}
			return false;
		}

		if(ExpressionType.OPTIONAL == expression.getType()) {
			return true;
		}

		if(ExpressionType.REPETITION == expression.getType()) {
			return true;
		}

		if(ExpressionType.RULE == expression.getType()) {
			RuleExpression ruleExpression = (RuleExpression)expression;
			return isOptional(ruleExpression.rule.getExpression());
		}

		if(ExpressionType.SEQUENCE == expression.getType()) {
			SequenceExpression sequenceExpression = (SequenceExpression)expression;
			for(Expression e : sequenceExpression.expressions) {
				if(!isOptional(e)) {
					return false;
				}
			}
			return true;
		}

		if(ExpressionType.TOKEN == expression.getType()) {
			return false;
		}

		return false;
	}


}
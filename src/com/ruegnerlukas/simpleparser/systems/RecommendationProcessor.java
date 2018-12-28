package com.ruegnerlukas.simpleparser.systems;

import com.ruegnerlukas.simpleparser.expressions.*;
import com.ruegnerlukas.simpleparser.tokens.Token;

import java.util.Set;

public class RecommendationProcessor {




	public static boolean collectPossibleTokens(Expression expression, Expression prev, Set<Token> tokens) {

		if(ExpressionType.ALTERNATIVE == expression.getType()) {
			return true;
		}

		if(ExpressionType.OPTIONAL == expression.getType()) {
			return true;
		}

		if(ExpressionType.REPETITION == expression.getType()) {
			if(prev != null) {
				collectPossibleTokens(expression, tokens);
			}
			return true;
		}

		if(ExpressionType.RULE == expression.getType()) {
			return true;
		}

		if(ExpressionType.SEQUENCE == expression.getType()) {
			SequenceExpression sequenceExpression = (SequenceExpression)expression;
			int index = prev == null ? -1 : sequenceExpression.expressions.indexOf(prev);
			SequenceExpression subsequence = new SequenceExpression();
			boolean allOptional = true;
			for(int i=index+1; i<sequenceExpression.expressions.size(); i++) {
				Expression e = sequenceExpression.expressions.get(i);
				subsequence.expressions.add(e);
				if(!Optional.isOptional(e)) {
					allOptional = false;
				}
			}
			collectPossibleTokens(subsequence, tokens);
			return allOptional;
		}

		if(ExpressionType.TOKEN == expression.getType()) {
			return true;
		}

		return false;
	}




	public static void collectPossibleTokens(Expression expression, Set<Token> tokens) {


		if(ExpressionType.ALTERNATIVE == expression.getType()) {
			AlternativeExpression alternativeExpression = (AlternativeExpression)expression;
			for(Expression e : alternativeExpression.expressions) {
				collectPossibleTokens(e, tokens);
			}
		}

		if(ExpressionType.OPTIONAL == expression.getType()) {
			OptionalExpression optionalExpression = (OptionalExpression)expression;
			collectPossibleTokens(optionalExpression.expression, tokens);
		}

		if(ExpressionType.REPETITION == expression.getType()) {
			RepetitionExpression repetitionExpression = (RepetitionExpression)expression;
			collectPossibleTokens(repetitionExpression.expression, tokens);
		}

		if(ExpressionType.RULE == expression.getType()) {
			RuleExpression ruleExpression = (RuleExpression)expression;
			collectPossibleTokens(ruleExpression.rule.getExpression(), tokens);
		}

		if(ExpressionType.SEQUENCE == expression.getType()) {
			SequenceExpression sequenceExpression = (SequenceExpression)expression;
			for(Expression e : sequenceExpression.expressions) {
				collectPossibleTokens(e, tokens);
				if(!Optional.isOptional(e)) {
					break;
				}
			}
		}

		if(ExpressionType.TOKEN == expression.getType()) {
			TokenExpression tokenExpression = (TokenExpression)expression;
			tokens.add(tokenExpression.token);
		}

	}


}
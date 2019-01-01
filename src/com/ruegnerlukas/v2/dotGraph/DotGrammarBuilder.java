package com.ruegnerlukas.v2.dotGraph;

import com.ruegnerlukas.v2.simpleparser.grammar.Grammar;
import com.ruegnerlukas.v2.simpleparser.grammar.expressions.*;

import java.util.HashSet;
import java.util.Set;

public class DotGrammarBuilder {


	public static String build(Grammar grammar) {

		StringBuilder builder = new StringBuilder();
		builder.append("digraph G {").append(System.lineSeparator());
		builder.append("    node [style=filled];").append(System.lineSeparator());

		Expression expression = grammar.getRule(grammar.getStartingRule()).getExpression();
		build(builder, expression, new HashSet<Expression>());

		builder.append("}");

		return builder.toString();
	}




	private static void build(StringBuilder builder, Expression expression, Set<Expression> visited) {

		if (visited.contains(expression)) {
			return;
		}
		visited.add(expression);

		if (ExpressionType.ALTERNATIVE == expression.getType()) {
			AlternativeExpression alternativeExpression = (AlternativeExpression) expression;
			for (Expression e : alternativeExpression.expressions) {
				appendConnection(builder, expression, e);
			}
			for (Expression e : alternativeExpression.expressions) {
				build(builder, e, visited);
			}
		}

		if (ExpressionType.OPTIONAL == expression.getType()) {
			OptionalExpression optionalExpression = (OptionalExpression) expression;
			appendConnection(builder, expression, optionalExpression.expression);
			build(builder, optionalExpression.expression, visited);
		}

		if (ExpressionType.REPETITION == expression.getType()) {
			RepetitionExpression repetitionExpression = (RepetitionExpression) expression;
			appendConnection(builder, expression, repetitionExpression.expression);
			build(builder, repetitionExpression.expression, visited);
		}

		if (ExpressionType.RULE == expression.getType()) {
			RuleExpression ruleExpression = (RuleExpression) expression;
			appendConnection(builder, expression, ruleExpression.rule.getExpression());
			appendStyle(builder, expression, 150, 150, 150);
			build(builder, ruleExpression.rule.getExpression(), visited);
		}

		if (ExpressionType.SEQUENCE == expression.getType()) {
			SequenceExpression sequenceExpression = (SequenceExpression) expression;
			for (Expression e : sequenceExpression.expressions) {
				appendConnection(builder, expression, e);
			}
			for (Expression e : sequenceExpression.expressions) {
				build(builder, e, visited);
			}
		}
	}




	private static void appendConnection(StringBuilder builder, Expression from, Expression to) {
		DotUtils.appendConnection(builder, quotes(from), quotes(to));
	}




	private static void appendStyle(StringBuilder builder, Expression expression, int red, int green, int blue) {
		DotUtils.appendStyle(builder, quotes(expression), red, green, blue);
	}




	private static String quotes(Expression expression) {
		return "\"" + expression.toString() + "\"";
	}


}
package com.ruegnerlukas.dotGraph;

import com.ruegnerlukas.simpleparser.expressions.*;
import com.ruegnerlukas.simpleparser.grammar.Grammar;
import com.ruegnerlukas.simpleparser.grammar.State;
import com.ruegnerlukas.simpleparser.trace.Trace;
import com.ruegnerlukas.simpleparser.trace.TraceElement;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DotGrammarBuilder {


	/**
	 * an edge with a state between the two expressions "from" and "to"
	 */
	static class Edge {

		Expression from, to;
		String label = "";
		State state = null;

		Edge(Expression from, Expression to) {
			this.from = from;
			this.to = to;
		}

	}




	/**
	 * a style/rgb-color of an expression
	 */
	static class Style {

		Expression expression;
		int r, g, b;

		Style(Expression expression, int r, int g, int b) {
			this.expression = expression;
			this.r = r;
			this.g = g;
			this.b = b;
		}

	}




	/**
	 * @return the graph of expressions of the given grammar as a string in the dot-language.
	 * If trace != null, the trace will be displayed in the graph
	 */
	public static String build(Grammar grammar, Trace trace) {

		// prepare
		List<Edge> edgeList = new ArrayList<>();
		List<Style> styleList = new ArrayList<>();

		// build, edge- and style-list
		Expression expression = grammar.getRule(grammar.getStartingRule()).getExpression();
		build(expression, new HashSet<Expression>(), edgeList, styleList);

		// add labels from trace
		if (trace != null) {

			for (int i = 1; i < trace.getElements().size(); i++) {
				TraceElement prev = trace.getElements().get(i - 1);
				TraceElement curr = trace.getElements().get(i);

				// is direct
				if (hasDirectConnection(prev.expression, curr.expression)) {

					Edge edge = getEdge(prev.expression, curr.expression, curr.state, edgeList);
					int edgeIndex = trace.getElements().indexOf(curr);
					if (edge.label.isEmpty()) {
						edge.label = Integer.toString(edgeIndex);
					} else {
						edge.label += ", " + edgeIndex;
					}


					// is jump
				} else {

					while (!hasDirectConnection(prev.expression, curr.expression)) {
						int indexPrev = trace.getElements().indexOf(prev) - 1;
						if (indexPrev < 0) {
							break;
						}
						prev = trace.getElements().get(indexPrev);
					}

					if (hasDirectConnection(prev.expression, curr.expression)) {
						Edge edge = getEdge(prev.expression, curr.expression, curr.state, edgeList);
						int edgeIndex = trace.getElements().indexOf(curr);
						if (edge.label.isEmpty()) {
							edge.label = Integer.toString(edgeIndex);
						} else {
							edge.label += ", " + edgeIndex;
						}
					}

				}

			}


		}

		// create string from lists in .dot-format
		StringBuilder builder = new StringBuilder();
		builder.append("digraph G {").append(System.lineSeparator());
		builder.append("    node [style=filled];").append(System.lineSeparator());
		for (Edge edge : edgeList) {
			if (edge.label != null && !edge.label.isEmpty()) {
				if (edge.state == State.MATCH) {
					appendConnection(builder, edge.from, edge.to, "(" + edge.label + ")", 0, 150, 50);
				}
				if (edge.state == State.NO_MATCH) {
					appendConnection(builder, edge.from, edge.to, "(" + edge.label + ")", 225, 200, 0);
				}
				if (edge.state == State.ERROR) {
					appendConnection(builder, edge.from, edge.to, "(" + edge.label + ")", 220, 0, 0);
				}
			} else {
				appendConnection(builder, edge.from, edge.to);
			}
		}
		for (Style style : styleList) {
			appendStyle(builder, style.expression, style.r, style.g, style.b);
		}
		builder.append("}");

		return builder.toString();
	}




	/**
	 * builds the graph starting from the given expression
	 */
	private static void build(Expression expression, Set<Expression> visited, List<Edge> edgeList, List<Style> styleList) {

		if (visited.contains(expression)) {
			return;
		}
		visited.add(expression);

		if (ExpressionType.ALTERNATIVE == expression.getType()) {
			AlternativeExpression alternativeExpression = (AlternativeExpression) expression;
			for (Expression e : alternativeExpression.expressions) {
				edgeList.add(new Edge(expression, e));
			}
			for (Expression e : alternativeExpression.expressions) {
				build(e, visited, edgeList, styleList);
			}
		}

		if (ExpressionType.OPTIONAL == expression.getType()) {
			OptionalExpression optionalExpression = (OptionalExpression) expression;
			edgeList.add(new Edge(expression, optionalExpression.expression));
			build(optionalExpression.expression, visited, edgeList, styleList);
		}

		if (ExpressionType.REPETITION == expression.getType()) {
			RepetitionExpression repetitionExpression = (RepetitionExpression) expression;
			edgeList.add(new Edge(expression, repetitionExpression.expression));
			build(repetitionExpression.expression, visited, edgeList, styleList);
		}

		if (ExpressionType.RULE == expression.getType()) {
			RuleExpression ruleExpression = (RuleExpression) expression;
			edgeList.add(new Edge(expression, ruleExpression.rule.getExpression()));
			styleList.add(new Style(expression, 150, 150, 150));
			build(ruleExpression.rule.getExpression(), visited, edgeList, styleList);
		}

		if (ExpressionType.SEQUENCE == expression.getType()) {
			SequenceExpression sequenceExpression = (SequenceExpression) expression;
			for (Expression e : sequenceExpression.expressions) {
				edgeList.add(new Edge(expression, e));
			}
			for (Expression e : sequenceExpression.expressions) {
				build(e, visited, edgeList, styleList);
			}
		}
	}




	/**
	 * finds the edge (from,to) with the given state in the given list or creates a new edge if none was found.
	 */
	private static Edge getEdge(Expression from, Expression to, State state, List<Edge> edgeList) {
		for (Edge edge : findEdges(from, to, edgeList)) {
			if (edge.state == null) {
				edge.state = state;
				return edge;

			} else if (edge.state == state) {
				return edge;
			}
		}
		Edge edge = new Edge(from, to);
		edge.state = state;
		edgeList.add(edge);
		return edge;
	}




	/**
	 * finds all edges (from,to) in the given list ignoring their state
	 */
	private static List<Edge> findEdges(Expression from, Expression to, List<Edge> edgeList) {
		List<Edge> edges = new ArrayList<>();
		for (Edge edge : edgeList) {
			if (edge.from.equals(from) && edge.to.equals(to)) {
				edges.add(edge);
			}
		}
		return edges;
	}




	/**
	 * @return true, if there is a direct connection from the expression "from" to the expression "to"
	 */
	private static boolean hasDirectConnection(Expression from, Expression to) {

		if (ExpressionType.ALTERNATIVE == from.getType()) {
			AlternativeExpression alternativeExpression = (AlternativeExpression) from;
			return alternativeExpression.expressions.contains(to);
		}

		if (ExpressionType.OPTIONAL == from.getType()) {
			OptionalExpression optionalExpression = (OptionalExpression) from;
			return optionalExpression.expression.equals(to);
		}

		if (ExpressionType.REPETITION == from.getType()) {
			RepetitionExpression repetitionExpression = (RepetitionExpression) from;
			return repetitionExpression.expression.equals(to);
		}

		if (ExpressionType.RULE == from.getType()) {
			RuleExpression ruleExpression = (RuleExpression) from;
			return ruleExpression.rule.getExpression().equals(to);
		}

		if (ExpressionType.SEQUENCE == from.getType()) {
			SequenceExpression sequenceExpression = (SequenceExpression) from;
			return sequenceExpression.expressions.contains(to);
		}

		return false;
	}




	/**
	 * appends the connection between the given expressions to the given StringBuilder
	 */
	private static void appendConnection(StringBuilder builder, Expression from, Expression to) {
		DotUtils.appendConnection(builder, quotes(from), quotes(to));
	}




	/**
	 * appends the connection with the given rgb-color between the given expressions to the given StringBuilder
	 */
	private static void appendConnection(StringBuilder builder, Expression from, Expression to, String label, int r, int g, int b) {
		DotUtils.appendConnection(builder, quotes(from), quotes(to), label, r, g, b);
	}




	/**
	 * appends the style/rgb-color of the given expression to the given StringBuilder
	 */
	private static void appendStyle(StringBuilder builder, Expression expression, int red, int green, int blue) {
		DotUtils.appendStyle(builder, quotes(expression), red, green, blue);
	}




	/**
	 * @return the given expression as a quoted string
	 */
	private static String quotes(Expression expression) {
		return "\"" + expression.toString() + "\"";
	}


}
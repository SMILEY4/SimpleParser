package com.ruegnerlukas.simpleparser.systems;

import com.ruegnerlukas.simpleparser.expressions.*;
import com.ruegnerlukas.simpleparser.grammar.Grammar;
import com.ruegnerlukas.simpleparser.tree.Node;

import java.awt.*;
import java.util.List;
import java.util.*;

public class DotGraphBuilder {

	private static final String INDENT = "    ";

	private static final HashMap<String, ArrayList<String>> lines = new HashMap<>();
	private static final HashMap<String, HashMap<String, Set<Point>>> labels = new HashMap<>();
	private static final List<String> styles = new ArrayList<>();

	private static final int[][] COLORS = new int[][]{
			{215, 0, 0},	// ERROR
			{255, 150, 0},	// NO MATCH
			{0, 155, 40}	// MATCH
	};



	public static String build(Grammar grammar) {
		return build(grammar, null);
	}




	public static String build(Grammar grammar, List<TraceElement> trace) {
		StringBuilder builder = new StringBuilder();

		Expression expression = grammar.getRule(grammar.getStartingRule()).getExpression();

		labels.clear();
		lines.clear();
		styles.clear();

		builder.append("digraph G {").append(System.lineSeparator());
		builder.append(INDENT + "node [style=filled];").append(System.lineSeparator());
		appendConnection("X", quotes(expression), null);
		appendStyle("X", 150, 150, 200);

		Set<Expression> visited = new HashSet<>();
		build(expression, visited, trace);

		for(String from : lines.keySet()) {
			for(String to : lines.get(from)) {

				if(labels.containsKey(from) && labels.get(from).containsKey(to)) {

					Set<Point> pointSet = new HashSet<>();
					Point minPoint = new Point(Integer.MIN_VALUE, -1);
					int minDist = Integer.MAX_VALUE;

					for(Point p : labels.get(from).get(to)) {
						int dist = p.y - p.x;
						if(dist == 1) {
							pointSet.add(p);
						}
						if(dist < minDist) {
							minDist = dist;
							minPoint = p;
						}
					}

					pointSet.add(minPoint);

					for(Point p : pointSet) {
						TraceElement e1 = trace.get(p.y);

						int[] color = e1.state == Result.State.ERROR ? COLORS[0] : e1.state == Result.State.NO_MATCH ? COLORS[1] : COLORS[2];

						builder.append(INDENT);
						builder	.append(from).append(" -> ").append(to)
								.append(" [")
								.append(" label=\"").append(p.y).append("\"")
								.append(" color=\"").append(color(color[0], color[1], color[2])).append("\"")
								.append("];");
						builder.append(System.lineSeparator());
					}

				} else {
					builder.append(INDENT);
					builder.append(from).append(" -> ").append(to).append(";");
					builder.append(System.lineSeparator());
				}

			}
		}

		for(String style : styles) {
			builder.append(style).append(System.lineSeparator());
		}

		builder.append('}').append(System.lineSeparator());
		return builder.toString();
	}




	private static void build(Expression expression, Set<Expression> visited, List<TraceElement> trace) {

		if(visited.contains(expression)) {
			return;
		}
		visited.add(expression);


		if(ExpressionType.ALTERNATIVE == expression.getType()) {
			AlternativeExpression alternativeExpression = (AlternativeExpression)expression;
			for(Expression e : alternativeExpression.expressions) {
				appendConnection(expression, e, trace);
			}
			for(Expression e : alternativeExpression.expressions) {
				build(e, visited, trace);
			}
		}

		if(ExpressionType.OPTIONAL == expression.getType()) {
			OptionalExpression optionalExpression = (OptionalExpression)expression;
			appendConnection(expression, optionalExpression.expression, trace);
			build(optionalExpression.expression, visited, trace);
		}

		if(ExpressionType.REPETITION == expression.getType()) {
			RepetitionExpression repetitionExpression = (RepetitionExpression)expression;
			appendConnection(expression, repetitionExpression.expression, trace);
			build(repetitionExpression.expression, visited, trace);
		}

		if(ExpressionType.RULE == expression.getType()) {
			RuleExpression ruleExpression = (RuleExpression)expression;
			appendConnection(expression, ruleExpression.rule.getExpression(), trace);
			appendStyle(expression, 150, 150, 150);
			build(ruleExpression.rule.getExpression(), visited, trace);
		}

		if(ExpressionType.SEQUENCE == expression.getType()) {
			SequenceExpression sequenceExpression = (SequenceExpression)expression;
			for(Expression e : sequenceExpression.expressions) {
				appendConnection(expression, e, trace);
			}
			for(Expression e : sequenceExpression.expressions) {
				build(e, visited, trace);
			}
		}

		if(ExpressionType.TOKEN == expression.getType()) {
			TokenExpression tokenExpression = (TokenExpression)expression;
		}

	}




	public static String build(Node node) {

		StringBuilder builder = new StringBuilder();

		builder.append("digraph G {").append(System.lineSeparator());
		builder.append(INDENT + "node [style=filled];").append(System.lineSeparator());
		build(node, builder);
		builder.append('}').append(System.lineSeparator());

		return builder.toString();
	}




	private static void build(Node node, StringBuilder builder) {

		for(Node child : node.getChildren()) {
			builder	.append(INDENT)
					.append(quotes(node))
					.append(" -> ")
					.append(quotes(child))
					.append(';')
					.append(System.lineSeparator());
			build(child, builder);
		}

		if(node.state == Result.State.ERROR) {
			builder	.append(INDENT)
					.append(quotes(node))
					.append("[color=\"")
					.append(color(200, 0, 0))
					.append("\"];")
					.append(System.lineSeparator());

		}


	}





	private static void appendConnection(Expression from, Expression to, List<TraceElement> trace) {
		appendConnection(quotes(from), quotes(to), trace);
	}




	private static void appendConnection(String from, String to, List<TraceElement> trace) {

		if(!lines.containsKey(from)) {
			lines.put(from, new ArrayList<>());
		}
		lines.get(from).add(to);


		if(trace != null && !trace.isEmpty()) {
			String label = "";


			// get possible labels (label=j)
			for (int i = 0; i < trace.size(); i++) {
				TraceElement e0 = trace.get(i);

				if (from.equals(quotes(e0.expression))) {

					for (int j = i; j < trace.size(); j++) {
						TraceElement e1 = trace.get(j);

						if (to.equals(quotes(e1.expression))) {
							if (!labels.containsKey(from)) {
								labels.put(from, new HashMap<>());
							}
							if (!labels.get(from).containsKey(to)) {
								labels.get(from).put(to, new HashSet<>());
							}
							labels.get(from).get(to).add(new Point(i, j));
						}

					}

				}

			}
		}
	}




	private static void appendStyle(Expression expression, int red, int green, int blue) {
		appendStyle(quotes(expression), red, green, blue);
	}




	private static void appendStyle(String expression, int r, int g, int b) {
		final String strColor = "[color=\"" + color(r, g, b) + "\"];";
		StringBuilder builder = new StringBuilder();
		builder	.append("    ")
				.append(expression)
				.append(" ").append(strColor);

		styles.add(builder.toString());
	}




	private static String color(int r, int g, int b) {
		final float[] hsb = Color.RGBtoHSB(r, g, b, null);
		return hsb[0] + " " + hsb[1] + " " + hsb[2];
	}




	private static String quotes(Expression expression) {
		return "\"" + expression.toString() + "\"";
	}




	private static String quotes(Node node) {
		return "\"" + node.toString() + "\"";
	}


}
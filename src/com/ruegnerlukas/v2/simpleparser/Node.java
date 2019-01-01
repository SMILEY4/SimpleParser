package com.ruegnerlukas.v2.simpleparser;

import com.ruegnerlukas.v2.simpleparser.grammar.expressions.Expression;

import java.util.ArrayList;
import java.util.List;

public class Node {

	public List<Node> children = new ArrayList<>();
	public Expression expression;


	public Node setExpression(Expression expression) {
		this.expression = expression;
		return this;
	}

}

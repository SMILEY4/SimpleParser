package com.ruegnerlukas.simpleparser.grammar.expressions;

import com.ruegnerlukas.simpleparser.grammar.Token;
import com.ruegnerlukas.simpleparser.tree.Node;

import java.util.List;
import java.util.Set;

public abstract class Expression {
	public abstract Node apply(List<Token> tokens);
	public abstract void printAsDotGraph(Set<Expression> visited);
}

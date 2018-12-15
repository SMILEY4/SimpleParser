package com.ruegnerlukas.simpleparser.grammar.expressions;

import com.ruegnerlukas.simpleparser.grammar.Token;

import java.util.List;
import java.util.Set;

public abstract class Expression {
	public abstract Result apply(List<Token> tokens);
	public abstract void printAsDotGraph(Set<Expression> visited);
}

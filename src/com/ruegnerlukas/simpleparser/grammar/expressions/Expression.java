package com.ruegnerlukas.simpleparser.grammar.expressions;

import com.ruegnerlukas.simpleparser.grammar.Token;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Expression {
	public abstract Result apply(List<Token> consumed, List<Token> tokens, List<Expression> trace);

	public abstract void createDotGraph(Set<Expression> visited, StringBuilder builder);

	public abstract boolean collectPossibleTokens(Set<Expression> visited, Set<Token> possibleTokens);

	public abstract Expression getParent();
	public abstract void setParent(Expression parent);

	public static void printPossible(Expression e) {
		printPossible(e, e);
	}

	public static Set<Token> possible = new HashSet<>();

	public static void printPossible(Expression e, Expression owner) {
		Set<Expression> visited = new HashSet<>();
		Set<Token> tokens = new HashSet<>();

		if(e != owner) {
			visited.add(owner);
		}

		e.collectPossibleTokens(visited, tokens);

		possible.addAll(tokens);

//		System.out.print("POSSIBLE [" + owner + "]:  ");
//		for(Token t : tokens) {
//			System.out.print(t.symbol + "  ");
//		}
//		System.out.println();
	}

}

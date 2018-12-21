package com.ruegnerlukas.simpleparser.expressions;

import com.ruegnerlukas.simpleparser.tokens.Token;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Expression {


	/**
	 * applies this Expression to the given list of tokens.
	 * @param consumed the list of consumed tokens
	 * @param tokens the list of remaining tokens
	 * @param trace the list of applied expressions
	 * @return the result of the operation
	 * */
	public abstract Result apply(List<Token> consumed, List<Token> tokens, List<Expression> trace);




	/**
	 * builds this expression (and its children) in the DOT-format and appends it to the given StringBuilder.
	 * @param visited the expressions that have already been visited
	 * */
	public abstract void createDotGraph(Set<Expression> visited, StringBuilder builder);




	/**
	 * collects all possible Tokens of this expressions and adds them to the given list.
	 * @param visited the expressions that have already been visited
	 * */
	public abstract boolean collectPossibleTokens(Set<Expression> visited, Set<Token> possibleTokens);







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

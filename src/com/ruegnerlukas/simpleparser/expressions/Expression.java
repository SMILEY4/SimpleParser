package com.ruegnerlukas.simpleparser.expressions;

import com.ruegnerlukas.simpleparser.tokens.Token;
import com.ruegnerlukas.simpleparser.tree.TraceElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public abstract class Expression {


	public boolean isRoot = false;

	private List<Expression> parents = new ArrayList<>();




	public void addParent(Expression parent) {
		this.parents.add(parent);
	}


	public void addParents(Collection<Expression> parents) {
		this.parents.addAll(parents);
	}




	public List<Expression> getParents() {
		if(isRoot) {
			return new ArrayList<>();
		} else {
			return parents;
		}
	}




	/**
	 * applies this Expression to the given list of tokens.
	 * @param consumed the list of consumed tokens
	 * @param tokens the list of remaining tokens
	 * @param trace the list of applied expressions
	 * @return the result of the operation
	 * */
	public abstract Result apply(List<Token> consumed, List<Token> tokens, List<TraceElement> trace);




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


	/**
	 * collects all possible Tokens of this expressions and adds them to the given list.
	 * @param visited the expressions that have already been visited
	 * */
	public abstract boolean collectPossibleTokens(Expression start, Set<Expression> visited, Set<Token> possibleTokens);



}

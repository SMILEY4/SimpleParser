package com.ruegnerlukas.simpleparser.expressions;

import com.ruegnerlukas.simpleparser.CharStream;
import com.ruegnerlukas.simpleparser.Node;
import com.ruegnerlukas.simpleparser.TokenStream;
import com.ruegnerlukas.simpleparser.grammar.State;
import com.ruegnerlukas.simpleparser.trace.Trace;

public abstract class Expression {


	private final ExpressionType type;




	Expression(ExpressionType type) {
		this.type = type;
	}




	/**
	 * @return the type of this expression
	 */
	public ExpressionType getType() {
		return this.type;
	}




	/**
	 * @return true/false, if this expression is optional/required
	 */
	public abstract boolean isOptional();




	/**
	 * applies this expression to the given token-stream (and adds a new node to the given root of a subtree)
	 *
	 * @return the resulting state of this operation
	 */
	public abstract State apply(Node root, TokenStream tokenStream, Trace trace);




	/**
	 * applies this expression to the given character-stream (and adds a new node to the given root of a subtree)
	 *
	 * @return the resulting state of this operation
	 */
	public abstract State apply(Node root, CharStream charStream, Trace trace);


}

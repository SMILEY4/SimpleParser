package com.ruegnerlukas.v2.simpleparser.expressions;

import com.ruegnerlukas.v2.simpleparser.CharStream;
import com.ruegnerlukas.v2.simpleparser.Node;
import com.ruegnerlukas.v2.simpleparser.TokenStream;
import com.ruegnerlukas.v2.simpleparser.grammar.State;
import com.ruegnerlukas.v2.simpleparser.trace.Trace;

public abstract class Expression {


	private final ExpressionType type;




	Expression(ExpressionType type) {
		this.type = type;
	}




	public ExpressionType getType() {
		return this.type;
	}




	public abstract boolean isOptional();




	public abstract State apply(Node root, TokenStream tokenStream, Trace trace);


	public abstract State apply(Node root, CharStream charStream, Trace trace);


}

package com.ruegnerlukas.v2.simpleparser.grammar.expressions;

import com.ruegnerlukas.v2.simpleparser.ErrorType;
import com.ruegnerlukas.v2.simpleparser.Node;
import com.ruegnerlukas.v2.simpleparser.Token;
import com.ruegnerlukas.v2.simpleparser.grammar.State;
import com.ruegnerlukas.v2.simpleparser.grammar.trace.Trace;

import java.util.ArrayList;
import java.util.List;

public abstract class Expression {

	public static List<ErrorType> errors = new ArrayList<>();



	private final ExpressionType type;




	Expression(ExpressionType type) {
		this.type = type;
	}




	public ExpressionType getType() {
		return this.type;
	}




	public abstract boolean isOptional();




	public abstract State apply(Node root, List<Token> tokens, Trace trace);


}

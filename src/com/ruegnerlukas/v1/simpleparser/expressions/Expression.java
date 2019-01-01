package com.ruegnerlukas.v1.simpleparser.expressions;

public abstract class Expression {


	private final ExpressionType type;




	Expression(ExpressionType type) {
		this.type = type;
	}




	public ExpressionType getType() {
		return this.type;
	}



}

package com.ruegnerlukas.simpleparser.expressions;

public class RepetitionExpression extends Expression {

	
	public Expression expression;




	/**
	 * X -> E E*
	 *  => one or more
	 * */
	public RepetitionExpression(Expression expression) {
		super(ExpressionType.REPETITION);
		this.expression = expression;
	}



	@Override
	public String toString() {
		return "REPETITION:"+Integer.toHexString(this.hashCode());
	}


}

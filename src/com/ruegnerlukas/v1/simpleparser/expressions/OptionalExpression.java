package com.ruegnerlukas.v1.simpleparser.expressions;

public class OptionalExpression extends Expression {

	
	public Expression expression;




	/**
	 * X -> [E]
	 *  => none or one E
	 * */
	public OptionalExpression(Expression expression) {
		super(ExpressionType.OPTIONAL);
		this.expression = expression;
	}





	@Override
	public String toString() {
		return "OPTIONAL:"+Integer.toHexString(this.hashCode());
	}


}

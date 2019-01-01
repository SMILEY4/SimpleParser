package com.ruegnerlukas.v1.simpleparser.expressions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AlternativeExpression extends Expression {

	
	public List<Expression> expressions = new ArrayList<>();




	/**
	 * E0 | E1 | ... | En
	 * */
	public AlternativeExpression(Expression... expressions) {
		super(ExpressionType.ALTERNATIVE);
		this.expressions.addAll(Arrays.asList(expressions));
	}






	@Override
	public String toString() {
		return "ALTERNATIVE:"+Integer.toHexString(this.hashCode());
	}


}

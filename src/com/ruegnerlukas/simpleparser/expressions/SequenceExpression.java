package com.ruegnerlukas.simpleparser.expressions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SequenceExpression extends Expression {


	public List<Expression> expressions = new ArrayList<>();




	/**
	 * X -> E0 E1 ... EN
	 * */
	public SequenceExpression(Expression... expressions) {
		super(ExpressionType.SEQUENCE);
		this.expressions.addAll(Arrays.asList(expressions));
	}



	@Override
	public String toString() {
		return "SEQUENCE:"+Integer.toHexString(this.hashCode());
	}


}
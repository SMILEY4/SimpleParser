package com.ruegnerlukas.simpleparser.expressions;

import com.ruegnerlukas.simpleparser.grammar.Rule;

public class RuleExpression extends Expression {


	public final Rule rule;




	public RuleExpression(Rule rule) {
		super(ExpressionType.RULE);
		this.rule = rule;
	}





	@Override
	public String toString() {
		return "RULE:"+Integer.toHexString(this.hashCode())+ ": " + rule.getName();
	}



}

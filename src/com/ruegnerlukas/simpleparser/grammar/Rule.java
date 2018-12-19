package com.ruegnerlukas.simpleparser.grammar;

import com.ruegnerlukas.simpleparser.grammar.expressions.Expression;

public class Rule {

	
	public final String name;
	public Expression expression = null;

	public Expression tmpParent;
	
	
	protected Rule(String name) {
		this.name =  name;
	}
	
	
	
	
	
	
	public void define(Expression op) {
		this.expression = op;
		this.expression.setParent(tmpParent);
	}
	
	
	
	
	public boolean isDefined() {
		return expression != null;
	}
	
	
	
}

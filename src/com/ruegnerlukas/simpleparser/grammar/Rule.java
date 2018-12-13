package com.ruegnerlukas.simpleparser.grammar;

import com.ruegnerlukas.simpleparser.grammar.expressions.Expression;

public class Rule {

	
	public final String name;
	public Expression expression = null;
	

	
	
	protected Rule(String name) {
		this.name =  name;
	}
	
	
	
	
	
	
	public void define(Expression op) {
		this.expression = op;
	}
	
	
	
	
	public boolean isDefined() {
		return expression != null;
	}
	
	
	
}

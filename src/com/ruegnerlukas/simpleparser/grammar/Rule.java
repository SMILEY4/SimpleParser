package com.ruegnerlukas.simpleparser.grammar;


import com.ruegnerlukas.simpleparser.expressions.Expression;

public class Rule {


	private final String name;
	private Expression expression = null;




	/**
	 * @param name the name of this production rule (case-insensitive)
	 * */
	protected Rule(String name) {
		this.name =  name.toUpperCase();
	}
	
	
	

	/**
	 * define this rule with the given expression
	 * */
	public void define(Expression expression) {
		this.expression = expression;
	}
	
	
	

	/**
	 * @return true, if this rule is defined / has an expression
	 * */
	public boolean isDefined() {
		return expression != null;
	}




	/**
	 * @return the name of this rule
	 * */
	public String getName() {
		return this.name;
	}




	/**
	 * @return the expression of this rule
	 * */
	public Expression getExpression() {
		return expression;
	}

}
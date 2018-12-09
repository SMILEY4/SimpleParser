package com.ruegnerlukas.simpleparser.grammar;

import com.ruegnerlukas.simpleparser.grammar.ruleops.Op;

public class Rule {

	
	public final String name;
	public Op op = null;
	

	
	
	protected Rule(String name) {
		this.name =  name;
	}
	
	
	
	
	
	
	public void define(Op op) {
		this.op = op;
	}
	
	
	
	
	public boolean isDefined() {
		return op != null;
	}
	
	
	
}

package com.ruegnerlukas.v1.simpleparser.tree;

import com.ruegnerlukas.v1.simpleparser.grammar.Rule;

public class RuleNode extends Node {


	public Rule rule;




	public RuleNode(Rule rule) {
		this.rule = rule;
	}




	@Override
	public String toString() {
		return "Rule:"+Integer.toHexString(this.hashCode()) + ": " + (rule == null ? "null" : rule.getName());
	}
	
}
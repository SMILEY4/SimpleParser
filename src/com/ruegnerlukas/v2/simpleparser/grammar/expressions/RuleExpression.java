package com.ruegnerlukas.v2.simpleparser.grammar.expressions;


import com.ruegnerlukas.v2.simpleparser.Node;
import com.ruegnerlukas.v2.simpleparser.Token;
import com.ruegnerlukas.v2.simpleparser.grammar.Rule;
import com.ruegnerlukas.v2.simpleparser.grammar.State;
import com.ruegnerlukas.v2.simpleparser.grammar.trace.Trace;

import java.util.List;

public class RuleExpression extends Expression {


	public final Rule rule;




	public RuleExpression(Rule rule) {
		super(ExpressionType.RULE);
		this.rule = rule;
	}




	@Override
	public boolean isOptional() {
		return rule.getExpression().isOptional();
	}




	@Override
	public State apply(Node root, List<Token> tokens, Trace trace) {
		Node node = new Node().setExpression(this);
		State state = rule.getExpression().apply(node, tokens, trace);
		root.children.add(node);
		return state;
	}




	@Override
	public String toString() {
		return "RULE:"+Integer.toHexString(this.hashCode())+ ": " + rule.getName();
	}



}

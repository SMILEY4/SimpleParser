package com.ruegnerlukas.v2.simpleparser.expressions;


import com.ruegnerlukas.v2.simpleparser.Node;
import com.ruegnerlukas.v2.simpleparser.TokenStream;
import com.ruegnerlukas.v2.simpleparser.grammar.Rule;
import com.ruegnerlukas.v2.simpleparser.grammar.State;
import com.ruegnerlukas.v2.simpleparser.trace.Trace;
import com.ruegnerlukas.v2.simpleparser.trace.TraceElement;

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
	public State apply(Node root, TokenStream tokenStream, Trace trace) {

		TraceElement traceElement = new TraceElement(this);
		trace.add(traceElement);

		Node node = new Node().setExpression(this);
		State state = rule.getExpression().apply(node, tokenStream, trace);
		root.children.add(node);

		traceElement.setState(state);
		return state;
	}




	@Override
	public String toString() {
		return "RULE:"+Integer.toHexString(this.hashCode())+ ": " + rule.getName();
	}



}

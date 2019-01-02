package com.ruegnerlukas.v2.simpleparser.expressions;


import com.ruegnerlukas.v2.simpleparser.Node;
import com.ruegnerlukas.v2.simpleparser.Token;
import com.ruegnerlukas.v2.simpleparser.errors.ErrorStack;
import com.ruegnerlukas.v2.simpleparser.errors.GenericError;
import com.ruegnerlukas.v2.simpleparser.grammar.Rule;
import com.ruegnerlukas.v2.simpleparser.grammar.State;
import com.ruegnerlukas.v2.simpleparser.parser.Parser;
import com.ruegnerlukas.v2.simpleparser.trace.Trace;
import com.ruegnerlukas.v2.simpleparser.trace.TraceElement;

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
	public State apply(Node root, List<Token> tokens, Parser parser) {

		Trace trace = parser.getTrace();
		ErrorStack errorStack = parser.getErrorStack();

		TraceElement traceElement = new TraceElement(this);
		trace.add(traceElement);

		Node node = new Node().setExpression(this);
		State state = rule.getExpression().apply(node, tokens, parser);
		root.children.add(node);

		if(state != State.MATCH) {
			errorStack.addError(new GenericError(), this, state);
		}

		traceElement.setState(state);
		return state;
	}




	@Override
	public String toString() {
		return "RULE:"+Integer.toHexString(this.hashCode())+ ": " + rule.getName();
	}



}

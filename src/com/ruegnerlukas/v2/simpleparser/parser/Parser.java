package com.ruegnerlukas.v2.simpleparser.parser;

import com.ruegnerlukas.v2.simpleparser.Node;
import com.ruegnerlukas.v2.simpleparser.Token;
import com.ruegnerlukas.v2.simpleparser.grammar.Grammar;
import com.ruegnerlukas.v2.simpleparser.grammar.State;
import com.ruegnerlukas.v2.simpleparser.grammar.expressions.Expression;
import com.ruegnerlukas.v2.simpleparser.grammar.trace.Trace;

import java.util.List;

public class Parser {


	private Grammar grammar;

	private Node root;
	private State state;
	private Trace trace;



	public Parser(Grammar grammar) {
		this.grammar = grammar;
	}




	private void reset() {
		this.state = null;
		this.root = new Node();
		this.trace = new Trace();
	}




	public Parser parse(List<Token> tokens) {
		reset();
		Expression rootExpression = grammar.getRule(grammar.getStartingRule()).getExpression();
		this.state = rootExpression.apply(this.root, tokens, this.trace);
		return this;
	}




	public State getState() {
		return this.state;
	}




	public Node getRoot() {
		return this.root;
	}




	public Trace getTrace() {
		return this.trace;
	}


}

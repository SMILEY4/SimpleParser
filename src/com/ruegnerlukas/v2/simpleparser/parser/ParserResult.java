package com.ruegnerlukas.v2.simpleparser.parser;

import com.ruegnerlukas.v2.simpleparser.Node;
import com.ruegnerlukas.v2.simpleparser.Token;
import com.ruegnerlukas.v2.simpleparser.grammar.State;
import com.ruegnerlukas.v2.simpleparser.trace.Trace;

import java.util.Collections;
import java.util.List;

public abstract class ParserResult {

	private final State state;
	private final Node root;
	private final Trace trace;

	private List<Token> inputTokens = null;
	private String inputString = null;



	ParserResult(State state, List<Token> inputTokens, Node root, Trace trace) {
		this(state, root, trace);
		this.inputTokens = Collections.unmodifiableList(inputTokens);
	}


	ParserResult(State state, String inputString, Node root, Trace trace) {
		this(state, root, trace);
		this.inputString = inputString;
	}


	ParserResult(State state, Node root, Trace trace) {
		this.state = state;
		this.root = root;
		this.trace = trace;
	}




	public boolean inputWasTokenList() {
		return inputTokens != null;
	}




	public List<Token> getInputTokens() {
		return this.inputTokens;
	}




	public String getInputString() {
		return this.inputString;
	}




	public Node getRoot() {
		return this.root;
	}




	public boolean failed() {
		return state != State.MATCH;
	}




	public State getState() {
		return this.state;
	}




	public Trace getTrace() {
		return this.trace;
	}


}

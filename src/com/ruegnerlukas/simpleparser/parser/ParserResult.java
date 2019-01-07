package com.ruegnerlukas.simpleparser.parser;

import com.ruegnerlukas.simpleparser.trace.Trace;
import com.ruegnerlukas.simpleparser.Node;
import com.ruegnerlukas.simpleparser.Token;
import com.ruegnerlukas.simpleparser.grammar.State;

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




	/**
	 * @return true/false if the input was a token-list/string
	 */
	public boolean inputWasTokenList() {
		return inputTokens != null;
	}




	/**
	 * @return the list of tokens used as input or null
	 */
	public List<Token> getInputTokens() {
		return this.inputTokens;
	}




	/**
	 * @return the string used as input or null
	 */
	public String getInputString() {
		return this.inputString;
	}




	/**
	 * @return the root node of the resulting tree
	 */
	public Node getRoot() {
		return this.root;
	}




	/**
	 * @return true, if the result was not a match
	 */
	public boolean failed() {
		return state != State.MATCH;
	}




	/**
	 * @return the result state
	 */
	public State getState() {
		return this.state;
	}




	/**
	 * @return the trace of the parsing-process
	 */
	public Trace getTrace() {
		return this.trace;
	}


}

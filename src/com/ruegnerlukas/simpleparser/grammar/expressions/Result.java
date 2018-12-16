package com.ruegnerlukas.simpleparser.grammar.expressions;

import com.ruegnerlukas.simpleparser.tree.Node;

public class Result {

	public enum State {
		SUCCESS,
		ERROR,
		UNEXPECTED_SYMBOL,
		END_OF_STREAM,

	}


	public State state;
	public Node node;
	public String message;




	public Result(State state, Node node) {
		this(state, node, "");
	}


	public Result(State state, Node node, String message) {
		this.state = state;
		this.node = node;
		this.message = message;
	}


}

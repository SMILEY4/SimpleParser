package com.ruegnerlukas.simpleparser.grammar.expressions;

import com.ruegnerlukas.simpleparser.tree.Node;

public class Result {

	public enum State {
//		SUCCESS,
//		ERROR,
//		UNEXPECTED_SYMBOL,
//		END_OF_STREAM,

		MATCH,
		NO_MATCH,
		ERROR;

	}


	public State state;
	public Node node;
	public String message;
	public int errorTokenIndex;




	public Result(State state, Node node) {
		this(state, node, "", -1);
	}


	public Result(State state, Node node, String message, int errorIndex) {
		this.state = state;
		this.node = node;
		this.message = message;
		this.errorTokenIndex = errorIndex;
	}


}

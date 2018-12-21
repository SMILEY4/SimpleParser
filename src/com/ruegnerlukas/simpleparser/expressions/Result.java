package com.ruegnerlukas.simpleparser.expressions;

import com.ruegnerlukas.simpleparser.tree.Node;

public class Result {

	public enum State {
		MATCH,
		NO_MATCH,
		ERROR;
	}


	public State state;
	public Node node;
	public String message;
	public int errorTokenIndex;




	public Result(Node node) {
		this(State.MATCH, node, "", -1);
	}


	public Result(State state, Node node, String message, int errorIndex) {
		this.state = state;
		this.node = node;
		this.message = message;
		this.errorTokenIndex = errorIndex;
	}


}

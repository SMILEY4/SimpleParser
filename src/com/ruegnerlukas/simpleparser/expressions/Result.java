package com.ruegnerlukas.simpleparser.expressions;

import com.ruegnerlukas.simpleparser.errors.ErrorMessage;
import com.ruegnerlukas.simpleparser.tree.Node;

public class Result {


	public enum State {
		MATCH,
		NO_MATCH,
		ERROR
	}


	public final State state;
	public final Node node;
	public final ErrorMessage error;




	public Result(Node node) {
		this(State.MATCH, node, null);
	}


	public Result(ErrorMessage error) {
		this(State.ERROR, null, error);
	}


	public Result(State state, Node node, ErrorMessage error) {
		this.state = state;
		this.node = node;
		this.error = error;
	}


}
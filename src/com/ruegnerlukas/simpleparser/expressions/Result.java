package com.ruegnerlukas.simpleparser.expressions;

import com.ruegnerlukas.simpleparser.tree.Node;
import com.ruegnerlukas.simpleparser.errors.Error;

public class Result {


	public enum State {
		MATCH,
		NO_MATCH,
		ERROR
	}


	public final State state;
	public final Node node;
	public final Error error;





	public static Result match(Node node) {
		return new Result(State.MATCH, node, null);
	}




	public static Result noMatch(Node node) {
		return new Result(State.NO_MATCH, node, null);
	}


	public static Result noMatch(Node node, Error error) {
		return new Result(State.NO_MATCH, node, error);
	}




	public static Result error(Node node, Error error) {
		return new Result(State.ERROR, node, error);
	}




	private Result(State state, Node node, Error error) {
		this.state = state;
		this.node = node;
		this.error = error;
	}


}
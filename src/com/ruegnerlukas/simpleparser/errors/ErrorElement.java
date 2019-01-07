package com.ruegnerlukas.simpleparser.errors;

import com.ruegnerlukas.simpleparser.grammar.State;

public class ErrorElement {

	public Error error;
	public Object origin;
	public State state;


	public ErrorElement(Error error, Object origin, State state) {
		this.error = error;
		this.origin = origin;
		this.state = state;
	}

}

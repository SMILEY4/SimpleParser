package com.ruegnerlukas.v2.simpleparser;

import com.ruegnerlukas.v2.simpleparser.errors.ErrorType;

public class ErrorNode extends Node {

	public ErrorType error;

	public ErrorNode(ErrorType error) {
		this.error = error;
	}

}

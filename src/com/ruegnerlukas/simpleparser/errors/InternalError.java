package com.ruegnerlukas.simpleparser.errors;

public class InternalError extends Error {

	public InternalError() {
		super(ErrorType.INTERNAL_ERROR);
	}

}

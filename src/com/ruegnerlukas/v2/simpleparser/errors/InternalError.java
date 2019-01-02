package com.ruegnerlukas.v2.simpleparser.errors;

public class InternalError extends Error{

	public InternalError() {
		super(ErrorType.INTERNAL_ERROR);
	}

}

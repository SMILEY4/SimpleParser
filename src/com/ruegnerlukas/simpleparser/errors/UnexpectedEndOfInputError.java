package com.ruegnerlukas.simpleparser.errors;

import java.util.HashSet;
import java.util.Set;

public class UnexpectedEndOfInputError extends Error {


	public Set<String> expected = new HashSet<>();




	public UnexpectedEndOfInputError(String expected) {
		super(ErrorType.UNEXPECTED_END_OF_INPUT);
		this.expected.add(expected);
	}




	public UnexpectedEndOfInputError(Set<String> expected) {
		super(ErrorType.UNEXPECTED_END_OF_INPUT);
		this.expected.addAll(expected);
	}




	@Override
	public String toString() {
		return "Error: " + getType() + " - expected: " + expected;
	}

}

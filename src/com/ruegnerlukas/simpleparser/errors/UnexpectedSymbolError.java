package com.ruegnerlukas.simpleparser.errors;

import java.util.HashSet;
import java.util.Set;

public class UnexpectedSymbolError extends Error{

	public Set<String> expected = new HashSet<>();
	public String actual;
	public int index;


	public UnexpectedSymbolError(String expected, String actual, int index) {
		super(ErrorType.UNEXPECTED_SYMBOL);
		this.expected.add(expected);
		this.actual = actual;
		this.index = index;
	}


	public UnexpectedSymbolError(Set<String> expected, String actual, int index) {
		super(ErrorType.UNEXPECTED_SYMBOL);
		this.expected.addAll(expected);
		this.actual = actual;
		this.index = index;
	}




	@Override
	public String toString() {
		return "Error: " + getType() + " - @" + index + " expected: " + expected + ",  actual: " + actual;
	}

}

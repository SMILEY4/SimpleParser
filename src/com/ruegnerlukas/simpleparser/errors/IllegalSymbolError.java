package com.ruegnerlukas.simpleparser.errors;

import java.util.HashSet;
import java.util.Set;

public class IllegalSymbolError extends Error{

	public Set<String> expected = new HashSet<>();
	public String actual;
	public int index;


	public IllegalSymbolError(String expected, String actual, int index) {
		super(ErrorType.ILLEGAL_SYMBOL);
		this.expected.add(expected);
		this.actual = actual;
		this.index = index;
	}


	public IllegalSymbolError(Set<String> expected, String actual, int index) {
		super(ErrorType.ILLEGAL_SYMBOL);
		this.expected.addAll(expected);
		this.actual = actual;
		this.index = index;
	}



	@Override
	public String toString() {
		return "Error: " + getType() + " - @" + index + " expected: " + expected + ",  actual: " + actual;
	}

}

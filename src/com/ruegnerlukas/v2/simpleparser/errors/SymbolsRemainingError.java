package com.ruegnerlukas.v2.simpleparser.errors;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SymbolsRemainingError extends Error{

	public int index;
	public Set<String> remaining = new HashSet<>();


	public SymbolsRemainingError(int index, String... remaining) {
		super(ErrorType.SYMBOLS_REMAINING);
		this.index = index;
		this.remaining.addAll(Arrays.asList(remaining));
	}


	public SymbolsRemainingError(int index, Set<String> remaining) {
		super(ErrorType.SYMBOLS_REMAINING);
		this.index = index;
		this.remaining.addAll(remaining);
	}


	@Override
	public String toString() {
		return "Error: " + getType() + " - @" + index + " remaining:" + remaining;
	}

}

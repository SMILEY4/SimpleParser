package com.ruegnerlukas.simpleparser.errors;

import com.ruegnerlukas.simpleparser.tokens.Token;

import java.util.List;

public class UndefinedSymbolError extends ErrorMessage {


	public UndefinedSymbolError(Object origin, List<Token> consumed, String found, String expected) {
		this(origin, consumed.size(), found, expected);
	}


	public UndefinedSymbolError(Object origin, int tokenIndex, String found, String expected) {
		super(ErrorMessage.TITLE_SYNTAX_ERROR, "Found undefined symbol: '"
				+ found + "', expected: '" + expected + "'.", origin, tokenIndex);
	}


	public UndefinedSymbolError(Object origin, List<Token> consumed) {
		this(origin, consumed.size());
	}


	public UndefinedSymbolError(Object origin, int tokenIndex) {
		super(ErrorMessage.TITLE_SYNTAX_ERROR, "Found undefined symbol.", origin, tokenIndex);
	}

}

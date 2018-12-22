package com.ruegnerlukas.simpleparser.errors;

import com.ruegnerlukas.simpleparser.tokens.Token;

import java.util.List;

public class UnexpectedSymbolError extends ErrorMessage {


	public UnexpectedSymbolError(Object origin, List<Token> consumed, String found, String expected) {
		this(origin, consumed.size(), found, expected);
	}


	public UnexpectedSymbolError(Object origin, int tokenIndex, String found, String expected) {
		super(ErrorMessage.TITLE_SYNTAX_ERROR, "Found unexpected symbol: '"
				+ found + "', expected: '" + expected + "'.", origin, tokenIndex);
	}


	public UnexpectedSymbolError(Object origin, List<Token> consumed) {
		this(origin, consumed.size());
	}


	public UnexpectedSymbolError(Object origin, int tokenIndex) {
		super(ErrorMessage.TITLE_SYNTAX_ERROR, "Found unexpected symbol.", origin, tokenIndex);
	}

}

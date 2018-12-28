package com.ruegnerlukas.simpleparser.errors;

import com.ruegnerlukas.simpleparser.tokens.Token;

import java.util.List;

public class NoMatchError extends ErrorMessage {


	public NoMatchError(Object origin, List<Token> consumed) {
		this(origin, consumed.size());
	}


	public NoMatchError(Object origin, int tokenIndex) {
		super(ErrorMessage.TITLE_SYNTAX_ERROR, "Grammar does not accept given input.", origin, tokenIndex);
	}


}
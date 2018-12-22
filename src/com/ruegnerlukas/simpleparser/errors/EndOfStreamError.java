package com.ruegnerlukas.simpleparser.errors;

import com.ruegnerlukas.simpleparser.tokens.Token;

import java.util.List;

public class EndOfStreamError extends ErrorMessage {


	public EndOfStreamError(Object origin, List<Token> consumed) {
		this(origin, consumed.size());
	}


	public EndOfStreamError(Object origin, int tokenIndex) {
		super(ErrorMessage.TITLE_SYNTAX_ERROR, "Reached end of stream.", origin, tokenIndex);
	}


}
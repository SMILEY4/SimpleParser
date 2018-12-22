package com.ruegnerlukas.simpleparser.errors;

import com.ruegnerlukas.simpleparser.tokens.Token;

import java.util.List;

public class TokensRemainingError extends ErrorMessage {


	public TokensRemainingError(Object origin, List<Token> consumed) {
		this(origin, consumed.size());
	}


	public TokensRemainingError(Object origin, int tokenIndex) {
		super(ErrorMessage.TITLE_SYNTAX_ERROR, "Tokens remaining after match.", origin, tokenIndex);
	}


}
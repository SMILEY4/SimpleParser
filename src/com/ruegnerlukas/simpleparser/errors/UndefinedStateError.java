package com.ruegnerlukas.simpleparser.errors;

import com.ruegnerlukas.simpleparser.tokens.Token;

import java.util.List;

public class UndefinedStateError extends ErrorMessage {


	public UndefinedStateError(Object origin, List<Token> consumed) {
		this(origin, consumed.size());
	}


	public UndefinedStateError(Object origin, int tokenIndex) {
		super(ErrorMessage.TITLE_INTERNAL_ERROR, "Undefined State.", origin, tokenIndex);
	}


}
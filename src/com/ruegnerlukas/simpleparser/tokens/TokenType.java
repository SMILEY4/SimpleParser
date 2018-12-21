package com.ruegnerlukas.simpleparser.tokens;

public enum TokenType {

	/**
	 * Valid/defined in a grammar
	 * */
	TOKEN,

	/**
	 * Not defined in a grammar but can be ignored when processing an input
	 * */
	IGNORABLE,

	/**
	 * Invalid token/symbol
	 * */
	UNDEFINED

}

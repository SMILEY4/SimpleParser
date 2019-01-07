package com.ruegnerlukas.simpleparser.errors;

public enum ErrorType {

	/** encountered illegal character **/
	ILLEGAL_SYMBOL,

	/** encountered legal symbol at unexpected place. (has expected,actual) **/
	UNEXPECTED_SYMBOL,

	/** end of input string **/
	UNEXPECTED_END_OF_INPUT,

	/** end of grammar, but symbols remaining **/
	SYMBOLS_REMAINING,

	/** child of a expression returned an error*/
	ERROR,

	INTERNAL_ERROR

}
package com.ruegnerlukas.v2.simpleparser;

public enum ErrorType {

	/** encountered illegal character **/
	ILLEGAL_CHARACTER,

	/** encountered legal symbol at unexpected place. (has expected,actual) **/
	UNEXPECTED_SYMBOL,

	/** end of input string **/
	UNEXPECTED_END_OF_INPUT,

	/** end of grammar, but symbols remaining **/
	SYMBOLS_REMAINING,

	UNKNOWN_ERROR,
	INTERNAL_ERROR

}
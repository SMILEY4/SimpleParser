package com.ruegnerlukas.v2.simpleparser.errors;

public enum ErrorType {

	/** encountered illegal character **/
	ILLEGAL_SYMBOL(5),

	/** encountered legal symbol at unexpected place. (has expected,actual) **/
	UNEXPECTED_SYMBOL(4),

	/** end of input string **/
	UNEXPECTED_END_OF_INPUT(3),

	/** end of grammar, but symbols remaining **/
	SYMBOLS_REMAINING(2),

	/** child of a expression returned an error*/
	ERROR(1),

	INTERNAL_ERROR(0);

	public final int level;

	private ErrorType(int level) {
		this.level = level;
	}

}
package com.ruegnerlukas.simpleparser.errors;

import com.ruegnerlukas.simpleparser.tokens.Token;

import java.util.HashSet;
import java.util.Set;

public class Error {


	public enum Type {

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


	public final Type msg;
	public final int indexStart;
	public final int indexEnd;
	public final Set<Token> expected;
	public final Token actual;


	public Error(Type msg, int indexStart, int indexEnd) {
		this(msg, indexStart, indexEnd, new HashSet<>(), null);
	}


	public Error(Type msg, int indexStart, int indexEnd, Set<Token> expected, Token actual) {
		this.msg = msg;
		this.indexStart = indexStart;
		this.indexEnd = indexEnd;
		this.expected = expected;
		this.actual = actual;
	}




	public static boolean toStringIncludeExpectedActual = false;

	@Override
	public String toString() {
		if( toStringIncludeExpectedActual && ((expected != null && !expected.isEmpty()) || actual != null) ) {
			StringBuilder str = new StringBuilder();
			for(Token t : expected) {
				str.append('\'');
				str.append(t.getSymbol());
				str.append('\'');
				str.append(',');
			}
			return msg.toString() + "(" + indexStart + "," + indexEnd + "): expected: \"" + str.toString() + "\" actual: \"" + actual + "\"";
		} else {
			return msg.toString() + "(" + indexStart + "," + indexEnd + ")";
		}
	}

}
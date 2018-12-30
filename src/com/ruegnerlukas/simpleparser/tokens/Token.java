package com.ruegnerlukas.simpleparser.tokens;


public class Token {


	/**
	 * Creates a new token; a character or sequence of characters that can not be split into smaller parts.
	 * @param symbol the symbol of this token
	 * */
	public static Token token(String symbol) {
		return new Token(TokenType.TOKEN, symbol);
	}


	/**
	 * Creates a new token that is not defined in a given grammar but can be ignored when processing an input.
	 * @param symbol the symbol of this token
	 * */
	public static Token ignoreable(String symbol) {
		return new Token(TokenType.IGNORABLE, symbol);
	}


	/**
	 * Creates a new token that is not allowed by a given grammar.
	 * @param symbol the symbol of this token
	 * */
	public static Token undefined(String symbol) {
		return new Token(TokenType.UNDEFINED, symbol);
	}





	private final String symbol;
	private final TokenType type;




	/**
	 * Creates a new token; a character or sequence of characters that can not be split into smaller parts.
	 * @param type the type of the token {@link TokenType}
	 * @param symbol the symbol of this token
	 * */
	private Token(TokenType type, String symbol) {
		this.type = type;
		this.symbol = symbol;
	}




	/**
	 * @return the symbol of this token
	 * */
	public String getSymbol() {
		return this.symbol;
	}




	/**
	 * @return the type of this token  {@link TokenType}
	 * */
	public TokenType getType() {
		return this.type;
	}



	@Override
	public String toString() {
		return getType().toString() + "'" + symbol + "'";
	}


	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Token) {
			Token other = (Token)obj;
			return this.getType() == other.getType() && this.getSymbol() == other.getSymbol();
		} else {
			return false;
		}
	}


}
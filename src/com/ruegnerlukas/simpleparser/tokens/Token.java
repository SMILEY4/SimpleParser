package com.ruegnerlukas.simpleparser.tokens;



public class Token {

	public final String symbol;
	
	public Token(String symbol) {
		this.symbol = symbol;
	}
	
	@Override
	public String toString() {
		return "TOKEN:'"+symbol+"'";
	}
	
}

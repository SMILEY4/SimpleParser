package com.ruegnerlukas.simpleparser;

public class Token {


	private String symbol;




	public Token(String symbol) {
		this.symbol = symbol;
	}




	public String getSymbol() {
		return this.symbol;
	}




	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Token) {
			return this.symbol.equals(((Token) obj).getSymbol());
		}
		return false;
	}




	@Override
	public String toString() {
		return "TKN:" + getSymbol();
	}

}

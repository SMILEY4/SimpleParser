package com.ruegnerlukas.simpleparser;

public class Token {


	private String name;
	private String symbol;




	public Token(String symbol) {
		this(null, symbol);
	}




	public Token(String tokenname, String symbol) {
		this.name = tokenname;
		this.symbol = symbol;
	}




	public String getSymbol() {
		return this.symbol;
	}




	public String getName() {
		return name;
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

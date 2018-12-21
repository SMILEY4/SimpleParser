package com.ruegnerlukas.simpleparser.tokens;



public class UndefinedToken extends Token {

	public UndefinedToken(String str) {
		super(str);
	}
	
	@Override
	public String toString() {
		return "UNDEFINED:'"+symbol+"'";
	}

}

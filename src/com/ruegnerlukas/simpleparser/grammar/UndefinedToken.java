package com.ruegnerlukas.simpleparser.grammar;



public class UndefinedToken extends Token {

	public UndefinedToken(String str) {
		super(str);
	}
	
	@Override
	public String toString() {
		return "UNDEFINED:'"+symbol+"'";
	}

}

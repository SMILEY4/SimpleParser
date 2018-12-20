package com.ruegnerlukas.simpleparser.grammar;



public class IgnorableToken extends Token {

	public IgnorableToken(String str) {
		super(str);
	}
	
	@Override
	public String toString() {
		return "IGNORABLE:'"+symbol+"'";
	}

}

package com.ruegnerlukas.simpleparser.grammar;



public class Atom {

	public final String symbol;
	
	public Atom(String symbol) {
		this.symbol = symbol;
	}
	
	@Override
	public String toString() {
		return "ATOM:'"+symbol+"'";
	}
	
}

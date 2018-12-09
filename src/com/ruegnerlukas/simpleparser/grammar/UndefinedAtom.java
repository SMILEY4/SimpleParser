package com.ruegnerlukas.simpleparser.grammar;



public class UndefinedAtom extends Atom {

	public UndefinedAtom(String str) {
		super(str);
	}
	
	@Override
	public String toString() {
		return "ERROR:'"+symbol+"'";
	}

}

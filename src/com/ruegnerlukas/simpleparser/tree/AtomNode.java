package com.ruegnerlukas.simpleparser.tree;

import com.ruegnerlukas.simpleparser.grammar.Token;

public class AtomNode extends Node {

	public Token atom;
	
	public AtomNode(Token atom) {
		this.atom = atom;
	}
	
	@Override
	public String toString() {
		return Integer.toHexString(this.hashCode()) + ": " + (atom == null ? "null" : atom.symbol.replaceAll("\"", "\\\\\""));
	}
	
}

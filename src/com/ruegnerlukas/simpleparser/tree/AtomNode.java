package com.ruegnerlukas.simpleparser.tree;

import com.ruegnerlukas.simpleparser.grammar.Token;

public class AtomNode extends Node {

	public Token token;
	
	public AtomNode(Token token) {
		this.token = token;
	}
	
	@Override
	public String toString() {
		return Integer.toHexString(this.hashCode()) + ": " + (token == null ? "null" : token.symbol.replaceAll("\"", "\\\\\""));
	}
	
}

package com.ruegnerlukas.simpleparser.tree;

import com.ruegnerlukas.simpleparser.tokens.Token;

public class TerminalNode extends Node {

	public final Token token;
	
	public TerminalNode(Token token) {
		this.token = token;
	}
	
	@Override
	public String toString() {
		return "\"" + "Terminal:"+Integer.toHexString(this.hashCode()) + ": " + (token == null ? "null" : token.symbol.replaceAll("\"", "\\\\\"")) + "\"";
	}
	
}

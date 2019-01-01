package com.ruegnerlukas.v1.simpleparser.tree;

import com.ruegnerlukas.v1.simpleparser.tokens.Token;

public class TerminalNode extends Node {


	public final Token token;




	public TerminalNode(Token token) {
		this.token = token;
	}



	@Override
	public String toString() {
		return "Terminal:"+Integer.toHexString(this.hashCode()) + ": " + (token == null ? "null" : token.getSymbol().replaceAll("\"", "\\\\\""));
	}

}
package com.ruegnerlukas.simpleparser.tree;

import com.ruegnerlukas.simpleparser.tokens.Token;

import java.util.List;

public class TerminalNode extends Node {


	public final Token token;




	public TerminalNode(Token token) {
		this.token = token;
	}



	@Override
	public void collectTerminalNodes(List<TerminalNode> nodes) {
		nodes.add(this);
	}



	@Override
	public String toString() {
		return "\"" + "Terminal:"+Integer.toHexString(this.hashCode()) + ": " + (token == null ? "null" : token.getSymbol().replaceAll("\"", "\\\\\"")) + "\"";
	}

}
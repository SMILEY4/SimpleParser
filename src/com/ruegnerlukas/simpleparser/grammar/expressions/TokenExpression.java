package com.ruegnerlukas.simpleparser.grammar.expressions;

import com.ruegnerlukas.simpleparser.grammar.Token;
import com.ruegnerlukas.simpleparser.tree.EmptyNode;
import com.ruegnerlukas.simpleparser.tree.Node;
import com.ruegnerlukas.simpleparser.tree.TerminalNode;

import java.util.List;
import java.util.Set;

public class TokenExpression extends Expression {

	public Token token;
	
	public TokenExpression(Token token) {
		this.token = token;
	}


	@Override
	public Node apply(List<Token> tokens) {
		if(!tokens.isEmpty() && tokens.get(0) == token) {
			TerminalNode node = new TerminalNode(token);
			node.token = this.token;
			tokens.remove(0);
			return node;
			
		} else {
			return new EmptyNode();
		}
	}




	@Override
	public String toString() {
		return "\"" + "TOKEN:"+Integer.toHexString(this.hashCode())+ ": " + token.symbol + "\"";
	}




	@Override
	public void printAsDotGraph(Set<Expression> visited) {
		if(visited.contains(this)) {
			return;
		}
		visited.add(this);
	}
	
}

package com.ruegnerlukas.simpleparser.grammar.expressions;

import java.util.List;

import com.ruegnerlukas.simpleparser.grammar.Token;
import com.ruegnerlukas.simpleparser.tree.AtomNode;
import com.ruegnerlukas.simpleparser.tree.EmptyNode;
import com.ruegnerlukas.simpleparser.tree.Node;

public class TokenExpression extends Expression {

	public Token token;
	
	public TokenExpression(Token token) {
		this.token = token;
	}


	@Override
	public Node apply(List<Token> tokens) {
		if(!tokens.isEmpty() && tokens.get(0) == token) {
			AtomNode node = new AtomNode(token);
			node.token = this.token;
			tokens.remove(0);
			return node;
			
		} else {
			return new EmptyNode();
		}
	}

	
}

package com.ruegnerlukas.simpleparser.grammar.expressions;

import java.util.List;

import com.ruegnerlukas.simpleparser.grammar.Token;
import com.ruegnerlukas.simpleparser.tree.AtomNode;
import com.ruegnerlukas.simpleparser.tree.EmptyNode;
import com.ruegnerlukas.simpleparser.tree.Node;

public class TokenExpression extends Expression {

	public Token atom;
	
	public TokenExpression(Token atom) {
		this.atom = atom;
	}


	@Override
	public Node apply(List<Token> tokens, int level) {
		if(!tokens.isEmpty() && tokens.get(0) == atom) {
			AtomNode node = new AtomNode(atom);
			node.op = this;
			tokens.remove(0);
			return node;
			
		} else {
			return new EmptyNode();
		}
	}
	
}

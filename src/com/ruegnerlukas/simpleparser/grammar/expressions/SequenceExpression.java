package com.ruegnerlukas.simpleparser.grammar.expressions;

import java.util.ArrayList;
import java.util.List;

import com.ruegnerlukas.simpleparser.grammar.Token;
import com.ruegnerlukas.simpleparser.tree.EmptyNode;
import com.ruegnerlukas.simpleparser.tree.MidNode;
import com.ruegnerlukas.simpleparser.tree.Node;

/**
 * X -> E0 E1 ... EN
 * */
public class SequenceExpression extends Expression {

	public List<Expression> ops = new ArrayList<Expression>();

	public SequenceExpression(Expression... ops) {
		for(Expression op : ops) {
			this.ops.add(op);
		}
	}

	


	@Override
	public Node apply(List<Token> tokens, int level) {
		Node node = new MidNode(Integer.toHexString(this.hashCode()));
		for(Expression op : ops) {
			Node n = op.apply(tokens, level+1);
			if(n instanceof EmptyNode) {
				break;
			} else {
				node.children.add(n);
			}
		}
		if(node.children.isEmpty()) {
			return new EmptyNode();
		} else {
			return node;
		}
	}


	
}







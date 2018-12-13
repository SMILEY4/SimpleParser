package com.ruegnerlukas.simpleparser.grammar.expressions;

import java.util.List;

import com.ruegnerlukas.simpleparser.grammar.Rule;
import com.ruegnerlukas.simpleparser.grammar.Token;
import com.ruegnerlukas.simpleparser.tree.EmptyNode;
import com.ruegnerlukas.simpleparser.tree.Node;
import com.ruegnerlukas.simpleparser.tree.RuleNode;

public class RuleExpression extends Expression {

	public Rule rule;
	
	
	public RuleExpression(Rule rule) {
		this.rule = rule;
	}

	
	

	@Override
	public Node apply(List<Token> tokens) {
		Node node = new RuleNode(rule);
		Node n = rule.expression.apply(tokens);
		if(n instanceof EmptyNode) {
			return new EmptyNode();
		} else {
			node.children.add(n);
			return node;
		}
	}


}

package com.ruegnerlukas.simpleparser.tree;

import java.util.List;

import com.ruegnerlukas.simpleparser.grammar.Grammar;
import com.ruegnerlukas.simpleparser.grammar.Rule;
import com.ruegnerlukas.simpleparser.grammar.Token;

public class TreeBuilder {

	/**
	 * builds a tree from the given tokenlist and grammar
	 * */
	public Node build(Grammar grammar, List<Token> tokens) {
		Rule start = grammar.getRule(grammar.getStartingRule());
		RuleNode root = new RuleNode(start);
		root.children.add(start.expression.apply(tokens));
		root.eliminateMidNodes();
		return root;
	}
	
}

package com.ruegnerlukas.simpleparser.tree;

import java.util.List;

import com.ruegnerlukas.simpleparser.grammar.Grammar;
import com.ruegnerlukas.simpleparser.grammar.Rule;
import com.ruegnerlukas.simpleparser.grammar.Token;

public class TreeBuilder {

	
	/**
	 * builds a tree from the given token list and grammar
	 * */
	public Node build(Grammar grammar, List<Token> tokens) {
		Rule start = grammar.getRule(grammar.getStartingRule());
		RuleNode root = new RuleNode(start);
		root.children.add(start.op.apply(tokens, 0));
		root.eliminateMidNodes();
		return root;
	}
	
}

package com.ruegnerlukas.simpleparser.tree;

import com.ruegnerlukas.simpleparser.grammar.Grammar;
import com.ruegnerlukas.simpleparser.grammar.Rule;
import com.ruegnerlukas.simpleparser.grammar.Token;

import java.util.List;

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


	/**
	 * removes chains of non-terminals that only have one child. Only removes the non-terminals given in the "rules"-array
	 * */
	public Node collapseTree(Node root, String... rules) {
		return root.collapseTree(rules);
	}




	/**
	 * removes all terminal-nodes contained in the given array
	 * */
	public Node removeTerminals(Node root, String... terminals) {
		return root.removeTerminals(terminals);
	}


}

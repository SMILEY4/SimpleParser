package com.ruegnerlukas.simpleparser.tree;

import java.util.List;

import com.ruegnerlukas.simpleparser.grammar.Atom;
import com.ruegnerlukas.simpleparser.grammar.Grammar;
import com.ruegnerlukas.simpleparser.grammar.Rule;

public class TreeBuilder {

	
	public Node build(Grammar grammar, List<Atom> tokens) {
		Rule start = grammar.getRule(grammar.getStartingRule());
		RuleNode root = new RuleNode(start);
		root.children.add(start.op.apply(tokens, 0));
		root.eliminateMidNodes();
		return root;
	}
	
	
	
}

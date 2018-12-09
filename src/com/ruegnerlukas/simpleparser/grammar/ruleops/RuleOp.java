package com.ruegnerlukas.simpleparser.grammar.ruleops;

import java.util.List;

import com.ruegnerlukas.simpleparser.grammar.Atom;
import com.ruegnerlukas.simpleparser.grammar.Rule;
import com.ruegnerlukas.simpleparser.tree.EmptyNode;
import com.ruegnerlukas.simpleparser.tree.Node;
import com.ruegnerlukas.simpleparser.tree.RuleNode;

public class RuleOp extends Op {

	public Rule rule;
	
	public RuleOp(Rule rule) {
		this.rule = rule;
	}

	
	@Override
	public void collectAtoms(List<Atom> atoms) {
		/* do nothing */
	}

	
	

	@Override
	public Node apply(List<Atom> tokens, int level) {
		Node node = new RuleNode(rule);
		Node n = rule.op.apply(tokens, level+1);
		if(n instanceof EmptyNode) {
			return new EmptyNode();
		} else {
			node.children.add(n);
			return node;
		}
	}
	
}

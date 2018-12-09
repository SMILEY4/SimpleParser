package com.ruegnerlukas.simpleparser.grammar.ruleops;

import java.util.List;

import com.ruegnerlukas.simpleparser.grammar.Atom;
import com.ruegnerlukas.simpleparser.tree.EmptyNode;
import com.ruegnerlukas.simpleparser.tree.Node;

/**
 * X -> [E]
 *  => none or one E
 * */
public class OptOp extends Op {

	public Op op = null;
	
	public OptOp(Op op) {
		this.op = op;
	}
	
	
	@Override
	public void collectAtoms(List<Atom> atoms) {
		op.collectAtoms(atoms);
	}


	@Override
	public Node apply(List<Atom> tokens, int level) {
		if(tokens.isEmpty()) {
			return new EmptyNode();
		} else {
			Node n = op.apply(tokens, level+1);
			if(n instanceof EmptyNode) {
				return new EmptyNode();
			} else {
				return n;
			}
		}
	}
	
}

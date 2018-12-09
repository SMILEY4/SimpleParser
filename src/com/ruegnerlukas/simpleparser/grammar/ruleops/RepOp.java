package com.ruegnerlukas.simpleparser.grammar.ruleops;

import java.util.List;

import com.ruegnerlukas.simpleparser.grammar.Atom;
import com.ruegnerlukas.simpleparser.tree.EmptyNode;
import com.ruegnerlukas.simpleparser.tree.MidNode;
import com.ruegnerlukas.simpleparser.tree.Node;

/**
 * X -> E E*
 *  => one or more
 * */
public class RepOp extends Op {

	public Op op = null;
	
	public RepOp(Op op) {
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
			Node node = new MidNode(Integer.toHexString(this.hashCode()));
			while(true) {
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

	
}

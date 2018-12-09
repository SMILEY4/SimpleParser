package com.ruegnerlukas.simpleparser.grammar.ruleops;

import java.util.ArrayList;
import java.util.List;

import com.ruegnerlukas.simpleparser.grammar.Atom;
import com.ruegnerlukas.simpleparser.tree.EmptyNode;
import com.ruegnerlukas.simpleparser.tree.MidNode;
import com.ruegnerlukas.simpleparser.tree.Node;

/**
 * X -> E0 E1 ... EN
 * */
public class ConcatOp extends Op {

	public List<Op> ops = new ArrayList<Op>();

	public ConcatOp(Op... ops) {
		for(Op op : ops) {
			this.ops.add(op);
		}
	}

	
	
	@Override
	public void collectAtoms(List<Atom> atoms) {
		for(Op op : ops) {
			op.collectAtoms(atoms);
		}
	}

	


	@Override
	public Node apply(List<Atom> tokens, int level) {
		Node node = new MidNode(Integer.toHexString(this.hashCode()));
		for(Op op : ops) {
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







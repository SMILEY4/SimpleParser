package com.ruegnerlukas.simpleparser.grammar.ruleops;

import java.util.List;

import com.ruegnerlukas.simpleparser.grammar.Atom;
import com.ruegnerlukas.simpleparser.tree.AtomNode;
import com.ruegnerlukas.simpleparser.tree.EmptyNode;
import com.ruegnerlukas.simpleparser.tree.Node;

public class AtomOp extends Op {

	public Atom atom;
	
	public AtomOp(Atom atom) {
		this.atom = atom;
	}
	
	
	@Override
	public void collectAtoms(List<Atom> atoms) {
		atoms.add(atom);
	}


	@Override
	public Node apply(List<Atom> tokens, int level) {
		if(!tokens.isEmpty() && tokens.get(0) == atom) {
			AtomNode node = new AtomNode(atom);
			node.op = this;
			tokens.remove(0);
			return node;
			
		} else {
			return new EmptyNode();
		}
	}
	
}

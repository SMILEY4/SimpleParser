package com.ruegnerlukas.simpleparser.grammar.ruleops;

import java.util.List;

import com.ruegnerlukas.simpleparser.grammar.Atom;
import com.ruegnerlukas.simpleparser.tree.Node;

public abstract class Op {
	public abstract void collectAtoms(List<Atom> atoms);
	public abstract Node apply(List<Atom> tokens, int level);
}

package com.ruegnerlukas.simpleparser.tree;

import com.ruegnerlukas.simpleparser.grammar.Atom;

public class AtomNode extends Node {

	public Atom atom;
	
	public AtomNode(Atom atom) {
		this.atom = atom;
	}
	
	@Override
	public String toString() {
		return Integer.toHexString(this.hashCode()) + ": " + (atom == null ? "null" : atom.symbol.replaceAll("\"", "\\\\\""));
	}
	
}

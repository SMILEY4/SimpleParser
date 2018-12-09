package com.ruegnerlukas.simpleparser.tokenizer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.ruegnerlukas.simpleparser.grammar.Atom;
import com.ruegnerlukas.simpleparser.grammar.Grammar;
import com.ruegnerlukas.simpleparser.grammar.UndefinedAtom;

public class Tokenizer {

	private String string;
	private Grammar grammar;
	
	private boolean hasError = false;
	
	
	
	
	
	public Tokenizer(String string, Grammar grammar) {
		this.string = string;
		this.grammar = grammar;
	}
	
	
	
	
	public List<Atom> tokenize() {
		
		// get all atoms, sort by size (desc.)
		List<Atom> atoms = grammar.getAtoms();
		atoms.sort(new Comparator<Atom>() {
			@Override public int compare(Atom a1, Atom a2) {
				int n1 = a1.symbol.length();
				int n2 = a2.symbol.length();
				if(n1 < n2) {
					return +1;
				}
				if(n2 < n1) {
					return -1;
				}
				return 0;
			}});
		
		List<Atom> tokens = new ArrayList<Atom>();
		
		int indexError = 0;
		boolean isError = false;
		
		int i = 0;
		while(i < string.length()) {

			String str = string.substring(i, string.length());
			
			Atom currAtom = null;
			for(Atom atom : atoms) {
				if(str.startsWith(atom.symbol)) {
					currAtom = atom;
					break;
				}
			}

			if(currAtom == null) {
				hasError = true;
				if(!isError) {
					isError = true;
					indexError = i;
				}
				i++;
				
			} else {
				if(isError) {
					isError = false;
					tokens.add(new UndefinedAtom(string.substring(indexError, i)));
				}
				tokens.add(currAtom);
				i += currAtom.symbol.length();
			}
			
		}
		
		return tokens;
	}
	
	
	
	
	public boolean hasError() {
		return this.hasError;
	}
	
	
	
}









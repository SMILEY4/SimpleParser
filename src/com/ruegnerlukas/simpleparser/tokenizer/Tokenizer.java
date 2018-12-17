package com.ruegnerlukas.simpleparser.tokenizer;

import com.ruegnerlukas.simpleparser.grammar.Grammar;
import com.ruegnerlukas.simpleparser.grammar.Token;
import com.ruegnerlukas.simpleparser.grammar.UndefinedToken;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Tokenizer {

	private String string;
	private Grammar grammar;
	
	private boolean hasError = false;
	
	
	
	
	/**
	 * @param string the string to split into tokens
	 * @param grammar the grammar to apply to the given string
	 * */
	public Tokenizer(String string, Grammar grammar) {
		this.string = string;
		this.grammar = grammar;
	}




	public List<Token> tokenize() {
		return tokenize(false);
	}


	public List<Token> tokenize(boolean ignoreWhitespace) {
		hasError = false;

		// get all symbols/tokens, sort by size (desc.)
		List<Token> atoms = grammar.getTokens();
		atoms.sort(new Comparator<Token>() {
			@Override public int compare(Token a1, Token a2) {
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
		
		List<Token> tokens = new ArrayList<Token>();
		
		int indexError = 0;
		boolean isError = false;
		
		int i = 0;
		while(i < string.length()) {

			String str = string.substring(i);

			if(ignoreWhitespace && str.startsWith(" ")) {
				i++;
				continue;
			}

			Token currAtom = null;
			for(Token atom : atoms) {
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
					tokens.add(new UndefinedToken(string.substring(indexError, i)));
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









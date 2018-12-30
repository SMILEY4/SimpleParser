package com.ruegnerlukas.simpleparser.tokens;

import com.ruegnerlukas.simpleparser.grammar.Grammar;

import java.util.*;

public class Tokenizer {


	private Grammar grammar;

	
	
	
	/**
	 * @param grammar the grammar to apply to a given input
	 * */
	public Tokenizer(Grammar grammar) {
		this.grammar = grammar;
	}




	/**
	 * Splits the input string into a list of tokens
	 * @param strInput the input string
	 * @return a list of tokens
	 * */
	public List<Token> tokenize(String strInput) {
		return tokenize(strInput, new HashSet<String>(), false);
	}




	/**
	 * Splits the input string into a list of tokens
	 * @param strInput the input string
	 * @param ignorableSymbols a set of symbols that can be ignored when processing the input.
	 *                            If the symbol is already defined in the grammar, the symbol will not be ignored
	 * @param addIgnorables whether the ignorable symbols should be added to the output list
	 * @return a list of tokens
	 * */
	public List<Token> tokenize(String strInput, Set<String> ignorableSymbols, boolean addIgnorables) {

		Set<Token> ignorableTokens = new HashSet<>();
		List<Token> tokens = grammar.getTokens();

		for(String ignore : ignorableSymbols) {
			boolean exists = false;
			for(Token token : tokens) {
				if(token.getSymbol().contentEquals(ignore)) {
					exists = true;
					break;
				}
			}
			if(!exists) {
				ignorableTokens.add(Token.ignoreable(ignore));
			}
		}

		return tokenize(strInput, addIgnorables, ignorableTokens);
	}




	/**
	 * Splits the input string into a list of tokens
	 * @param strInput the input string
	 * @param addIgnorables whether the ignorable symbols should be added to the output list
	 * @param ignorableTokens a set of symbols that can be ignored when processing the input.
	 *                           The token type of all tokens in the set must be {@code TokenType.IGNORE}.
	 *                           If the symbol is already defined in the grammar, the symbol will not be ignored
	 * @return a list of tokens
	 * */
	public List<Token> tokenize(String strInput, boolean addIgnorables, Set<Token> ignorableTokens) {

		// get all symbols/tokens
		List<Token> tokens = grammar.getTokens();
		for(Token ignore : ignorableTokens) {
			if(ignore.getType() == TokenType.IGNORABLE) {
				tokens.add(ignore);
			}
		}

		// sort all tokens by size
		tokens.sort(new Comparator<Token>() {
			@Override public int compare(Token a1, Token a2) {
				int n1 = a1.getSymbol().length();
				int n2 = a2.getSymbol().length();
				if(n1 < n2) {
					return +1;
				}
				if(n2 < n1) {
					return -1;
				}
				return 0;
			}});

		// tokenize input string
		List<Token> output = new ArrayList<>();
		int indexError = 0;
		boolean isError = false;
		
		int i = 0;
		while(i < strInput.length()) {

			String str = strInput.substring(i);

			// search token at current index in input
			Token currToken = null;
			for(Token token : tokens) {
				if(str.startsWith(token.getSymbol())) {
					currToken = token;
					break;
				}
			}

			// no valid token found -> start error
			if(currToken == null) {
				if(!isError) {
					isError = true;
					indexError = i;
				}
				i++;

			// token found -> add token, end error
			} else {
				if(isError) {
					isError = false;
					output.add(Token.undefined(strInput.substring(indexError, i)));
				}
				if(currToken.getType() != TokenType.IGNORABLE || addIgnorables) {
					output.add(currToken);
				}
				i += currToken.getSymbol().length();
			}
			
		}

		// reached end of input-string -> end final errorsOld
		if(isError) {
			output.add(Token.undefined(strInput.substring(indexError)));
		}
		
		return output;
	}
	

}









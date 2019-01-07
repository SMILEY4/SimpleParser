package com.ruegnerlukas.simpleparser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TokenStream {

	private int indexOffset = 0;
	private List<Token> tokens = new ArrayList<>();
	private List<Token> consumed = new ArrayList<>();


	public TokenStream(List<Token> tokens) {
		this.tokens.addAll(tokens);
	}


	public TokenStream(Token... tokens) {
		this.tokens.addAll(Arrays.asList(tokens));
	}


	public TokenStream(int indexOffset, List<Token> tokens) {
		this.indexOffset = indexOffset;
		this.tokens.addAll(tokens);
	}


	public TokenStream(int indexOffset, Token... tokens) {
		this.indexOffset = indexOffset;
		this.tokens.addAll(Arrays.asList(tokens));
	}






	public boolean hasNext() {
		if(getIndexWithoutOffset() >= tokens.size()) {
			return false;
		} else {
			return true;
		}
	}




	public Token peek() {
		if(hasNext()) {
			return tokens.get(getIndexWithoutOffset());
		} else {
			return null;
		}
	}




	public Token consume() {
		if(hasNext()) {
			Token token = peek();
			consumed.add(token);
			return token;
		} else {
			return null;
		}
	}



	public int getIndex() {
		return getIndexWithoutOffset() + indexOffset;
	}



	public int getIndexWithoutOffset() {
		return consumed.size();
	}




	public int size() {
		return tokens.size() - getIndex();
	}



	public List<Token> getConsumed() {
		return Collections.unmodifiableList(this.consumed);
	}



	public List<Token> getRemaining() {
		return tokens.subList(getIndexWithoutOffset(), tokens.size());
	}

}

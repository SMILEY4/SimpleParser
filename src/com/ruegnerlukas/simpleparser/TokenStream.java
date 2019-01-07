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




	/**
	 * @return true, if this stream has at least one token left
	 */
	public boolean hasNext() {
		if (getIndexWithoutOffset() >= tokens.size()) {
			return false;
		} else {
			return true;
		}
	}




	/**
	 * @return the next token without consuming it
	 */
	public Token peek() {
		if (hasNext()) {
			return tokens.get(getIndexWithoutOffset());
		} else {
			return null;
		}
	}




	/**
	 * @return the next token and consumes it
	 */
	public Token consume() {
		if (hasNext()) {
			Token token = peek();
			consumed.add(token);
			return token;
		} else {
			return null;
		}
	}




	/**
	 * @return the index in the total stream (includes offset)
	 */
	public int getIndex() {
		return getIndexWithoutOffset() + indexOffset;
	}




	/**
	 * @return the index of this stream
	 */
	public int getIndexWithoutOffset() {
		return consumed.size();
	}




	/**
	 * @return the amount of remaining tokens
	 */
	public int size() {
		return tokens.size() - getIndex();
	}




	/**
	 * @return an unmodifiable list of all consumed tokens of this stream
	 */
	public List<Token> getConsumed() {
		return Collections.unmodifiableList(this.consumed);
	}




	/**
	 * @return a list of all remaining tokens of this stream
	 */
	public List<Token> getRemaining() {
		return tokens.subList(getIndexWithoutOffset(), tokens.size());
	}

}

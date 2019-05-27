package com.ruegnerlukas.simpleparser;

public class CharStream {


	private int indexOffset = 0;
	private String string;
	private StringBuilder consumed = new StringBuilder();




	public CharStream(int indexOffset, String strInput) {
		this(strInput);
		this.indexOffset = indexOffset;
	}




	public CharStream(String strInput) {
		this.string = strInput;
	}




	/**
	 * @return true, if this stream has at least one character left
	 */
	public boolean hasNext() {
		if (getIndexWithoutOffset() >= string.length()) {
			return false;
		} else {
			return true;
		}
	}




	/**
	 * consumes n-characters and returns them as a single token
	 *
	 * @return the consumed characters as a token
	 */
	public Token consume(int n) {
		if (hasNext()) {
			StringBuilder token = new StringBuilder();
			int limit = Math.min(getIndexWithoutOffset() + n, string.length());
			for (int i = getIndexWithoutOffset(); i < limit; i++) {
				token.append(string.charAt(i));
				consumed.append(string.charAt(i));
			}
			return new Token(token.toString());
		} else {
			return null;
		}
	}




	/**
	 * consumes the given word (if possible) and returns it as a token
	 *
	 * @return the consumed characters as a token, or null
	 */
	public Token consume(String word) {
		if (hasNext()) {
			if (getRemaining().startsWith(word)) {
				consumed.append(word);
				return new Token(word);
			}
		}
		return null;
	}




	/**
	 * @return null, if the stream does not start with the given word;
	 * returns the word as a token otherwise
	 */
	public Token peek(String word) {
		if (hasNext()) {
			if (getRemaining().startsWith(word)) {
				return new Token(word);
			}
		}
		return null;
	}




	/**
	 * @return the next char without consuming it
	 */
	public char peek() {
		if (hasNext()) {
			return getRemaining().charAt(0);
		} else {
			return 0;
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
		return consumed.length();
	}




	/**
	 * @return the amount of remaining characters
	 */
	public int size() {
		return string.length() - getIndex();
	}




	/**
	 * @return all consumed characters as a string
	 */
	public String getConsumed() {
		return consumed.toString();
	}




	/**
	 * @return all remaining characters as a string
	 */
	public String getRemaining() {
		return string.substring(getIndexWithoutOffset(), string.length());
	}

}

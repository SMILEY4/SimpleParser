package com.ruegnerlukas.v2.simpleparser;

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




	public boolean hasNext() {
		if(getIndexWithoutOffset() >= string.length()) {
			return false;
		} else {
			return true;
		}
	}




	public Token consume(int n) {
		if(hasNext()) {
			StringBuilder token = new StringBuilder();
			int limit = Math.min(getIndexWithoutOffset()+n, string.length());
			for(int i=getIndexWithoutOffset(); i<limit; i++) {
				token.append(string.charAt(i));
				consumed.append(string.charAt(i));
			}
			return new Token(token.toString());
		} else {
			return null;
		}
	}




	public Token consume(String word) {
		if(hasNext()) {
			if(getRemaining().startsWith(word)) {
				consumed.append(word);
				return new Token(word);
			}
		}
		return null;
	}



	public Token peek(String word) {
		if(hasNext()) {
			if(getRemaining().startsWith(word)) {
				return new Token(word);
			}
		}
		return null;
	}



	public int getIndex() {
		return getIndexWithoutOffset() + indexOffset;
	}




	public int getIndexWithoutOffset() {
		return consumed.length();
	}




	public int size() {
		return string.length() - getIndex();
	}




	public String getConsumed() {
		return consumed.toString();
	}




	public String getRemaining() {
		return string.substring(getIndexWithoutOffset(), string.length());
	}

}

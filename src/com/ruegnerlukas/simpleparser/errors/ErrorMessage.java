package com.ruegnerlukas.simpleparser.errors;

public abstract class ErrorMessage {

	public static final String TITLE_SYNTAX_ERROR = "Syntax Error";
	public static final String TITLE_INTERNAL_ERROR = "Internal Error";

	public final String title;
	public final String message;
	public final Object origin;
	public final int tokenIndex;




	public ErrorMessage(String title, String message, Object origin, int tokenIndex) {
		this.title = title;
		this.message = message;
		this.origin = origin;
		this.tokenIndex = tokenIndex;
	}




	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(title).append(" ");
		builder.append("@").append(tokenIndex).append(" ");
		builder.append('[').append(origin.toString()).append("]: ");
		builder.append(message);
		return builder.toString();
	}

}
package com.ruegnerlukas.simpleparser.error;

public class ErrorMessage {

	private final String message;
	private final Errors.ErrorLevel level;
	private final Object origin;

	protected ErrorMessage(Object origin, Errors.ErrorLevel level, String message) {
		this.message = message;
		this.level = level;
		this.origin = origin;
	}




	public String getMessage() {
		return this.message;
	}




	public Errors.ErrorLevel getLevel() {
		return this.level;
	}




	public Object getOrigin() {
		return this.origin;
	}




	@Override
	public String toString() {
		return level.toString() + "-" + Integer.toHexString(this.hashCode()) + ": " + message;
	}

}

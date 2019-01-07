package com.ruegnerlukas.simpleparser.errors;

public class Error {


	private final ErrorType type;




	public Error(ErrorType type) {
		this.type = type;
	}




	public ErrorType getType() {
		return this.type;
	}




	@Override
	public String toString() {
		return "Error: " + type.toString();
	}


}

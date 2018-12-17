package com.ruegnerlukas.simpleparser.error;

import com.ruegnerlukas.simpleparser.grammar.Token;

import java.util.List;

public class ErrorMessages {


	public static String genMessage_endOfStream() {
		return "Syntax Error: Reached end of Stream.";
	}




	public static String genMessage_unexpectedSymbol(String expected, List<Token> consumed, List<Token> tokens) {
		StringBuilder builder = new StringBuilder();
		builder.append("Syntax Error: Found unexpected symbol @").append(consumed.size()).append(": ");
		for(int i=0; i<Math.min(3, tokens.size()); i++) {
			builder.append('"').append(tokens.get(i).symbol).append('"').append(" ");
		}
		builder.append("...  Expected: ").append('"').append(expected).append('"');
		return builder.toString();
	}




	public static String genMessage_unexpectedSymbol(List<Token> consumed, List<Token> tokens) {
		StringBuilder builder = new StringBuilder();
		builder.append("Syntax Error: Found unexpected symbol @").append(consumed.size()).append(": ");
		for(int i=0; i<Math.min(3, tokens.size()); i++) {
			builder.append('"').append(tokens.get(i).symbol).append('"').append(" ");
		}
		builder.append("...  ");
		return builder.toString();
	}





	public static String genMessage_undefinedState() {
		return "Internal Error: Undefined State.";
	}

}

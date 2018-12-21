package com.ruegnerlukas.simpleparser.errors;

import com.ruegnerlukas.simpleparser.tokens.Token;

import java.util.List;

public class ErrorMessages {


	public static String genMessage_endOfStream(Object source) {
		return "Syntax Error: [" + source + "] Reached end of Stream.";
	}


	public static String genMessage_tokensRemaining(Object source, List<Token> consumed, List<Token> tokens) {
		return "Syntax Error: @" + consumed.size() + " [" + source + "] Tokens remaining after match";
	}


	public static String genMessage_unexpectedSymbol(Object source, String expected, List<Token> consumed, List<Token> tokens) {
		StringBuilder builder = new StringBuilder();
		builder.append("Syntax Error: @" + consumed.size() + " [" + source + "] Found unexpected symbol @").append(consumed.size()).append(": ");
		for(int i=0; i<Math.min(3, tokens.size()); i++) {
			builder.append('"').append(tokens.get(i).getSymbol()).append('"').append(" ");
		}
		builder.append("...  Expected: ").append('"').append(expected).append('"');
		return builder.toString();
	}




	public static String genMessage_unexpectedSymbol(Object source, List<Token> consumed, List<Token> tokens) {
		StringBuilder builder = new StringBuilder();
		builder.append("Syntax Error: @" + consumed.size() + " [" + source + "] Found unexpected symbol @").append(consumed.size()).append(": ");
		for(int i=0; i<Math.min(3, tokens.size()); i++) {
			builder.append('"').append(tokens.get(i).getSymbol()).append('"').append(" ");
		}
		builder.append("...  ");
		return builder.toString();
	}



	public static String genMessage_undefinedSymbol(Object source, String expected, List<Token> consumed, List<Token> tokens) {
		StringBuilder builder = new StringBuilder();
		builder.append("Syntax Error: @" + consumed.size() + " [" + source + "] Found undefined symbol @").append(consumed.size()).append(": ");
		for(int i=0; i<Math.min(3, tokens.size()); i++) {
			builder.append('"').append(tokens.get(i).getSymbol()).append('"').append(" ");
		}
		builder.append("...  Expected: ").append('"').append(expected).append('"');
		return builder.toString();
	}



	public static String genMessage_undefinedState(Object source) {
		return "Internal Error: [" + source + "] Undefined State.";
	}

}

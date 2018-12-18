package com.ruegnerlukas.simpleparser;

import com.ruegnerlukas.simpleparser.grammar.Token;
import com.ruegnerlukas.simpleparser.grammar.expressions.Expression;

import java.util.List;

public class ErrorMessages {


	public static String genMessage_endOfStream(Expression source) {
		return "Syntax Error: [" + source + "] Reached end of Stream.";
	}




	public static String genMessage_unexpectedSymbol(Expression source, String expected, List<Token> consumed, List<Token> tokens) {
		StringBuilder builder = new StringBuilder();
		builder.append("Syntax Error: [" + source + "] Found unexpected symbol @").append(consumed.size()).append(": ");
		for(int i=0; i<Math.min(3, tokens.size()); i++) {
			builder.append('"').append(tokens.get(i).symbol).append('"').append(" ");
		}
		builder.append("...  Expected: ").append('"').append(expected).append('"');
		return builder.toString();
	}




	public static String genMessage_unexpectedSymbol(Expression source, List<Token> consumed, List<Token> tokens) {
		StringBuilder builder = new StringBuilder();
		builder.append("Syntax Error: [" + source + "] Found unexpected symbol @").append(consumed.size()).append(": ");
		for(int i=0; i<Math.min(3, tokens.size()); i++) {
			builder.append('"').append(tokens.get(i).symbol).append('"').append(" ");
		}
		builder.append("...  ");
		return builder.toString();
	}



	public static String genMessage_undefinedSymbol(Expression source, String expected, List<Token> consumed, List<Token> tokens) {
		StringBuilder builder = new StringBuilder();
		builder.append("Syntax Error: [" + source + "] Found undefined symbol @").append(consumed.size()).append(": ");
		for(int i=0; i<Math.min(3, tokens.size()); i++) {
			builder.append('"').append(tokens.get(i).symbol).append('"').append(" ");
		}
		builder.append("...  Expected: ").append('"').append(expected).append('"');
		return builder.toString();
	}



	public static String genMessage_undefinedState(Expression source) {
		return "Internal Error: [" + source + "] Undefined State.";
	}

}

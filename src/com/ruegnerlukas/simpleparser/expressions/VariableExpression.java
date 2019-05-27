package com.ruegnerlukas.simpleparser.expressions;

import com.ruegnerlukas.simpleparser.*;
import com.ruegnerlukas.simpleparser.errors.ErrorType;
import com.ruegnerlukas.simpleparser.grammar.State;
import com.ruegnerlukas.simpleparser.trace.Trace;
import com.ruegnerlukas.simpleparser.trace.TraceElement;

public class VariableExpression extends Expression {


	public final Variable variable;




	public VariableExpression(Variable variable) {
		super(ExpressionType.VARIABLE);
		this.variable = variable;
	}




	@Override
	public boolean isOptional() {
		return false;
	}




	@Override
	public State apply(Node root, TokenStream tokenStream, Trace trace) {

		variable.value = null;

		if (!tokenStream.hasNext()) {
			trace.add(new TraceElement(this).setState(State.NO_MATCH));
			root.children.add(new ErrorNode(ErrorType.UNEXPECTED_END_OF_INPUT, tokenStream.getIndex()).setExpression(this));
			return State.NO_MATCH;

		} else {

			Token next = tokenStream.peek();

			if (isValidToken(next)) {
				variable.value = convertToValue(next);
				tokenStream.consume();
				Node node = new Node().setExpression(this);
				root.children.add(node);
				trace.add(new TraceElement(this).setState(State.MATCH));
				return State.MATCH;

			} else {
				trace.add(new TraceElement(this).setState(State.NO_MATCH));
				root.children.add(new ErrorNode(ErrorType.UNEXPECTED_SYMBOL, tokenStream.getIndex()).setExpression(this));
				return State.NO_MATCH;
			}

		}

	}




	@Override
	public State apply(Node root, CharStream charStream, boolean ignoreWhitespace, Trace trace) {

		variable.value = null;

		if (!charStream.hasNext()) {
			trace.add(new TraceElement(this).setState(State.NO_MATCH));
			root.children.add(new ErrorNode(ErrorType.UNEXPECTED_END_OF_INPUT, charStream.getIndex()).setExpression(this));
			return State.NO_MATCH;

		} else {

			if(ignoreWhitespace) {
				while(charStream.peek() == ' ') {
					charStream.consume(1);
				}
			}

			String strStream = charStream.getRemaining();
			String lastValid = null;

			if(variable.datatype == String.class) {
				if(strStream.length() >= 2 && strStream.charAt(0) == '"') {
					int indexEnd = strStream.substring(1).indexOf('"') + 1;
					lastValid = strStream.substring(0, indexEnd+1);
				}

			} else {
				StringBuilder builder = new StringBuilder();

				for(int i=0; i<strStream.length(); i++) {
					builder.append(strStream.charAt(i));
					String str = builder.toString();
					if(isValidSymbol(str)) {
						lastValid = str;
					}
				}
			}

			if(lastValid != null) {
				variable.value = convertToValue(lastValid);
				charStream.consume(lastValid);
				Node node = new Node().setExpression(this);
				root.children.add(node);
				trace.add(new TraceElement(this).setState(State.MATCH));
				return State.MATCH;

			} else {
				trace.add(new TraceElement(this).setState(State.NO_MATCH));
				root.children.add(new ErrorNode(ErrorType.UNEXPECTED_SYMBOL, charStream.getIndex()).setExpression(this));
				return State.NO_MATCH;
			}

		}

	}




	private boolean isValidToken(Token token) {
		return isValidSymbol(token.getSymbol());
	}




	private boolean isValidSymbol(String symbol) {

		if (variable.datatype == String.class) {
			return symbol.length() >= 2 && symbol.startsWith("\"") && symbol.endsWith("\"");
		}

		if (variable.datatype == Integer.class) {
			if(symbol.contains(" ")) {
				return false;
			}
			try {
				Integer.parseInt(symbol);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		}

		if (variable.datatype == Double.class) {
			if(symbol.contains(" ")) {
				return false;
			}
			try {
				Double.parseDouble(symbol);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		}

		if (variable.datatype == Boolean.class) {
			if(symbol.contains(" ")) {
				return false;
			}
			return symbol.equalsIgnoreCase("true") || symbol.equalsIgnoreCase("false");
		}

		return false;
	}




	private Object convertToValue(Token token) {
		return convertToValue(token.getSymbol());
	}




	private Object convertToValue(String symbol) {

		if (variable.datatype == String.class) {
			return symbol.substring(1, symbol.length() - 1);
		}

		if (variable.datatype == Integer.class) {
			return Integer.parseInt(symbol);
		}

		if (variable.datatype == Double.class) {
			return Double.parseDouble(symbol);
		}

		if (variable.datatype == Boolean.class) {
			return symbol.equalsIgnoreCase("true");
		}

		return null;
	}




	@Override
	public String toString() {
		if (variable.value == null) {
			return "VARIABLE:" + Integer.toHexString(this.hashCode()) + ": {" + variable.varname + ":" + variable.datatype.getSimpleName() + "}";
		} else {
			return "VARIABLE:" + Integer.toHexString(this.hashCode()) + ": {" + variable.varname + ":" + variable.datatype.getSimpleName() + "} = " + variable.value;
		}
	}


}
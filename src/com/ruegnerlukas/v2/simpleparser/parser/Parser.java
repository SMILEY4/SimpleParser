package com.ruegnerlukas.v2.simpleparser.parser;

import com.ruegnerlukas.v2.simpleparser.Node;
import com.ruegnerlukas.v2.simpleparser.Token;
import com.ruegnerlukas.v2.simpleparser.errors.ErrorMerger;
import com.ruegnerlukas.v2.simpleparser.errors.ErrorStack;
import com.ruegnerlukas.v2.simpleparser.errors.SymbolsRemainingError;
import com.ruegnerlukas.v2.simpleparser.grammar.Grammar;
import com.ruegnerlukas.v2.simpleparser.grammar.State;
import com.ruegnerlukas.v2.simpleparser.expressions.Expression;
import com.ruegnerlukas.v2.simpleparser.trace.Trace;
import com.ruegnerlukas.v2.simpleparser.errors.Error;

import java.util.ArrayList;
import java.util.List;

public class Parser {


	private Grammar grammar;

	private Node root;
	private State state;
	private Trace trace;
	private ErrorStack errorStack;

	private List<Token> tokens;



	public Parser(Grammar grammar) {
		this.grammar = grammar;
	}




	private void reset() {
		this.state = null;
		this.root = new Node();
		this.trace = new Trace();
		this.errorStack = new ErrorStack();
		this.tokens = null;
	}




	public Parser parse(List<Token> tokens) {
		reset();

		this.tokens = tokens;
		List<Token> tokensCopy = new ArrayList<>(tokens);

		Expression rootExpression = grammar.getRule(grammar.getStartingRule()).getExpression();
		this.state = rootExpression.apply(this.root, tokensCopy, this);

		if(!tokensCopy.isEmpty()) {
			String[] remaining = new String[tokensCopy.size()];
			int i=0;
			for(Token t : tokensCopy) {
				remaining[i++] = t.getSymbol();
			}
			errorStack.addError(new SymbolsRemainingError(calcErrorIndex(tokensCopy)), this, State.ERROR);
			this.state = State.ERROR;
		}

		return this;
	}




	public List<Token> getLastParsedTokens() {
		return this.tokens;
	}



	public State getState() {
		return this.state;
	}




	public Node getRoot() {
		return this.root;
	}




	public Trace getTrace() {
		return this.trace;
	}




	public ErrorStack getErrorStack() {
		return this.errorStack;
	}




	public List<Error> getPrimaryErrors() {
		return ErrorMerger.mergeErrors(this.errorStack);
	}




	public int calcErrorIndex(List<Token> procTokens) {
		return getLastParsedTokens().size() - procTokens.size();
	}

}

package com.ruegnerlukas.simpleparser.tree;

import com.ruegnerlukas.simpleparser.errors.ErrorMessages;
import com.ruegnerlukas.simpleparser.grammar.Grammar;
import com.ruegnerlukas.simpleparser.tokens.IgnorableToken;
import com.ruegnerlukas.simpleparser.grammar.Rule;
import com.ruegnerlukas.simpleparser.tokens.Token;
import com.ruegnerlukas.simpleparser.expressions.Expression;
import com.ruegnerlukas.simpleparser.expressions.Result;

import java.util.ArrayList;
import java.util.List;

public class TreeBuilder {

	private boolean traceEnabled = false;
	private List<Expression> trace = new ArrayList<>();




	public void enableTrace(boolean enable) {
		this.traceEnabled = enable;
	}




	public boolean isTraceEnabled() {
		return traceEnabled;
	}




	public List<Expression> getLastTrace() {
		return this.trace;
	}




	/**
	 * builds a tree from the given tokenlist and grammar
	 * */
	public Result build(Grammar grammar, List<Token> tokens) {
		trace.clear();

		List<Token> consumed = new ArrayList<>(tokens.size());
		List<Token> tokensCopy = new ArrayList<>(tokens.size());
		tokensCopy.addAll(tokens);

		Rule start = grammar.getRule(grammar.getStartingRule());
		Result resultStart = start.expression.apply(consumed, tokensCopy, (traceEnabled ? trace : null) );

		Node root = new RuleNode(start);
		if(resultStart.node != null) {
			root.children.add(resultStart.node);
		}

		root.eliminatePlaceholders();

		Result.State state = Result.State.MATCH;
		String message = resultStart.message;

		if(resultStart.state == Result.State.ERROR) {
			state = Result.State.ERROR;
		}
		if(resultStart.state == Result.State.MATCH && !tokensCopy.isEmpty()) {
			for(Token token : tokensCopy) {
				if( !(token instanceof IgnorableToken) ) {
					state = Result.State.ERROR;
					message = ErrorMessages.genMessage_tokensRemaining(this, consumed, tokensCopy);
				}
			}
		}

		return new Result(state, root, message, consumed.size());
	}




	/**
	 * removes chains of non-terminals that only have one child. Only removes the non-terminals given in the "rules"-array
	 * */
	public Node collapseTree(Node root, String... rules) {
		return root.collapseTree(rules);
	}




	/**
	 * removes all terminal-nodes contained in the given array
	 * */
	public Node removeTerminals(Node root, String... terminals) {
		return root.removeTerminals(terminals);
	}


}

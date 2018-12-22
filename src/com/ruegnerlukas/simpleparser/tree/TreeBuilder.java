package com.ruegnerlukas.simpleparser.tree;

import com.ruegnerlukas.simpleparser.errors.TokensRemainingError;
import com.ruegnerlukas.simpleparser.expressions.Result;
import com.ruegnerlukas.simpleparser.grammar.Grammar;
import com.ruegnerlukas.simpleparser.grammar.Rule;
import com.ruegnerlukas.simpleparser.tokens.Token;
import com.ruegnerlukas.simpleparser.tokens.TokenType;

import java.util.ArrayList;
import java.util.List;

public class TreeBuilder {


	private boolean traceEnabled = false;
	private List<TraceElement> trace = new ArrayList<>();




	/**
	 * @param enable true, to create a trace of all applied expressions and additional information while building the tree
	 * */
	public void enableTrace(boolean enable) {
		this.traceEnabled = enable;
	}



	/**
	 * @return true, when a trace of all applied expressions and additional information while building the tree is created
	 * */
	public boolean isTraceEnabled() {
		return traceEnabled;
	}




	/**
	 * @return the list of all applied expressions and additional information while building the last tree
	 * */
	public List<TraceElement> getLastTrace() {
		return this.trace;
	}




	/**
	 * builds a tree from the given list of tokens and grammar
	 * */
	public Result build(Grammar grammar, List<Token> tokens) {

		// prepare
		trace.clear();
		List<Token> consumed = new ArrayList<>(tokens.size());
		List<Token> tokensCopy = new ArrayList<>(tokens.size());
		tokensCopy.addAll(tokens);

		// build tree recursive
		Rule start = grammar.getRule(grammar.getStartingRule());
		Result resultStart = start.getExpression().apply(consumed, tokensCopy, (traceEnabled ? trace : null) );

		Node root = new RuleNode(start);
		if(resultStart.node != null) {
			root.children.add(resultStart.node);
		}

		// process finished tree
		root.eliminatePlaceholders();

		// return final result
		if(resultStart.state == Result.State.MATCH && !tokensCopy.isEmpty()) {
			for(Token token : tokensCopy) {
				if(token.getType() != TokenType.IGNORABLE ) {
					return new Result(Result.State.ERROR, null, new TokensRemainingError(this, consumed));
				}
			}

		}
		return resultStart;
	}




	/**
	 * removes chains of non-terminals that only have one child from the tree with the given node as root.
	 * Only removes the production rules with the names in the given array
	 * */
	public Node collapseTree(Node root, String... rules) {
		return root.collapseTree(rules);
	}




	/**
	 * removes all terminal-nodes specified in the given array from the tree with the given node as root
	 * */
	public Node removeTerminals(Node root, String... terminals) {
		return root.removeTerminals(terminals);
	}


}
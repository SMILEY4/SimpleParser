package com.ruegnerlukas.simpleparser.tree;

import com.ruegnerlukas.simpleparser.grammar.Grammar;
import com.ruegnerlukas.simpleparser.grammar.Rule;
import com.ruegnerlukas.simpleparser.grammar.Token;
import com.ruegnerlukas.simpleparser.grammar.expressions.Expression;
import com.ruegnerlukas.simpleparser.grammar.expressions.Result;

import java.util.ArrayList;
import java.util.List;

public class TreeBuilder {

	private boolean traceEnabled = false;
	private List<Expression> trace = new ArrayList<>();




	public void enableTrace(boolean enable) {
		this.traceEnabled = enable;
	}




	/**
	 * builds a tree from the given tokenlist and grammar
	 * */
	public Result build(Grammar grammar, List<Token> tokens) {
		trace.clear();

		List<Token> tokenCopy = new ArrayList<>(tokens.size());
		tokenCopy.addAll(tokens);

		Rule start = grammar.getRule(grammar.getStartingRule());
		Result resultStart = start.expression.apply(new ArrayList<Token>(tokenCopy.size()), tokenCopy, (traceEnabled ? trace : null) );

		Node root = new RuleNode(start);
		if(resultStart.node != null) {
			root.children.add(resultStart.node);
		}

		root.eliminateMidNodes();

		Result.State state = Result.State.SUCCESS;
		String message = resultStart.message;

		if(resultStart.state == Result.State.ERROR) {
			state = Result.State.ERROR;
		}
		if(resultStart.state == Result.State.END_OF_STREAM && !tokenCopy.isEmpty()) {
			state = Result.State.ERROR;
			message = "End of stream but tokens remaining.";
		}

		return new Result(state, root, message);
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

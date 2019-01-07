package com.ruegnerlukas.simpleparser.parser;

import com.ruegnerlukas.simpleparser.CharStream;
import com.ruegnerlukas.simpleparser.ErrorNode;
import com.ruegnerlukas.simpleparser.Node;
import com.ruegnerlukas.simpleparser.Token;
import com.ruegnerlukas.simpleparser.errors.ErrorType;
import com.ruegnerlukas.simpleparser.expressions.Expression;
import com.ruegnerlukas.simpleparser.grammar.Grammar;
import com.ruegnerlukas.simpleparser.grammar.State;
import com.ruegnerlukas.simpleparser.trace.Trace;
import com.ruegnerlukas.simpleparser.expressions.TokenExpression;

public class StringParser {


	private Grammar grammar;




	public StringParser(Grammar grammar) {
		this.grammar = grammar;
	}




	/**
	 * @param eliminateNonTerminalLeafs Node.eliminateNonTerminalLeafs
	 * @param eliminateNonRuleNodes     Node.eliminateNonRuleNodes
	 */
	public ParserResult parse(String input, boolean eliminateNonTerminalLeafs, boolean eliminateNonRuleNodes) {

		CharStream charStream = new CharStream(input);
		Trace trace = new Trace();

		Expression rootExpression = grammar.getRule(grammar.getStartingRule()).getExpression();
		Node root = new Node();
		State state = rootExpression.apply(root, charStream, trace);

		if (charStream.hasNext()) {
			while (charStream.hasNext()) {
				String strRemaining = charStream.getRemaining();
				root.children.add(new ErrorNode(ErrorType.SYMBOLS_REMAINING, charStream.getIndex()).setExpression(new TokenExpression(new Token(strRemaining))));
				charStream.consume(strRemaining.length());
			}
			state = State.ERROR;
		}

		if (state == State.MATCH) {
			return buildResultMatch(root, input, trace, eliminateNonTerminalLeafs, eliminateNonRuleNodes);
		} else {
			return buildResultError(root, input, trace, eliminateNonTerminalLeafs, eliminateNonRuleNodes);
		}

	}




	private ParserResultMatch buildResultMatch(Node rootNode, String inputString, Trace trace, boolean eliminateNonTerminalLeafs, boolean eliminateNonRuleNodes) {
		Node root = rootNode;
		if (eliminateNonRuleNodes) {
			root = new Node(root.eliminateNonRuleNodes());
		}
		if (eliminateNonTerminalLeafs) {
			root = root.eliminateNonTerminalLeafs();
		}
		return new ParserResultMatch(root, inputString, trace);
	}




	private ParserResultError buildResultError(Node rootNode, String inputString, Trace trace, boolean eliminateNonTerminalLeafs, boolean eliminateNonRuleNodes) {
		Node root = rootNode;
		if (eliminateNonRuleNodes) {
			root = new Node(root.eliminateNonRuleNodes());
		}
		if (eliminateNonTerminalLeafs) {
			root = root.eliminateNonTerminalLeafs();
		}
		return new ParserResultError(root, inputString, trace);
	}


}

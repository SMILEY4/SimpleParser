package com.ruegnerlukas.v2.simpleparser.parser;

import com.ruegnerlukas.v2.simpleparser.*;
import com.ruegnerlukas.v2.simpleparser.errors.ErrorType;
import com.ruegnerlukas.v2.simpleparser.expressions.Expression;
import com.ruegnerlukas.v2.simpleparser.expressions.TokenExpression;
import com.ruegnerlukas.v2.simpleparser.grammar.Grammar;
import com.ruegnerlukas.v2.simpleparser.grammar.State;
import com.ruegnerlukas.v2.simpleparser.trace.Trace;

public class StringParser {


	private Grammar grammar;




	public StringParser(Grammar grammar) {
		this.grammar = grammar;
	}





	public ParserResult parse(String input, boolean eliminateNonTerminalLeafs, boolean eliminateNonRuleNodes) {

		CharStream charStream = new CharStream(input);
		Trace trace = new Trace();

		Expression rootExpression = grammar.getRule(grammar.getStartingRule()).getExpression();
		Node root = new Node();
		State state = rootExpression.apply(root, charStream, trace);

		if(charStream.hasNext()) {
			while(charStream.hasNext()) {
				String strRemaining = charStream.getRemaining();
				root.children.add(new ErrorNode(ErrorType.SYMBOLS_REMAINING, charStream.getIndex()).setExpression(new TokenExpression(new Token(strRemaining))));
				charStream.consume(strRemaining.length());
			}
			state = State.ERROR;
		}

		if(state == State.MATCH) {
			return buildResultMatch(root, input, trace, eliminateNonTerminalLeafs, eliminateNonRuleNodes);
		} else {
			return buildResultError(root, input, trace, eliminateNonTerminalLeafs, eliminateNonRuleNodes);
		}

	}




	private ParserResultMatch buildResultMatch(Node rootNode, String inputString, Trace trace, boolean eliminateNonTerminalLeafs, boolean eliminateNonRuleNodes) {
		Node root = rootNode;
		if(eliminateNonRuleNodes) {
			root = new Node(root.eliminateNonRuleNodes());
		}
		if(eliminateNonTerminalLeafs) {
			root = root.eliminateNonTerminalLeafs();
		}
		return new ParserResultMatch(root, inputString, trace);
	}




	private ParserResultError buildResultError(Node rootNode, String inputString, Trace trace, boolean eliminateNonTerminalLeafs, boolean eliminateNonRuleNodes) {
		Node root = rootNode;
		if(eliminateNonRuleNodes) {
			root = new Node(root.eliminateNonRuleNodes());
		}
		if(eliminateNonTerminalLeafs) {
			root = root.eliminateNonTerminalLeafs();
		}
		return new ParserResultError(root, inputString, trace);
	}


}

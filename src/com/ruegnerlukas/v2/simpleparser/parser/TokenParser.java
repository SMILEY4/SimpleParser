package com.ruegnerlukas.v2.simpleparser.parser;

import com.ruegnerlukas.v2.simpleparser.ErrorNode;
import com.ruegnerlukas.v2.simpleparser.Node;
import com.ruegnerlukas.v2.simpleparser.Token;
import com.ruegnerlukas.v2.simpleparser.TokenStream;
import com.ruegnerlukas.v2.simpleparser.errors.ErrorType;
import com.ruegnerlukas.v2.simpleparser.expressions.Expression;
import com.ruegnerlukas.v2.simpleparser.expressions.TokenExpression;
import com.ruegnerlukas.v2.simpleparser.grammar.Grammar;
import com.ruegnerlukas.v2.simpleparser.grammar.State;
import com.ruegnerlukas.v2.simpleparser.trace.Trace;

import java.util.List;

public class TokenParser {


	private Grammar grammar;




	public TokenParser(Grammar grammar) {
		this.grammar = grammar;
	}





	public ParserResult parse(List<Token> tokens, boolean eliminateNonTerminalLeafs, boolean eliminateNonRuleNodes) {

		TokenStream tokenStream = new TokenStream(tokens);
		Trace trace = new Trace();

		Expression rootExpression = grammar.getRule(grammar.getStartingRule()).getExpression();
		Node root = new Node();
		State state = rootExpression.apply(root, tokenStream, trace);

		if(tokenStream.hasNext()) {
			while(tokenStream.hasNext()) {
				Token token = tokenStream.peek();
				root.children.add(new ErrorNode(ErrorType.SYMBOLS_REMAINING, tokenStream.getIndex()).setExpression(new TokenExpression(token)));
				tokenStream.consume();
			}
			state = State.ERROR;
		}

		if(state == State.MATCH) {
			return buildResultMatch(root, tokens, trace, eliminateNonTerminalLeafs, eliminateNonRuleNodes);
		} else {
			return buildResultError(root, tokens, trace, eliminateNonTerminalLeafs, eliminateNonRuleNodes);
		}

	}




	private ParserResultMatch buildResultMatch(Node rootNode, List<Token> inputTokens, Trace trace, boolean eliminateNonTerminalLeafs, boolean eliminateNonRuleNodes) {
		Node root = rootNode;
		if(eliminateNonRuleNodes) {
			root = new Node(root.eliminateNonRuleNodes());
		}
		if(eliminateNonTerminalLeafs) {
			root = root.eliminateNonTerminalLeafs();
		}
		return new ParserResultMatch(root, inputTokens, trace);
	}




	private ParserResultError buildResultError(Node rootNode, List<Token> inputTokens, Trace trace, boolean eliminateNonTerminalLeafs, boolean eliminateNonRuleNodes) {
		Node root = rootNode;
		if(eliminateNonRuleNodes) {
			root = new Node(root.eliminateNonRuleNodes());
		}
		if(eliminateNonTerminalLeafs) {
			root = root.eliminateNonTerminalLeafs();
		}
		return new ParserResultError(root, inputTokens, trace);
	}


}

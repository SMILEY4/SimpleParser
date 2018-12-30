package com.ruegnerlukas.tests;

import com.ruegnerlukas.simpleparser.expressions.Result;
import com.ruegnerlukas.simpleparser.grammar.Grammar;
import com.ruegnerlukas.simpleparser.systems.ExpressionProcessor;
import com.ruegnerlukas.simpleparser.systems.NodeProcessor;
import com.ruegnerlukas.simpleparser.tokens.Token;
import com.ruegnerlukas.simpleparser.tokens.Tokenizer;
import com.ruegnerlukas.simpleparser.tree.TerminalNode;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TreeTerminalsTest {


	class TestItem {
		public final String string;
		public final List<String> terminals = new ArrayList<>();


		public TestItem(String string, String... symbols) {
			this.string = string;
			for(String symbol : symbols) {
				terminals.add(symbol);
			}
		}


	}




	@Test
	public void testBoolGrammar() {

		final Grammar grammar = TestGrammarBuilder.buildBoolGrammar();

		Set<String> ignorables = new HashSet<>();
		ignorables.add(" ");
		Tokenizer tokenizer = new Tokenizer(grammar);

		// TEST
		final TestItem[] testItems = new TestItem[] {
				new TestItem("e and e or e", "e", "and", "e", "or", "e"),
				new TestItem("e and (e or e)", "e", "and", "(", "e", "or", "e", ")"),
				new TestItem("e and ((e or e) and (e and e))",
						"e", "and", "(", "(", "e", "or", "e", ")", "and", "(", "e", "and", "e", ")", ")"),
				new TestItem("e", "e"),
		};


		boolean failed = false;
		for(int i=0; i<testItems.length; i++) {
			TestItem item = testItems[i];

			List<Token> tokens = tokenizer.tokenize(item.string, ignorables, false);
			Result result = ExpressionProcessor.apply(grammar, tokens);

			List<TerminalNode> terminalNodes = NodeProcessor.collectTerminals(result.node);

			List<String> stringTerminalNodes = new ArrayList<>();
			for(TerminalNode node : terminalNodes) {
				stringTerminalNodes.add(node.token.getSymbol());
			}

			try {
				Assertions.assertIterableEquals(item.terminals, stringTerminalNodes);
				System.out.println((i+1) + ".  PASSED:   Match \"" + item.string + "\" - expected: \"" + item.terminals + "\", actual: '" + stringTerminalNodes + "\"");
			} catch(AssertionError e) {
				System.err.println((i+1) + ".  FAILED:   Match \"" + item.string + "\" - expected: \"" + item.terminals + "\", actual: '" + stringTerminalNodes + "\"");
				failed = true;
			}
		}

		if(failed) {
			Assertions.fail();
		}

	}



	@Test
	public void testAGrammar() {

		final Grammar grammar = TestGrammarBuilder.buildAGrammar();
		Tokenizer tokenizer = new Tokenizer(grammar);

		// TEST
		final TestItem[] testItems = new TestItem[] {
				new TestItem("aa", "a", "a"),
				new TestItem("aaaa", "a", "aa", "a"),
		};


		boolean failed = false;
		for(int i=0; i<testItems.length; i++) {
			TestItem item = testItems[i];

			List<Token> tokens = tokenizer.tokenize(item.string, new HashSet<>(), false);
			Result result = ExpressionProcessor.apply(grammar, tokens);

			List<TerminalNode> terminalNodes = NodeProcessor.collectTerminals(result.node);

			List<String> stringTerminalNodes = new ArrayList<>();
			for(TerminalNode node : terminalNodes) {
				stringTerminalNodes.add(node.token.getSymbol());
			}

			try {
				Assertions.assertIterableEquals(item.terminals, stringTerminalNodes);
				System.out.println((i+1) + ".  PASSED:   Match \"" + item.string + "\" - expected: \"" + item.terminals + "\", actual: '" + stringTerminalNodes + "\"");
			} catch(AssertionError e) {
				System.err.println((i+1) + ".  FAILED:   Match \"" + item.string + "\" - expected: \"" + item.terminals + "\", actual: '" + stringTerminalNodes + "\"");
				failed = true;
			}
		}

		if(failed) {
			Assertions.fail();
		}

	}


}

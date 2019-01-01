package com.ruegnerlukas.v1.tests;

import com.ruegnerlukas.v1.simpleparser.grammar.Grammar;
import com.ruegnerlukas.v1.simpleparser.systems.NodeProcessor;
import com.ruegnerlukas.v1.simpleparser.tokens.Token;
import com.ruegnerlukas.v1.simpleparser.tokens.Tokenizer;
import com.ruegnerlukas.v1.simpleparser.tree.Node;
import com.ruegnerlukas.v1.simpleparser.tree.TerminalNode;
import com.ruegnerlukas.v1.simpleparser.systems.ExpressionProcessor;
import com.ruegnerlukas.v1.simpleparser.systems.RecommendationProcessor;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RecommendationsTest {


	class TestItem {
		public final String string;
		public final Set<String> possibles = new HashSet<>();


		public TestItem(String string, String... symbols) {
			this.string = string;
			for(String symbol : symbols) {
				possibles.add(symbol);
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
				new TestItem("e", "and", "or"),
				new TestItem("e and", "e", "("),
				new TestItem("e and e", "and", "or"),
				new TestItem("e and (", "e", "("),
				new TestItem("e and (e or (", "e", "("),
				new TestItem("e and (e or (e ", "and", "or", ")"),
				new TestItem("e and (e or e)", "and", "or"),
				new TestItem("e and ((e or e)", "and", "or", ")"),
		};


		boolean failed = false;
		for(int i=0; i<testItems.length; i++) {
			TestItem item = testItems[i];

			List<Token> tokens = tokenizer.tokenize(item.string, ignorables, false);
			Node root = ExpressionProcessor.apply(grammar, tokens);

			List<TerminalNode> terminalNodes = NodeProcessor.collectTerminals(root);

			Set<Token> possibleTokens = new HashSet<>();
			RecommendationProcessor.collectPossibleTokens(terminalNodes.get(terminalNodes.size()-1), possibleTokens);

			Set<String> stringPossibles = new HashSet<>();
			for(Token t : possibleTokens) {
				stringPossibles.add(t.getSymbol());
			}

			try {
				Assertions.assertIterableEquals(item.possibles, stringPossibles);
				System.out.println((i+1) + ".  PASSED:   Match \"" + item.string + "\" - expected: \"" + item.possibles + "\", actual: '" + stringPossibles + "\"");
			} catch(AssertionError e) {
				System.err.println((i+1) + ".  FAILED:   Match \"" + item.string + "\" - expected: \"" + item.possibles + "\", actual: '" + stringPossibles + "\"");
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
				new TestItem("a", "a", "aa"),
				new TestItem("aaa", "a"),
		};


		boolean failed = false;
		for(int i=0; i<testItems.length; i++) {
			TestItem item = testItems[i];

			List<Token> tokens = tokenizer.tokenize(item.string, null, false);
			Node root  = ExpressionProcessor.apply(grammar, tokens);

			List<TerminalNode> terminalNodes = NodeProcessor.collectTerminals(root);

			Set<Token> possibleTokens = new HashSet<>();
			RecommendationProcessor.collectPossibleTokens(terminalNodes.get(terminalNodes.size()-1), possibleTokens);

			List<String> stringPossibles = new ArrayList<>();
			for(Token t : possibleTokens) {
				stringPossibles.add(t.getSymbol());
			}

			try {
				Assertions.assertIterableEquals(item.possibles, stringPossibles);
				System.out.println((i+1) + ".  PASSED:   Match \"" + item.string + "\" - expected: \"" + item.possibles + "\", actual: '" + stringPossibles + "\"");
			} catch(AssertionError e) {
				System.err.println((i+1) + ".  FAILED:   Match \"" + item.string + "\" - expected: \"" + item.possibles + "\", actual: '" + stringPossibles + "\"");
				failed = true;
			}
		}

		if(failed) {
			Assertions.fail();
		}

	}


}

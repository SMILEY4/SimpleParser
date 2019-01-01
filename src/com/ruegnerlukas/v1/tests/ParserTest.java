package com.ruegnerlukas.v1.tests;

import com.ruegnerlukas.v1.simpleparser.errors.Error;
import com.ruegnerlukas.v1.simpleparser.grammar.Grammar;
import com.ruegnerlukas.v1.simpleparser.tokens.Token;
import com.ruegnerlukas.v1.simpleparser.tokens.Tokenizer;
import com.ruegnerlukas.v1.simpleparser.tree.Node;
import com.ruegnerlukas.v1.simpleparser.expressions.Result;
import com.ruegnerlukas.v1.simpleparser.systems.ExpressionProcessor;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {


	class TestItem {
		public final String string;
		public final Result.State state;
		public final Error error;

		public TestItem(String string, Result.State state) {
			this(string, state, null);
		}

		public TestItem(String string, Result.State state, Error error) {
			this.string = string;
			this.state = state;
			this.error = error;
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
				new TestItem("e and e or e", Result.State.MATCH),
				new TestItem("e and (e or e)", Result.State.MATCH),
				new TestItem("e and ((e or e) and (e and e))", Result.State.MATCH),
				new TestItem("e", Result.State.MATCH),
				new TestItem("e and", Result.State.ERROR, new Error(Error.Type.UNEXPECTED_END_OF_INPUT, 2, 2)),
				new TestItem("or e", Result.State.ERROR, new Error(Error.Type.UNEXPECTED_SYMBOL, 0, 0, new HashSet<>(Arrays.asList(Token.token("e"), Token.token("("))), Token.token("or"))),
				new TestItem("e or and", Result.State.ERROR, new Error(Error.Type.UNEXPECTED_SYMBOL, 2, 2, new HashSet<>(Arrays.asList(Token.token("e"), Token.token("("))), Token.token("and"))),
				new TestItem("e and e e", Result.State.ERROR, new Error(Error.Type.UNEXPECTED_SYMBOL, 3, 3)),
				new TestItem("e and (e or e", Result.State.ERROR, new Error(Error.Type.UNEXPECTED_END_OF_INPUT, 6, 6)),
				new TestItem("e and (e or e))", Result.State.ERROR, new Error(Error.Type.UNEXPECTED_SYMBOL, 7, 7)),
				new TestItem("x and e or e", Result.State.ERROR, new Error(Error.Type.ILLEGAL_CHARACTER, 0, 0, new HashSet<>(Arrays.asList(Token.token("e"), Token.token("("))), Token.token("x"))),
				new TestItem("e and (x or e)", Result.State.ERROR, new Error(Error.Type.ILLEGAL_CHARACTER, 3, 3, new HashSet<>(Arrays.asList(Token.token("e"), Token.token("("))), Token.token("x"))),
				new TestItem("e and [e or e)", Result.State.ERROR, new Error(Error.Type.ILLEGAL_CHARACTER, 2, 2, new HashSet<>(Collections.singletonList(Token.token("("))), Token.token("["))),
				new TestItem("e and ((x or e) and (e and x))", Result.State.ERROR, new Error(Error.Type.ILLEGAL_CHARACTER, 4, 4)),
		};

		boolean failed = false;
		for(int i=0; i<testItems.length; i++) {
			TestItem item = testItems[i];

			List<Token> tokens = tokenizer.tokenize(item.string, ignorables, false);

			Node root = ExpressionProcessor.apply(grammar, tokens);
			Result.State state = ExpressionProcessor.state;
			Error error = ExpressionProcessor.errors.isEmpty() ? null : ExpressionProcessor.errors.get(ExpressionProcessor.errors.size()-1);

			try {
				assertEquals(state, item.state);
				System.out.println((i+1) + "  PASSED:   Match \"" + item.string + "\" - expected: \"" + item.state + "\",      actual: '" + state + "\"");
			} catch(AssertionError e) {
				System.err.println((i+1) + "  FAILED:   Match \"" + item.string + "\" - expected: \"" + item.state + "\",      actual: '" + state + "\"");
				failed = true;
			}

			if(item.state == Result.State.ERROR) {
				try {

					assertEquals(state, item.state);
					assertEquals(error.msg, item.error.msg);
					assertEquals(error.indexStart, item.error.indexStart);
					assertEquals(error.indexEnd, item.error.indexEnd);

					Error.toStringIncludeExpectedActual = false;
					System.out.println((i+1) + ".1  PASSED: " + "expected: " + item.error + ",      " + "actual: '" + error);
				} catch(AssertionError e) {
					Error.toStringIncludeExpectedActual = false;
					System.out.println((i+1) + ".1  FAILED: " + "expected: " + item.error + ",      " + "actual: '" + error);
					failed = true;
				}


				if(item.error.msg == Error.Type.UNEXPECTED_SYMBOL || item.error.msg == Error.Type.ILLEGAL_CHARACTER) {

					Set<String> set0 = new HashSet<>();
					for(Token t : error.expected) {
						set0.add(t.getSymbol());
					}

					Set<String> set1 = new HashSet<>();
					for(Token t : item.error.expected) {
						set1.add(t.getSymbol());
					}

					try {

						assertEquals(error.actual.getSymbol(), item.error.actual.getSymbol());
						Assertions.assertIterableEquals(set0, set1);

						Error.toStringIncludeExpectedActual = true;
						System.out.println((i+1) + ".2  PASSED: " + "expected: " + item.error + ",      " + "actual: '" + error);
					} catch(AssertionError e) {
						Error.toStringIncludeExpectedActual = true;
						System.out.println((i+1) + ".2  FAILED: " + "expected: " + item.error + ",      " + "actual: '" + error);
						failed = true;
					}




				}

			}

			System.out.println();

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
				new TestItem("a", Result.State.ERROR),
				new TestItem("aa", Result.State.MATCH),
				new TestItem("aaa", Result.State.ERROR),
				new TestItem("aaaa", Result.State.MATCH),
				new TestItem("aaaaa", Result.State.ERROR),
				new TestItem("aaaaaa", Result.State.ERROR),
				new TestItem("axa", Result.State.ERROR),
				new TestItem("axaa", Result.State.ERROR),
		};

		boolean failed = false;
		for(int i=0; i<testItems.length; i++) {
			TestItem item = testItems[i];

			List<Token> tokens = tokenizer.tokenize(item.string, new HashSet<>(), false);

			Node root = ExpressionProcessor.apply(grammar, tokens);
			Result.State state = ExpressionProcessor.state;
			Error error = ExpressionProcessor.errors.isEmpty() ? null : ExpressionProcessor.errors.get(ExpressionProcessor.errors.size()-1);

			try {
				assertEquals(state, item.state, "Match " + item.string);
				System.out.println((i+1) + "  PASSED:   Match \"" + item.string + "\" - expected: \"" + item.state + "\", actual: '" + state + "\"");
			} catch(AssertionError e) {
				System.err.println((i+1) + "  FAILED:   Match \"" + item.string + "\" - expected: \"" + item.state + "\", actual: '" + state + "\"");
				failed = true;
			}

			if(item.state == Result.State.ERROR) {
				try {

					assertEquals(state, item.state);
					assertEquals(error, item.error);
					assertEquals(error.indexStart, item.error.indexStart);
					assertEquals(error.indexEnd, item.error.indexEnd);

					System.out.println((i+1) + ".2  PASSED: "
							+ "expected: " + item.error + "(" + item.error.indexStart + "," + item.error.indexEnd + ")" + ", "
							+ "actual: '" + error + "(" + error.indexStart + "," + error.indexEnd + ")");
				} catch(Exception e) {
					System.out.println((i+1) + ".2  FAILED: "
							+ "expected: " + item.error + "(" + item.error.indexStart + "," + item.error.indexEnd + ")" + ", "
							+ "actual: '" + error + "(" + error.indexStart + "," + error.indexEnd + ")");
					failed = true;
				}
			}

			System.out.println();

		}


		if(failed) {
			Assertions.fail();
		}

	}



}
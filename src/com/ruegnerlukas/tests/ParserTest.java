package com.ruegnerlukas.tests;

import com.ruegnerlukas.simpleparser.expressions.Result;
import com.ruegnerlukas.simpleparser.grammar.Grammar;
import com.ruegnerlukas.simpleparser.grammar.GrammarBuilder;
import com.ruegnerlukas.simpleparser.systems.ExpressionProcessor;
import com.ruegnerlukas.simpleparser.tokens.Token;
import com.ruegnerlukas.simpleparser.tokens.Tokenizer;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {

	@Test
	public void testBoolGrammar() {

		class TestItem {
			public final String string;
			public final Result.State state;

			public TestItem(String string, Result.State state) {
				this.string = string;
				this.state = state;
			}

		}


		final Grammar grammar = buildBoolGrammar();
		final TestItem[] testItems = new TestItem[] {
				new TestItem("e and e or e", Result.State.MATCH),
				new TestItem("e and (e or e)", Result.State.MATCH),
				new TestItem("e and ((e or e) and (e and e))", Result.State.MATCH),
				new TestItem("e", Result.State.MATCH),
				new TestItem("e and", Result.State.ERROR),
				new TestItem("or e", Result.State.ERROR),
				new TestItem("e and e e", Result.State.ERROR),
				new TestItem("e and (e or e", Result.State.ERROR),
				new TestItem("e and (e or e))", Result.State.ERROR),
				new TestItem("x and e or e", Result.State.ERROR),
				new TestItem("e and (x or e)", Result.State.ERROR),
				new TestItem("e and ((x or e) and (e and x))", Result.State.ERROR),
		};

		Set<String> ignorables = new HashSet<>();
		ignorables.add(" ");

		Tokenizer tokenizer = new Tokenizer(grammar);

		boolean failed = false;
		for(TestItem item : testItems) {

			List<Token> tokens = tokenizer.tokenize(item.string, ignorables, false);
			Result result = ExpressionProcessor.apply(grammar, tokens);

			try {
				assertEquals(result.state, item.state, "Match " + item.string);
				System.out.println("PASSED:   Match \"" + item.string + "\" - expected: \"" + item.state + "\", actual: '" + result.state + "\"");
			} catch(AssertionError e) {
				System.err.println("FAILED:   Match \"" + item.string + "\" - expected: \"" + item.state + "\", actual: '" + result.state + "\"");
				failed = true;
			}
		}

		if(failed) {
			Assertions.fail();
		}
	}



	@Test
	public void testAGrammar() {

		class TestItem {
			public final String string;
			public final Result.State state;

			public TestItem(String string, Result.State state) {
				this.string = string;
				this.state = state;
			}

		}

		final Grammar grammar = buildAGrammar();
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

		Tokenizer tokenizer = new Tokenizer(grammar);

		boolean failed = false;
		for(TestItem item : testItems) {

			List<Token> tokens = tokenizer.tokenize(item.string, new HashSet<>(), false);
			Result result = ExpressionProcessor.apply(grammar, tokens);


			try {
				assertEquals(result.state, item.state, "Match " + item.string);
				System.out.println("PASSED:   Match \"" + item.string + "\" - expected: \"" + item.state + "\", actual: '" + result.state + "\"");
			} catch(AssertionError e) {
				System.err.println("FAILED:   Match \"" + item.string + "\" - expected: \"" + item.state + "\", actual: '" + result.state + "\"");
				failed = true;
			}
		}


		if(failed) {
			Assertions.fail();
		}

	}




	private Grammar buildBoolGrammar() {

		// OR_EXPRESSION 	-> AND_EXPRESSION {"or" AND_EXPRESSION}
		// AND_EXPRESSION 	-> COMPONENT {"and" COMPONENT}
		// COMPONENT 		-> STATEMENT | ( "(" EXPRESSION ")"
		// STATEMENT		-> "expr"

		GrammarBuilder gb = new GrammarBuilder();

		// OR_EXPRESSION 	-> AND_EXPRESSION {"or" AND_EXPRESSION}
		gb.defineRootNonTerminal("OR_EXPRESSION",
				gb.sequence(
						gb.nonTerminal("AND_EXPRESSION"),
						gb.zeroOrMore(
								gb.sequence(
										gb.terminal("or"),
										gb.nonTerminal("AND_EXPRESSION")
								)
						)
				)
		);

		// AND_EXPRESSION 	-> COMPONENT {"and" COMPONENT}
		gb.defineNonTerminal("AND_EXPRESSION",
				gb.sequence(
						gb.nonTerminal("COMPONENT"),
						gb.zeroOrMore(
								gb.sequence(
										gb.terminal("and"),
										gb.nonTerminal("COMPONENT")
								)
						)
				)
		);

		// COMPONENT 		-> STATEMENT | ( "(" EXPRESSION ")"
		gb.defineNonTerminal("COMPONENT",
				gb.alternative(
						gb.nonTerminal("STATEMENT"),
						gb.sequence(
								gb.terminal("("),
								gb.nonTerminal("OR_EXPRESSION"),
								gb.terminal(")")
						)
				)
		);

		// STATEMENT	-> "expr"
		gb.defineNonTerminal("STATEMENT", gb.terminal("e"));

		return gb.get();
	}




	private Grammar buildAGrammar() {

		// G -> "a" ["aa"] "a"

		GrammarBuilder gb = new GrammarBuilder();

		gb.defineRootNonTerminal("G",
				gb.sequence(
						gb.terminal("a"),
						gb.optional(
								gb.terminal("aa")
						),
						gb.terminal("a")
				)
		);

		return gb.get();
	}


}

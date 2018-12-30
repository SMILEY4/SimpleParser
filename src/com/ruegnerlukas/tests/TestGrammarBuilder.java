package com.ruegnerlukas.tests;

import com.ruegnerlukas.simpleparser.grammar.Grammar;
import com.ruegnerlukas.simpleparser.grammar.GrammarBuilder;

public class TestGrammarBuilder {


	public static Grammar buildBoolGrammar() {

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




	public static Grammar buildAGrammar() {

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

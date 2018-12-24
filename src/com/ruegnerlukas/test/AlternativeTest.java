package com.ruegnerlukas.test;

import com.ruegnerlukas.simpleparser.expressions.Result;
import com.ruegnerlukas.simpleparser.grammar.Grammar;
import com.ruegnerlukas.simpleparser.grammar.GrammarBuilder;
import com.ruegnerlukas.simpleparser.tokens.Token;
import com.ruegnerlukas.simpleparser.tokens.Tokenizer;
import com.ruegnerlukas.simpleparser.tree.TreeBuilder;

import java.util.List;

public class AlternativeTest {


	public static void main(String[] args) {

		GrammarBuilder gb = new GrammarBuilder();

//		gb.defineRootNonTerminal("X",
//				gb.sequence(
//						gb.terminal("x"),
//						gb.alternative(
//								gb.terminal("a"),
//								gb.terminal("b"),
//								gb.terminal("c")
//						),
//						gb.terminal("x")
//				)
//		);

//		gb.defineRootNonTerminal("X",
//				gb.sequence(
//						gb.terminal("x"),
//						gb.alternative(
//								gb.optional(gb.terminal("a")),
//								gb.optional(gb.terminal("b")),
//								gb.optional(gb.terminal("c"))
//						),
//						gb.terminal("x")
//				)
//		);

//		gb.defineRootNonTerminal("X",
//				gb.sequence(
//						gb.terminal("x"),
//						gb.alternative(
//								gb.optional(gb.terminal("a")),
//								gb.optional(gb.terminal("b")),
//								gb.optional(gb.terminal("c")),
//								gb.terminal("y")
//						),
//						gb.terminal("x")
//				)
//		);

//		gb.defineRootNonTerminal("X",
//				gb.sequence(
//						gb.terminal("x"),
//						gb.alternative(
//								gb.terminal("y"),
//								gb.optional(gb.terminal("a")),
//								gb.optional(gb.terminal("b")),
//								gb.optional(gb.terminal("c"))
//						),
//						gb.terminal("x")
//				)
//		);

		gb.defineRootNonTerminal("X",
				gb.sequence(
						gb.terminal("x"),
						gb.alternative(
								gb.terminal("a"),
								gb.optional(gb.terminal("b")),
								gb.optional(gb.terminal("c")),
								gb.terminal("y")
						),
						gb.terminal("x")
				)
		);

		Grammar g = gb.get();

		String[] testStrings = new String[]{
				"xax", "xbx", "xcx", "xyx", "xx", "xxx"
		};

		for(String str : testStrings) {
			System.out.println(str + " -> " + match(g, str));
		}

	}


	static Result.State match(Grammar g, String input) {

		Tokenizer tokenizer = new Tokenizer(g);
		List<Token> tokens = tokenizer.tokenize(input);

		TreeBuilder treeBuilder = new TreeBuilder();
		Result result = treeBuilder.build(g, tokens);

		return result.state;
	}

}

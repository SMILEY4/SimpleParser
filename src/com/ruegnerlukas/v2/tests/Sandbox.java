package com.ruegnerlukas.v2.tests;

import com.ruegnerlukas.v2.dotGraph.DotGrammarBuilder;
import com.ruegnerlukas.v2.dotGraph.DotTreeBuilder;
import com.ruegnerlukas.v2.simpleparser.Token;
import com.ruegnerlukas.v2.simpleparser.grammar.Grammar;
import com.ruegnerlukas.v2.simpleparser.parser.Parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Sandbox {


	public static void main(String[] args) {

		// build grammar
		Grammar grammar = TestGrammarBuilder.buildBoolGrammar();
		String dotGrammar = DotGrammarBuilder.build(grammar);
		System.out.println(dotGrammar);

		// e and (e or e)
		List<Token> tokens = new ArrayList<>(Arrays.asList(
				new Token("e"),
				new Token("and"),
				new Token("("),
				new Token("e"),
				new Token("or"),
				new Token("e"),
				new Token(")")
		));


		Parser parser = new Parser(grammar).parse(tokens);

		System.out.println("============");
		System.out.println(" -> " + parser.getState());
		System.out.println("============");
		System.out.println(DotTreeBuilder.build(parser.getRoot()));
		System.out.println("============");



	}

}

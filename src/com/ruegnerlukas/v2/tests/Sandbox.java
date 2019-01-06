package com.ruegnerlukas.v2.tests;

import com.ruegnerlukas.v2.dotGraph.DotGrammarBuilder;
import com.ruegnerlukas.v2.simpleparser.Token;
import com.ruegnerlukas.v2.simpleparser.grammar.Grammar;
import com.ruegnerlukas.v2.simpleparser.parser.TokenParser;
import com.ruegnerlukas.v2.simpleparser.parser.ParserResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Sandbox {


	public static void main(String[] args) {

		// build grammar
		Grammar grammar = TestGrammarBuilder.buildBoolGrammar();
		String dotGrammar = DotGrammarBuilder.build(grammar, null);
		System.out.println(dotGrammar);

		// e and (e or e)
//		List<Token> tokens = new ArrayList<>(Arrays.asList(
//				new Token("e"),
//				new Token("and"),
//				new Token("("),
//				new Token("e"),
//				new Token("or"),
//				new Token("e"),
//				new Token(")")
//		));

		List<Token> tokens = new ArrayList<>(Arrays.asList(
				new Token("e"),
				new Token("and"),
				new Token("(")
		));


		TokenParser parser = new TokenParser(grammar);
		ParserResult result = parser.parse(tokens, true, true);


//		System.out.println("============");
//		System.out.println(" -> " + parser.getState());
//		System.out.println("============");
//		System.out.println(DotTreeBuilder.build(parser.getRoot()));
//		System.out.println("============");
//		for(TraceElement e : parser.getTrace().getElements()) {
//			System.out.println(e.expression + "  -> " + e.state);
//		}
//		System.out.println("============");
//		System.out.println(DotGrammarBuilder.build(grammar, parser.getTrace()));


	}

}

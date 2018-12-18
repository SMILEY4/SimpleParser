package com.ruegnerlukas.test;

import com.ruegnerlukas.simpleparser.grammar.Grammar;
import com.ruegnerlukas.simpleparser.grammar.GrammarBuilder;
import com.ruegnerlukas.simpleparser.grammar.Token;
import com.ruegnerlukas.simpleparser.grammar.expressions.Expression;
import com.ruegnerlukas.simpleparser.grammar.expressions.Result;
import com.ruegnerlukas.simpleparser.tokenizer.Tokenizer;
import com.ruegnerlukas.simpleparser.tree.Node;
import com.ruegnerlukas.simpleparser.tree.TreeBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FilterTest {


	public static void main(String[] args) throws IOException {

		File file = new File("src/test3.txt");
		byte[] bytes = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
		String source = new String(bytes);

		System.out.println("==== SOURCE ====");
		System.out.println(source);
		System.out.println("================ \n");

		String[] testStrings = source.split(System.lineSeparator());

		/*
		OR_EXPRESSION 	-> AND_EXPRESSION {"or" AND_EXPRESSION}
		AND_EXPRESSION 	-> COMPONENT {"and" COMPONENT}
		COMPONENT 		-> STATEMENT | ( "(" OR_EXPRESSION ")"
		STATEMENT		-> "expr"

		TOKENS: [TOKEN:'expr', TOKEN:'or', TOKEN:'(', UNDEFINED:'x ', TOKEN:'and', TOKEN:'expr', TOKEN:')']

		*/



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
		gb.defineNonTerminal("STATEMENT", gb.terminal("expr"));


		Grammar grammar = gb.get();

		System.out.println("EXPRESSION-GRAPH:");
		System.out.println(grammar.createDotGraph());
		System.out.println();

		test(grammar, "expr or (expr and expr");


	}


	static void test(Grammar grammar, String testString) {
		System.out.println("INPUT: " + testString);

		Tokenizer tokenizer = new Tokenizer(testString, grammar);
		List<Token> tokens = tokenizer.tokenize(true);
		System.out.println("TOKENS: " + tokens);

		System.out.println("TREE: ");
		TreeBuilder treeBuilder = new TreeBuilder();
		treeBuilder.enableTrace(true);
		Result result = treeBuilder.build(grammar, tokens);
		System.out.println("RESULT: " + result.state + ": " + (result.message.length() == 0 ? "" : result.message) );
		Node root = result.node;
		System.out.println(root.createDotTree());

		System.out.println("TREE COLLAPSED: ");
		root = treeBuilder.removeTerminals(root, "and", "or", "(", ")");
		root = treeBuilder.collapseTree(root, "OR_EXPRESSION", "AND_EXPRESSION", "COMPONENT");
		System.out.println(root.createDotTree());

		System.out.println("TRACE");
		for(Expression e : treeBuilder.getLastTrace()) {
			System.out.println(e);
		}

		System.out.println();
		System.out.println();
	}

}

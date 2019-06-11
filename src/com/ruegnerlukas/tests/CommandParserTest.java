package com.ruegnerlukas.tests;

import com.ruegnerlukas.dotGraph.DotGrammarBuilder;
import com.ruegnerlukas.dotGraph.DotTreeBuilder;
import com.ruegnerlukas.simpleparser.Node;
import com.ruegnerlukas.simpleparser.grammar.Grammar;
import com.ruegnerlukas.simpleparser.grammar.GrammarBuilder;
import com.ruegnerlukas.simpleparser.parser.ParserResult;
import com.ruegnerlukas.simpleparser.parser.ParserResultError;
import com.ruegnerlukas.simpleparser.parser.ParserResultMatch;
import com.ruegnerlukas.simpleparser.parser.StringParser;

import java.util.List;
import java.util.Scanner;

public class CommandParserTest {


	public static void main(String[] args) {

		Grammar grammar = build();

		System.out.println("GRAMMAR:");
		System.out.println(DotGrammarBuilder.build(grammar, null));
		System.out.println();
		System.out.println();

//		TokenParser parser = new TokenParser(grammar);
//
//
//		List<Token> tokenList = new ArrayList<>(Arrays.asList(
//				new Token("project rename "),
//				new Token("153.2"),
//				new Token(" --use-logic")));
//
//		ParserResult result = parser.parse(tokenList, true, true);
//
//		if (result.failed()) {
//			System.out.println("Unknown command '" + tokenList + "'");
//			ParserResultError resultError = (ParserResultError) result;
//			for (List<Node> nodes : resultError.getResultList()) {
//				for (Node node : nodes) {
//					System.out.print(node.toString() + " ");
//				}
//				System.out.println();
//			}
//			System.out.println();
//
//			System.out.println(DotGrammarBuilder.build(grammar, resultError.getTrace()));
//
//
//		} else {
//			System.out.println("Command successful");
//			ParserResultMatch resultMatch = (ParserResultMatch) result;
//			System.out.println(DotTreeBuilder.build(resultMatch.getRoot()));
//
//		}



		StringParser parser = new StringParser(grammar);


		Scanner scanner = new Scanner(System.in);
		while (true) {
			String in = scanner.nextLine();
			System.out.println("INPUT: " + in);

			long ts = System.currentTimeMillis();
			ParserResult result = parser.parse(in, true, true, true);
			long te = System.currentTimeMillis();
			System.out.println("parsing took " + (te - ts) + "ms");

			if (result.failed()) {
				System.out.println("Unknown command '" + in + "'");
				ParserResultError resultError = (ParserResultError) result;
				for (List<Node> bucket : resultError.getResultList()) {
					System.out.println("bucket");
					for (Node node : bucket) {
						System.out.println("  " + node.toString());
					}
				}
				System.out.println();

				System.out.println(DotGrammarBuilder.build(grammar, resultError.getTrace()));


			} else {
				System.out.println("Command successful");
				ParserResultMatch resultMatch = (ParserResultMatch) result;
				System.out.println(DotTreeBuilder.build(resultMatch.getRoot()));

			}


		}


	}




	private static Grammar build() {

		/*
		GRAMMAR = CMD_HELP | CMD_PROJECT_RENAME | ...
		HELP = "help"
		EXIT = "exit"
		PROJECT_RENAME = "project rename" <name> ["-l" | "--logic"]
		PROJECT_LOCK = "project lock" <locked> ["-l" | "--logic"]
		 */

		GrammarBuilder builder = new GrammarBuilder();

		builder.defineRootNonTerminal("COMMANDS",
				builder.alternative(
						builder.nonTerminal("HELP"),
						builder.nonTerminal("EXIT"),
						builder.nonTerminal("PROJECT_RENAME"),
						builder.nonTerminal("PROJECT_LOCK")));


		builder.defineNonTerminal("HELP",
				builder.terminal("help"));


		builder.defineNonTerminal("EXIT",
				builder.terminal("exit"));


		builder.defineNonTerminal("PROJECT_RENAME",
				builder.sequence(
						builder.terminal("project"),
						builder.terminal("rename"),
						builder.variable("name", String.class),
						builder.optional(
								builder.alternative("-l", "--use-logic")
						)
				)
		);

		builder.defineNonTerminal("PROJECT_LOCK",
				builder.sequence(
						builder.terminal("project"),
						builder.terminal("lock-attributes"),
						builder.variable("locked", Boolean.class),
						builder.optional(
								builder.alternative("-l", "--use-logic")
						)
				));


		return builder.get();
	}


}


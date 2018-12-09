package com.ruegnerlukas.test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.ruegnerlukas.simpleparser.grammar.Atom;
import com.ruegnerlukas.simpleparser.grammar.Grammar;
import com.ruegnerlukas.simpleparser.grammar.ruleops.AltOp;
import com.ruegnerlukas.simpleparser.grammar.ruleops.AtomOp;
import com.ruegnerlukas.simpleparser.grammar.ruleops.ConcatOp;
import com.ruegnerlukas.simpleparser.grammar.ruleops.RepOp;
import com.ruegnerlukas.simpleparser.grammar.ruleops.RuleOp;
import com.ruegnerlukas.simpleparser.tokenizer.Tokenizer;
import com.ruegnerlukas.simpleparser.tree.Node;
import com.ruegnerlukas.simpleparser.tree.TreeBuilder;


public class TestSimpleParser {

	public static void main(String[] args) throws IOException {
		
		File file = new File("src/test2.txt");
		byte[] bytes = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
		String source = new String(bytes);
		
		System.out.println("==== SOURCE ====");
		System.out.println(source);
		System.out.println("================ \n");

		String[] testStrings = source.split(System.lineSeparator());

		
		/*

		EXPRESSION 	-> (EXPRESSION ADD_OP TERM) | TERM
		TERM		-> (TERM MULT_OP FACTOR) | FACTOR
		FACTOR		-> ( "(" EXPRESSION ")" ) | NUMBER
		ADD_OP		-> "+" | "-"
		MULT_OP		-> "*" | "/"
		NUMBER		-> (DIGIT NUMBER) DIGIT
		DIGIT		-> "0" | "1" | ... | "9"
		-> !! LEFT_RECURSIVE @EXPRESSION !!
	
	
		EXPRESSION 	-> TERM {ADD_OP TERM}
		TERM		-> FACTOR {MULT_OP FACTOR}
		FACTOR 		-> ( "(" EXPRESSION ")" ) | NUMBER
		ADD_OP		-> "+" | "-"
		MULT_OP		-> "*" | "/"
		NUMBER		-> "0" | (NZ_DIGIT {"0" | NZ_DIGIT} )
		NZ_DIGIT	-> "1" | "2" | ... | "9"

	
		*/
		
		
		Grammar grammar = new Grammar();
		grammar.predefineRules("EXPRESSION", "TERM", "FACTOR", "ADD_OP", "MULT_OP", "NUMBER", "NZ_DIGIT");
		grammar.setStartingRule("EXPRESSION");
		grammar.predefineAtoms("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "+", "-", "*", "/", "(", ")");
		
//		EXPRESSION 	-> TERM {ADD_OP TERM}
		grammar.defineRule("EXPRESSION",
				new ConcatOp(
						new RuleOp(grammar.getRule("TERM")),
						new RepOp(
								new ConcatOp(
										new RuleOp(grammar.getRule("ADD_OP")),
										new RuleOp(grammar.getRule("TERM"))
										)
								)
						)
				);
		
		
//		TERM		-> FACTOR {MULT_OP FACTOR}
		grammar.defineRule("TERM",
				new ConcatOp(
						new RuleOp(grammar.getRule("FACTOR")),
						new RepOp(
								new ConcatOp(
										new RuleOp(grammar.getRule("MULT_OP")),
										new RuleOp(grammar.getRule("FACTOR"))
										)
								)
						)
				);
		
		
//		FACTOR 		-> ( "(" EXPRESSION ")" ) | NUMBER
		grammar.defineRule("FACTOR",
					new AltOp(
							new ConcatOp(
									new AtomOp(grammar.getAtom("(")),
									new RuleOp(grammar.getRule("EXPRESSION")),
									new AtomOp(grammar.getAtom(")"))
									),
							new RuleOp(grammar.getRule("NUMBER"))
							)
				);
		
//		ADD_OP		-> "+" | "-"
		grammar.defineRule("ADD_OP",
				new AltOp(
						new AtomOp(grammar.getAtom("+")),
						new AtomOp(grammar.getAtom("-"))
						)
			);
		
//		MULT_OP		-> "*" | "/"
		grammar.defineRule("MULT_OP",
				new AltOp(
						new AtomOp(grammar.getAtom("*")),
						new AtomOp(grammar.getAtom("/"))
						)
			);
		
		
//		NUMBER		-> "0" | (NZ_DIGIT {"0" | NZ_DIGIT} )
		grammar.defineRule("NUMBER",
				new AltOp(
						new AtomOp(grammar.getAtom("0")),
						new ConcatOp(
								new RuleOp(grammar.getRule("NZ_DIGIT")),
								new RepOp(
										new AltOp(
												new AtomOp(grammar.getAtom("0")),
												new RuleOp(grammar.getRule("NZ_DIGIT"))
												)
										)
								)
						)
			);
		
		
//		NZ_DIGIT	-> "1" | "2" | ... | "9"
		grammar.defineRule("NZ_DIGIT",
				new AltOp(
						new AtomOp(grammar.getAtom("1")),
						new AtomOp(grammar.getAtom("2")),
						new AtomOp(grammar.getAtom("3")),
						new AtomOp(grammar.getAtom("4")),
						new AtomOp(grammar.getAtom("5")),
						new AtomOp(grammar.getAtom("6")),
						new AtomOp(grammar.getAtom("7")),
						new AtomOp(grammar.getAtom("8")),
						new AtomOp(grammar.getAtom("9"))
						)
			);
		
		
		test(grammar, testStrings[1]);
		
//		/*
//		
//		GRAMMAR:
//		
//		SENTENCE	-> STRING WS OP [?|!] WS STRING
//		STRING		-> QM WORD QM 
//		WS			-> " " [WS]
//		QM			-> """
//		WORD		-> "Hello" | "and Bye"
//		OP			-> "and" | "or"
//		
//		*/
//		
//		Grammar grammar = new Grammar();
//		grammar.predefineRules("SENTENCE", "WHITESPACE", "STRING", "WORD", "OPERATOR");
//		grammar.setStartingRule("SENTENCE");
//		grammar.predefineAtoms(" ", "\"", "Hello", "and Bye", "and", "or", "xor", "?", "!");
//		
//		grammar.defineRule("SENTENCE", new ConcatOp(
//				new RuleOp(grammar.getRule("STRING")),
//				new RuleOp(grammar.getRule("WHITESPACE")),
//				new RuleOp(grammar.getRule("OPERATOR")),
//				new OptOp(new AltOp(
//						new AtomOp(grammar.getAtom("?")),
//						new AtomOp(grammar.getAtom("!"))
//						)),
//				new RuleOp(grammar.getRule("WHITESPACE")),
//				new RuleOp(grammar.getRule("STRING"))
//				));
//		
//		grammar.defineRule("WHITESPACE", new ConcatOp(
//				new AtomOp(grammar.getAtom(" ")),
//				new OptOp(new RuleOp(grammar.getRule("WHITESPACE")))
//			));
//		
//		grammar.defineRule("STRING", new ConcatOp(
//				new AtomOp(grammar.getAtom("\"")),
//				new RuleOp(grammar.getRule("WORD")),
//				new AtomOp(grammar.getAtom("\""))
//				));
//		
//		grammar.defineRule("WORD", new AltOp(
//				new AtomOp(grammar.getAtom("Hello")),
//				new AtomOp(grammar.getAtom("and Bye"))
//				));
//		
//		grammar.defineRule("OPERATOR", new AltOp(
//				new AtomOp(grammar.getAtom("and")),
//				new AtomOp(grammar.getAtom("or")),
//				new AtomOp(grammar.getAtom("xor"))
//				));
//		
//		for(String str : testStrings) {
//			test(grammar, str);
//		}
		
	}
	
	
	
	
	private static void test(Grammar grammar, String testString) {
		
		System.out.println("==== TEST: " + testString);
		
		Tokenizer tokenizer = new Tokenizer(testString, grammar);
		List<Atom> tokens = tokenizer.tokenize();
		System.out.println("TOKENS: " + tokens);
		
		TreeBuilder treeBuilder = new TreeBuilder();
		Node root = treeBuilder.build(grammar, tokens);
		root.printGraphViz(true);
		
		System.out.println();
	}
	
}

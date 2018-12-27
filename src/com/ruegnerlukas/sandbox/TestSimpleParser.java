//package com.ruegnerlukas.sandbox;
//
//import com.ruegnerlukas.simpleparser.grammar.Grammar;
//import com.ruegnerlukas.simpleparser.grammar.GrammarBuilder;
//import com.ruegnerlukas.simpleparser.tokens.Token;
//import com.ruegnerlukas.simpleparser.tokens.Tokenizer;
//import com.ruegnerlukas.simpleparser.tree.Node;
//import com.ruegnerlukas.simpleparser.tree.TreeBuilder;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.List;
//
//
//public class TestSimpleParser {
//
//	public static void main(String[] args) throws IOException {
//
//		File file = new File("src/test2.txt");
//		byte[] bytes = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
//		String source = new String(bytes);
//
//		System.out.println("==== SOURCE ====");
//		System.out.println(source);
//		System.out.println("================ \n");
//
//		String[] testStrings = source.split(System.lineSeparator());
//
//
//
//		/*
//
//		EXPRESSION 	-> (EXPRESSION ADD_OP TERM) | TERM
//		TERM		-> (TERM MULT_OP FACTOR) | FACTOR
//		FACTOR		-> ( "(" EXPRESSION ")" ) | NUMBER
//		ADD_OP		-> "+" | "-"
//		MULT_OP		-> "*" | "/"
//		NUMBER		-> (DIGIT NUMBER) DIGIT
//		DIGIT		-> "0" | "1" | ... | "9"
//		-> !! LEFT_RECURSIVE @EXPRESSION !!
//
//
//		EXPRESSION 	-> TERM {ADD_OP TERM}
//		TERM		-> FACTOR {MULT_OP FACTOR}
//		FACTOR 		-> ( "(" EXPRESSION ")" ) | NUMBER
//		ADD_OP		-> "+" | "-"
//		MULT_OP		-> "*" | "/"
//		NUMBER		-> "0" | (NZ_DIGIT {"0" | NZ_DIGIT} )
//		NZ_DIGIT	-> "1" | "2" | ... | "9"
//
//		*/
//
//		GrammarBuilder gb = new GrammarBuilder();
//
////		EXPRESSION 	-> TERM {ADD_OP TERM}
//		gb.defineRootNonTerminal("EXPRESSION",
//				gb.sequence(
//						gb.nonTerminal("TERM"),
//						gb.zeroOrMore(
//								gb.sequence(
//										gb.nonTerminal("ADD_OP"),
//										gb.nonTerminal("TERM")
//										)
//								)
//						)
//				);
//
////		TERM		-> FACTOR {MULT_OP FACTOR}
//		gb.defineNonTerminal("TERM",
//				gb.sequence(
//						gb.nonTerminal("FACTOR"),
//						gb.zeroOrMore(
//								gb.sequence(
//										gb.nonTerminal("MULT_OP"),
//										gb.nonTerminal("FACTOR")
//										)
//								)
//						)
//				);
//
////		FACTOR 		-> ( "(" EXPRESSION ")" ) | NUMBER
//		gb.defineNonTerminal("FACTOR",
//				gb.alternative(
//						gb.sequence(
//								gb.terminal("("),
//								gb.nonTerminal("EXPRESSION"),
//								gb.terminal(")")
//								),
//						gb.nonTerminal("NUMBER")
//						)
//				);
//
////		ADD_OP		-> "+" | "-"
//		gb.defineNonTerminal("ADD_OP",
//				gb.alternative(
//						gb.terminal("+"),
//						gb.terminal("-")
//						)
//				);
//
////		MULT_OP		-> "*" | "/"
//		gb.defineNonTerminal("MULT_OP",
//				gb.alternative(
//						gb.terminal("*"),
//						gb.terminal("/")
//						)
//				);
//
////		NUMBER		-> "0" | (NZ_DIGIT {"0" | NZ_DIGIT} )
//		gb.defineNonTerminal("NUMBER",
//				gb.alternative(
//						gb.terminal("0"),
//						gb.sequence(
//								gb.nonTerminal("NZ_DIGIT"),
//								gb.zeroOrMore(
//										gb.alternative(
//												gb.terminal("0"),
//												gb.nonTerminal("NZ_DIGIT")
//												)
//										)
//								)
//						)
//				);
//
////		NZ_DIGIT	-> "1" | "2" | ... | "9"
//		gb.defineNonTerminal("NZ_DIGIT",
//				gb.alternative("1", "2", "3", "4", "5", "6", "7", "8", "9")
//				);
//
//
//		Grammar grammar = gb.get();
//		sandbox(grammar, testStrings[2]);
//
//
////		Grammar grammar = new Grammar();
////		grammar.predefineRules("EXPRESSION", "TERM", "FACTOR", "ADD_OP", "MULT_OP", "NUMBER", "NZ_DIGIT");
////		grammar.setStartingRule("EXPRESSION");
////		grammar.predefineAtoms("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "+", "-", "*", "/", "(", ")");
////
//////		EXPRESSION 	-> TERM {ADD_OP TERM}
////		grammar.defineRule("EXPRESSION",
////				new SequenceExpression(
////						new RuleExpression(grammar.getRule("TERM")),
////						new RepetitionExpression(
////								new SequenceExpression(
////										new RuleExpression(grammar.getRule("ADD_OP")),
////										new RuleExpression(grammar.getRule("TERM"))
////										)
////								)
////						)
////				);
////
////
//////		TERM		-> FACTOR {MULT_OP FACTOR}
////		grammar.defineRule("TERM",
////				new SequenceExpression(
////						new RuleExpression(grammar.getRule("FACTOR")),
////						new RepetitionExpression(
////								new SequenceExpression(
////										new RuleExpression(grammar.getRule("MULT_OP")),
////										new RuleExpression(grammar.getRule("FACTOR"))
////										)
////								)
////						)
////				);
////
////
//////		FACTOR 		-> ( "(" EXPRESSION ")" ) | NUMBER
////		grammar.defineRule("FACTOR",
////					new AlternativeExpression(
////							new SequenceExpression(
////									new TokenExpression(grammar.getToken("(")),
////									new RuleExpression(grammar.getRule("EXPRESSION")),
////									new TokenExpression(grammar.getToken(")"))
////									),
////							new RuleExpression(grammar.getRule("NUMBER"))
////							)
////				);
////
//////		ADD_OP		-> "+" | "-"
////		grammar.defineRule("ADD_OP",
////				new AlternativeExpression(
////						new TokenExpression(grammar.getToken("+")),
////						new TokenExpression(grammar.getToken("-"))
////						)
////			);
////
//////		MULT_OP		-> "*" | "/"
////		grammar.defineRule("MULT_OP",
////				new AlternativeExpression(
////						new TokenExpression(grammar.getToken("*")),
////						new TokenExpression(grammar.getToken("/"))
////						)
////			);
////
////
//////		NUMBER		-> "0" | (NZ_DIGIT {"0" | NZ_DIGIT} )
////		grammar.defineRule("NUMBER",
////				new AlternativeExpression(
////						new TokenExpression(grammar.getToken("0")),
////						new SequenceExpression(
////								new RuleExpression(grammar.getRule("NZ_DIGIT")),
////								new RepetitionExpression(
////										new AlternativeExpression(
////												new TokenExpression(grammar.getToken("0")),
////												new RuleExpression(grammar.getRule("NZ_DIGIT"))
////												)
////										)
////								)
////						)
////			);
////
////
//////		NZ_DIGIT	-> "1" | "2" | ... | "9"
////		grammar.defineRule("NZ_DIGIT",
////				new AlternativeExpression(
////						new TokenExpression(grammar.getToken("1")),
////						new TokenExpression(grammar.getToken("2")),
////						new TokenExpression(grammar.getToken("3")),
////						new TokenExpression(grammar.getToken("4")),
////						new TokenExpression(grammar.getToken("5")),
////						new TokenExpression(grammar.getToken("6")),
////						new TokenExpression(grammar.getToken("7")),
////						new TokenExpression(grammar.getToken("8")),
////						new TokenExpression(grammar.getToken("9"))
////						)
////			);
////
////
////		sandbox(grammar, testStrings[1]);
//
////		/*
////
////		GRAMMAR:
////
////		SENTENCE	-> STRING WS OP [?|!] WS STRING
////		STRING		-> QM WORD QM
////		WS			-> " " [WS]
////		QM			-> """
////		WORD		-> "Hello" | "and Bye"
////		OP			-> "and" | "or"
////
////		*/
////
////		Grammar grammar = new Grammar();
////		grammar.predefineRules("SENTENCE", "WHITESPACE", "STRING", "WORD", "OPERATOR");
////		grammar.setStartingRule("SENTENCE");
////		grammar.predefineAtoms(" ", "\"", "Hello", "and Bye", "and", "or", "xor", "?", "!");
////
////		grammar.defineRule("SENTENCE", new ConcatOp(
////				new RuleOp(grammar.getRule("STRING")),
////				new RuleOp(grammar.getRule("WHITESPACE")),
////				new RuleOp(grammar.getRule("OPERATOR")),
////				new OptOp(new AltOp(
////						new AtomOp(grammar.getToken("?")),
////						new AtomOp(grammar.getToken("!"))
////						)),
////				new RuleOp(grammar.getRule("WHITESPACE")),
////				new RuleOp(grammar.getRule("STRING"))
////				));
////
////		grammar.defineRule("WHITESPACE", new ConcatOp(
////				new AtomOp(grammar.getToken(" ")),
////				new OptOp(new RuleOp(grammar.getRule("WHITESPACE")))
////			));
////
////		grammar.defineRule("STRING", new ConcatOp(
////				new AtomOp(grammar.getToken("\"")),
////				new RuleOp(grammar.getRule("WORD")),
////				new AtomOp(grammar.getToken("\""))
////				));
////
////		grammar.defineRule("WORD", new AltOp(
////				new AtomOp(grammar.getToken("Hello")),
////				new AtomOp(grammar.getToken("and Bye"))
////				));
////
////		grammar.defineRule("OPERATOR", new AltOp(
////				new AtomOp(grammar.getToken("and")),
////				new AtomOp(grammar.getToken("or")),
////				new AtomOp(grammar.getToken("xor"))
////				));
////
////		for(String str : testStrings) {
////			sandbox(grammar, str);
////		}
//
//	}
//
//
//
//
//	private static void sandbox(Grammar grammar, String testString) {
//
//		System.out.println("==== TEST: " + testString);
//
//		Tokenizer tokenizer = new Tokenizer(testString, grammar);
//		List<Token> tokens = tokenizer.tokenize(true);
//		System.out.println("TOKENS: " + tokens);
//
//		System.out.println("TREE: " + tokens);
//		TreeBuilder treeBuilder = new TreeBuilder();
//		Node root = treeBuilder.build(grammar, tokens).node;
//		root.printGraphViz(true);
//
//		System.out.println();
//	}
//
//}

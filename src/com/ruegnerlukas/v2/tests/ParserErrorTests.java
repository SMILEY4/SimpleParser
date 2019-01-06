package com.ruegnerlukas.v2.tests;

import com.ruegnerlukas.v2.dotGraph.DotTreeBuilder;
import com.ruegnerlukas.v2.simpleparser.Node;
import com.ruegnerlukas.v2.simpleparser.Token;
import com.ruegnerlukas.v2.simpleparser.grammar.Grammar;
import com.ruegnerlukas.v2.simpleparser.grammar.State;
import com.ruegnerlukas.v2.simpleparser.parser.TokenParser;
import com.ruegnerlukas.v2.simpleparser.parser.ParserResult;
import com.ruegnerlukas.v2.simpleparser.parser.ParserResultError;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ParserErrorTests {


	private Grammar boolGrammar = TestGrammarBuilder.buildBoolGrammar();




	@Test
	public void testBool1() {
		Assertions.assertEquals(State.ERROR, getState(""));
	}

	@Test
	public void testBool2() {
		Assertions.assertEquals(State.ERROR, getState("x,(,e,)"));
	}

	@Test
	public void testBool3() {
		Assertions.assertEquals(State.ERROR, getState("e,(,e,)"));
	}

	@Test
	public void testBool4() {
		Assertions.assertEquals(State.ERROR, getState("e,and,e,and"));
	}

	@Test
	public void testBool5() {
		Assertions.assertEquals(State.ERROR, getState("e,and,(,e,or,)"));
	}

	@Test
	public void testBool6() {
		Assertions.assertEquals(State.ERROR, getState("e,and,x"));
	}

	@Test
	public void testBool7() {
		Assertions.assertEquals(State.ERROR, getState("e,and,(,x,)"));
	}

	@Test
	public void testBool8() {
		Assertions.assertEquals(State.ERROR, getState("e,and,(,e"));
	}

	@Test
	public void testBool9() {
		Assertions.assertEquals(State.ERROR, getState("e,and,(,(,e,or,e,)"));
	}

	@Test
	public void testBool10() {
		Assertions.assertEquals(State.ERROR, getState("e,and,(,e,or,e,),and,(,e,),)"));
	}




	State getState(String csInput) {

		List<Token> tokens = asTokenList(csInput);
		System.out.println("PARSE: " + csInput + "  -> " + tokens);

		TokenParser parser = new TokenParser(boolGrammar);
		ParserResult result = parser.parse(tokens, true, true);

		if(result.failed()) {
			ParserResultError errorResult = (ParserResultError)result;
			int i=0;
			for(List<Node> bucket : errorResult.getResultList()) {
				System.out.print( (i++) + ":  ");
				for(Node n : bucket) {
					System.out.print(n + ",  ");
				}
				System.out.println();
			}
		}

		System.out.println(DotTreeBuilder.build(result.getRoot()));
		System.out.println();

		return result.getState();
	}




	List<Token> asTokenList(String csInput) {
		String[] array = csInput.split(",");
		List<Token> tokens = new ArrayList<>();
		for(String str : array) {
			if(!str.isEmpty()) {
				tokens.add(new Token(str));
			}
		}
		return tokens;
	}


}

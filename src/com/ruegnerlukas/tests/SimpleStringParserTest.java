package com.ruegnerlukas.tests;

import com.ruegnerlukas.dotGraph.DotGrammarBuilder;
import com.ruegnerlukas.simpleparser.grammar.Grammar;
import com.ruegnerlukas.simpleparser.grammar.State;
import com.ruegnerlukas.simpleparser.parser.ParserResult;
import com.ruegnerlukas.simpleparser.parser.StringParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class SimpleStringParserTest {

	private Grammar boolGrammar = TestGrammarBuilder.buildBoolGrammar();


	@Test
	public void testBool1() {
		Assertions.assertEquals(State.MATCH, getState("e and e"));
	}

	@Test
	public void testBool2() {
		Assertions.assertEquals(State.MATCH, getState("e or e"));
	}

	@Test
	public void testBool3() {
		Assertions.assertEquals(State.MATCH, getState("e and ( e or e )"));
	}

	@Test
	public void testBool4() {
		Assertions.assertEquals(State.MATCH, getState("e or ( e and ( e or e ) or ( e and e ) )"));
	}

	@Test
	public void testBool6() {
		Assertions.assertEquals(State.MATCH, getState("e"));
	}

	@Test
	public void testBool7() {
		Assertions.assertEquals(State.MATCH, getState("( e )"));
	}

	@Test
	public void testBool8() {
		Assertions.assertEquals(State.ERROR, getState(""));
	}

	@Test
	public void testBool9() {
		Assertions.assertEquals(State.ERROR, getState("x ( e )"));
	}

	@Test
	public void testBool10() {
		Assertions.assertEquals(State.ERROR, getState("e ( e )"));
	}

	@Test
	public void testBool11() {
		Assertions.assertEquals(State.ERROR, getState("e and e and"));
	}

	@Test
	public void testBool12() {
		Assertions.assertEquals(State.ERROR, getState("e and e or"));
	}

	@Test
	public void testBool13() {
		Assertions.assertEquals(State.ERROR, getState("e and x"));
	}

	@Test
	public void testBool14() {
		Assertions.assertEquals(State.ERROR, getState("e and ( x )"));
	}

	@Test
	public void testBool15() {
		Assertions.assertEquals(State.ERROR, getState("e and ( e"));
	}

	@Test
	public void testBool16() {
		Assertions.assertEquals(State.ERROR, getState("e and ( ( e or e )"));
	}

	@Test
	public void testBool17() {
		Assertions.assertEquals(State.ERROR, getState("e and ( e or e ) and ( e ) )"));
	}




	State getState(String strInput) {
		StringParser parser = new StringParser(boolGrammar);
		ParserResult result = parser.parse(strInput.replaceAll(" ", ""), false, false, false);
		System.out.println("PARSE: " + strInput);
		System.out.println(result.getRoot().collectLeafNodes());
		System.out.println(DotGrammarBuilder.build(boolGrammar, result.getTrace()));
		System.out.println();
		return result.getState();
	}


}

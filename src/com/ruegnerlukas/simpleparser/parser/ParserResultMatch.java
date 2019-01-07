package com.ruegnerlukas.simpleparser.parser;

import com.ruegnerlukas.simpleparser.Node;
import com.ruegnerlukas.simpleparser.Token;
import com.ruegnerlukas.simpleparser.grammar.State;
import com.ruegnerlukas.simpleparser.trace.Trace;

import java.util.List;

public class ParserResultMatch extends ParserResult {


	public ParserResultMatch(Node root, String inputString, Trace trace) {
		super(State.MATCH, inputString, root, trace);
	}




	public ParserResultMatch(Node root, List<Token> inputTokens, Trace trace) {
		super(State.MATCH, inputTokens, root, trace);
	}


}

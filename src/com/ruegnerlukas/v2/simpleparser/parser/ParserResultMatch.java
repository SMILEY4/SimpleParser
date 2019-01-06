package com.ruegnerlukas.v2.simpleparser.parser;

import com.ruegnerlukas.v2.simpleparser.Node;
import com.ruegnerlukas.v2.simpleparser.Token;
import com.ruegnerlukas.v2.simpleparser.grammar.State;
import com.ruegnerlukas.v2.simpleparser.trace.Trace;

import java.util.List;

public class ParserResultMatch extends ParserResult {




	public ParserResultMatch(Node root, String inputString, Trace trace) {
		super(State.MATCH, inputString, root, trace);
	}


	public ParserResultMatch(Node root, List<Token> inputTokens, Trace trace) {
		super(State.MATCH, inputTokens, root, trace);
	}




}

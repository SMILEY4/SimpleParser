package com.ruegnerlukas.simpleparser.parser;

import com.ruegnerlukas.simpleparser.ErrorNode;
import com.ruegnerlukas.simpleparser.Node;
import com.ruegnerlukas.simpleparser.Token;
import com.ruegnerlukas.simpleparser.grammar.State;
import com.ruegnerlukas.simpleparser.trace.Trace;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParserResultError extends ParserResult {


	private List<Node>[] resultList;


	public ParserResultError(Node root, String inputString, Trace trace) {
		super(State.ERROR, inputString, root, trace);
		buildResultList();
	}


	public ParserResultError(Node root, List<Token> inputTokens, Trace trace) {
		super(State.ERROR, inputTokens, root, trace);
		buildResultList();
	}



	private void buildResultList() {

		Node root = getRoot().eliminateNonTerminalLeafs();
		root = new Node(root.eliminateNonRuleNodes());

		List<Node> leafs = root.collectLeafNodes();

		if(this.inputWasTokenList()) {

			int nBuckets = 0;
			for(Node node : leafs) {
				if( !(node instanceof ErrorNode) ) {
					nBuckets++;
				} else {
					ErrorNode error = (ErrorNode)node;
					if(error.index > nBuckets) {
						nBuckets++;
					}
				}
			}

			List[] buckets = new List[nBuckets+1];

			int index=-1;
			for(Node node : leafs) {

				if( !(node instanceof ErrorNode) ) {
					index++;
				} else {
					ErrorNode error = (ErrorNode)node;
					if(error.index > index) {
						index++;
					}
				}

				int bucket = index;
				if(node instanceof ErrorNode) {
					bucket = ((ErrorNode)node).index;
				}

				if (buckets[bucket] == null) {
					buckets[bucket] = new ArrayList<Node>(Collections.singleton(node));

				} else {
					List list = buckets[bucket];
					list.add(node);
				}

			}

			this.resultList = (List<Node>[])buckets;

		} else {

			this.resultList = new List[]{leafs};

		}
	}




	public List<Node>[] getResultList() {
		return this.resultList;
	}


}

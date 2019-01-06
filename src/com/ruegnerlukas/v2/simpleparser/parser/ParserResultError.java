package com.ruegnerlukas.v2.simpleparser.parser;

import com.ruegnerlukas.v2.simpleparser.ErrorNode;
import com.ruegnerlukas.v2.simpleparser.Node;
import com.ruegnerlukas.v2.simpleparser.Token;
import com.ruegnerlukas.v2.simpleparser.grammar.State;
import com.ruegnerlukas.v2.simpleparser.trace.Trace;

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
	}




	public List<Node>[] getResultList() {
		return this.resultList;
	}


}

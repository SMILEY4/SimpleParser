package com.ruegnerlukas.simpleparser.parser;

import com.ruegnerlukas.simpleparser.ErrorNode;
import com.ruegnerlukas.simpleparser.Node;
import com.ruegnerlukas.simpleparser.Token;
import com.ruegnerlukas.simpleparser.grammar.State;
import com.ruegnerlukas.simpleparser.trace.Trace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ParserResultError extends ParserResult {


	private List<List<Node>> resultList;




	public ParserResultError(Node root, String inputString, Trace trace) {
		super(State.ERROR, inputString, root, trace);
		this.resultList = buildResultList();
	}




	public ParserResultError(Node root, List<Token> inputTokens, Trace trace) {
		super(State.ERROR, inputTokens, root, trace);
		this.resultList = buildResultList();
	}




	private List<List<Node>> buildResultList() {

		Node root = getRoot().eliminateNonTerminalLeafs();
		root = new Node(root.eliminateNonRuleNodes());

		List<Node> leafs = root.collectLeafNodes();

		List<List<Node>> buckets = new ArrayList<>();

		for (Node node : leafs) {

			if (node instanceof ErrorNode) {
				ErrorNode errorNode = (ErrorNode) node;

				if (buckets.isEmpty()) {
					buckets.add(new ArrayList<>(Collections.singletonList(errorNode)));
				} else {

					List<Node> lastBucket = buckets.get(buckets.size() - 1);
					Node lastNode = lastBucket.get(lastBucket.size() - 1);

					if (lastNode instanceof ErrorNode) {
						ErrorNode lastError = (ErrorNode) lastNode;

						if (lastError.index == errorNode.index) {
							lastBucket.add(errorNode);
						}

					} else {
						buckets.add(new ArrayList<>(Collections.singletonList(errorNode)));
					}

				}

			} else {
				buckets.add(new ArrayList<>(Arrays.asList(node)));
			}

		}

		return buckets;
	}




	/**
	 * @return an array of lists containing the tokens/errors at their index of the array
	 */
	public List<List<Node>> getResultList() {
		return this.resultList;
	}


}

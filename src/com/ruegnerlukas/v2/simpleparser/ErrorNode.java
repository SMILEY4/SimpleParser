package com.ruegnerlukas.v2.simpleparser;

import com.ruegnerlukas.v2.simpleparser.errors.ErrorType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ErrorNode extends Node {

	public ErrorType error;
	public int index;

	public ErrorNode(ErrorType error, int index) {
		this.error = error;
		this.index = index;
	}




	@Override
	public Node eliminateErrorNodes() {
		return null;
	}




	@Override
	public List<Node> eliminateNonRuleNodes() {
		List<Node> nodes = new ArrayList<>();
		for(Node child : children) {
			nodes.addAll(child.eliminateNonRuleNodes());
		}
		this.children = nodes;
		return new ArrayList<>(Collections.singletonList(this));
	}




	@Override
	public Node eliminateNonTerminalLeafs() {
		if(this.children.isEmpty()) {
			return this;

		} else {
			List<Node> nodes = new ArrayList<>();
			for(Node child : children) {
				Node n = child.eliminateNonTerminalLeafs();
				if(n != null) {
					nodes.add(n);
				}
			}
			this.children = nodes;
			return this;
		}
	}



	@Override
	public String toString() {
		return error + "@" + index + ":" + expression + "  ";
	}

}

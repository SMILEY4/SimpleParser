package com.ruegnerlukas.simpleparser.tree;

import java.util.ArrayList;
import java.util.List;

import com.ruegnerlukas.simpleparser.grammar.ruleops.AtomOp;
import com.ruegnerlukas.simpleparser.grammar.ruleops.Op;

public abstract class Node {
	
	
	public List<Node> children = new ArrayList<Node>();
	public Op op;

	
	
	
	public void eliminateMidNodes() {
		
		for(Node child : children) {
			child.eliminateMidNodes();
		}
		
		List<Node> newChildren = new ArrayList<Node>();
		
		for(Node child : children) {
			if(child instanceof MidNode) {
				newChildren.addAll(child.children);
			} else {
				newChildren.add(child);
			}
		}
		
		this.children = newChildren;
	}
	
	
	
	
	public void printGraphViz(boolean root) {
		
		if(root) {
			System.out.println("digraph G {");
		}
		
		for(Node child : children) {
			System.out.println("    \"" + this + "\" -> \"" + child + "\"");
		}
		for(Node child : children) {
			child.printGraphViz(false);
		}
	
		if(root) {
			System.out.println("}");
		}
		
	}
	
	
	public void print(int level) {
		
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<level; i++) {
			sb.append("  ");
		}
		
		if(op == null) {
			sb.append("null");
		} else {
			if(op instanceof AtomOp) {
				sb.append(op.getClass().getSimpleName() + ": '" + ((AtomOp)op).atom.symbol + "'");
			} else {
				sb.append(op.getClass().getSimpleName());
			}
		}
		System.out.println(sb.toString());
		
		for(Node child : children) {
			child.print(level+1);
		}
	}
	
	
}

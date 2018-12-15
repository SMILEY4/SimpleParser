package com.ruegnerlukas.simpleparser.grammar.expressions;

import com.ruegnerlukas.simpleparser.grammar.Rule;
import com.ruegnerlukas.simpleparser.grammar.Token;
import com.ruegnerlukas.simpleparser.tree.Node;
import com.ruegnerlukas.simpleparser.tree.RuleNode;

import java.util.List;
import java.util.Set;

public class RuleExpression extends Expression {

	public final Rule rule;
	
	
	public RuleExpression(Rule rule) {
		this.rule = rule;
	}

	
	

	@Override
	public Result apply(List<Token> tokens) {

		if(tokens.isEmpty()) {
			return new Result(Result.State.END_OF_STREAM, null);

		} else {
			Result resultRule = rule.expression.apply(tokens);
			if(resultRule.state == Result.State.SUCCESS) {
				Node node = new RuleNode(rule);
				node.children.add(resultRule.node);
				return new Result(Result.State.SUCCESS, node);
			}
			if(resultRule.state == Result.State.END_OF_STREAM) {
				Node node = new RuleNode(rule);
				node.children.add(resultRule.node);
				return new Result(Result.State.END_OF_STREAM, node);
			}
			if(resultRule.state == Result.State.UNEXPECTED_SYMBOL) {
				return new Result(Result.State.UNEXPECTED_SYMBOL, null, resultRule.message);
			}
			if(resultRule.state == Result.State.ERROR) {
				return new Result(Result.State.ERROR, null, resultRule.message);
			}

			return new Result(Result.State.ERROR, null, this + ": Undefined result state.");
		}

//		Node node = new RuleNode(rule);
//		Node n = rule.expression.apply(tokens);
//		if(n instanceof EmptyNode) {
//			return new EmptyNode();
//		} else {
//			node.children.add(n);
//			return node;
//		}
	}




	@Override
	public String toString() {
		return "\"" + "RULE:"+Integer.toHexString(this.hashCode())+ ": " + rule.name + "\"";
	}




	@Override
	public void printAsDotGraph(Set<Expression> visited) {
		if(visited.contains(this)) {
			return;
		}
		visited.add(this);

		System.out.println("    " + this + " -> " + rule.expression + ";");
		rule.expression.printAsDotGraph(visited);
		System.out.println("    " + this + " [color=\"1.0 1.0 1.0\"];");
	}


}

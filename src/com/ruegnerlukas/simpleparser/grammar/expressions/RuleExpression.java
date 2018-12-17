package com.ruegnerlukas.simpleparser.grammar.expressions;

import com.ruegnerlukas.simpleparser.error.ErrorMessages;
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
	public Result apply(List<Token> consumed, List<Token> tokens, List<Expression> trace) {
		trace.add(this);

		Result resultRule = rule.expression.apply(consumed, tokens, trace);

		if (resultRule.state == Result.State.SUCCESS) {
			Node node = new RuleNode(rule);
			node.children.add(resultRule.node);
			return new Result(Result.State.SUCCESS, node);
		}
		if (resultRule.state == Result.State.END_OF_STREAM) {
			return new Result(Result.State.ERROR, null, ErrorMessages.genMessage_endOfStream());
		}
		if (resultRule.state == Result.State.UNEXPECTED_SYMBOL) {
			return new Result(Result.State.ERROR, null, ErrorMessages.genMessage_unexpectedSymbol(consumed, tokens));
		}
		if (resultRule.state == Result.State.ERROR) {
			return new Result(Result.State.ERROR, null, resultRule.message);
		}

		return new Result(Result.State.ERROR, null, ErrorMessages.genMessage_undefinedState());

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

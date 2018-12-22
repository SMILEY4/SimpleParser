package com.ruegnerlukas.simpleparser.expressions;

import com.ruegnerlukas.simpleparser.grammar.Rule;
import com.ruegnerlukas.simpleparser.tokens.Token;
import com.ruegnerlukas.simpleparser.tree.Node;
import com.ruegnerlukas.simpleparser.tree.RuleNode;
import com.ruegnerlukas.simpleparser.tree.TraceElement;

import java.util.List;
import java.util.Set;

public class RuleExpression extends Expression {


	public final Rule rule;




	public RuleExpression(Rule rule) {
		this.rule = rule;
	}


	
	
	@Override
	public Result apply(List<Token> consumed, List<Token> tokens, List<TraceElement> trace) {
		TraceElement traceElement = null;
		if(trace != null) {
			traceElement = new TraceElement(this, Result.State.MATCH);
			trace.add(traceElement);
		}

		Result resultRule = rule.getExpression().apply(consumed, tokens, trace);

		if (resultRule.state == Result.State.MATCH) {
			Node node = new RuleNode(rule);
			node.children.add(resultRule.node);
			return new Result(node);
		} else {
			if(traceElement != null) { traceElement.state = resultRule.state; }
			return resultRule;
		}

	}




	@Override
	public boolean collectPossibleTokens(Set<Expression> visited, Set<Token> possibleTokens) {
		if(visited.contains(this)) {
			return false;
		} else {
			visited.add(this);
			return rule.getExpression().collectPossibleTokens(visited, possibleTokens);
		}
	}




	@Override
	public String toString() {
		return "\"" + "RULE:"+Integer.toHexString(this.hashCode())+ ": " + rule.getName() + "\"";
	}




	@Override
	public void createDotGraph(Set<Expression> visited, StringBuilder builder) {
		if(visited.contains(this)) {
			return;
		}
		visited.add(this);

		builder.append("    ").append(this).append(" -> ").append(rule.getExpression()).append(';').append(System.lineSeparator());
		rule.getExpression().createDotGraph(visited, builder);
		builder.append("    ").append(this).append(" [color=\"1.0 1.0 1.0\"];").append(System.lineSeparator());
	}

}

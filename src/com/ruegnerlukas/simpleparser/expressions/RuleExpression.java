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
		super(ExpressionType.RULE);
		this.rule = rule;
	}




	public boolean isOptionalExpression() {
		return rule.getExpression().isOptionalExpression();
	}




	public boolean collectPossibleTokens(Expression start, Set<Token> tokens) {
//		if(start != null) {
//			rule.getExpression().collectPossibleTokens(this, tokens);
//		}
		return true;
	}




	public void collectPossibleTokens(Set<Token> tokens) {
		rule.getExpression().collectPossibleTokens(tokens);
	}


	
	
	@Override
	public Result apply(List<Token> consumed, List<Token> tokens, List<TraceElement> trace) {

		// handle trace
		TraceElement traceElement = null;
		if(trace != null) {
			traceElement = new TraceElement(this, Result.State.MATCH);
			trace.add(traceElement);
		}

		// apply expression
		Result result = rule.getExpression().apply(consumed, tokens, trace);

		// matching
		if (result.state == Result.State.MATCH) {
			Node node = new RuleNode(rule).setExpression(this);
			node.addChild(result.node);
			return Result.match( node );

		} else {
			if(traceElement != null) { traceElement.state = result.state; }
			return result;
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

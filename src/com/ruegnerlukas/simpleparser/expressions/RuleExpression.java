package com.ruegnerlukas.simpleparser.expressions;

import com.ruegnerlukas.simpleparser.grammar.Rule;
import com.ruegnerlukas.simpleparser.tokens.Token;
import com.ruegnerlukas.simpleparser.tree.Node;
import com.ruegnerlukas.simpleparser.tree.RuleNode;

import java.util.List;
import java.util.Set;

public class RuleExpression extends Expression {

	public final Rule rule;
	public Expression parent;




	protected RuleExpression(Rule rule) {
		this.rule = rule;
		if(rule.expression != null) {
			rule.expression.setParent(this);
		} else {
			rule.tmpParent = this;
		}
	}



	@Override
	public void setParent(Expression parent) {
		this.parent = parent;
	}


	@Override
	public Expression getParent() {
		return parent;
	}

	
	

	@Override
	public Result apply(List<Token> consumed, List<Token> tokens, List<Expression> trace) {
		if(trace != null) {
			trace.add(this);
		}

		Result resultRule = rule.expression.apply(consumed, tokens, trace);

		if (resultRule.state == Result.State.MATCH) {
			Node node = new RuleNode(rule);
			node.children.add(resultRule.node);
			return new Result(node);
		} else {
			return resultRule;
		}

	}




	@Override
	public boolean collectPossibleTokens(Set<Expression> visited, Set<Token> possibleTokens) {
		if(visited.contains(this)) {
			return false;
		} else {
			visited.add(this);
			return rule.expression.collectPossibleTokens(visited, possibleTokens);
		}
	}




	@Override
	public String toString() {
		return "\"" + "RULE:"+Integer.toHexString(this.hashCode())+ ": " + rule.name + "\"";
	}




	@Override
	public void createDotGraph(Set<Expression> visited, StringBuilder builder) {
		if(visited.contains(this)) {
			return;
		}
		visited.add(this);

		builder.append("    ").append(this).append(" -> ").append(rule.expression).append(';').append(System.lineSeparator());
		rule.expression.createDotGraph(visited, builder);
		builder.append("    ").append(this).append(" [color=\"1.0 1.0 1.0\"];").append(System.lineSeparator());
	}


}

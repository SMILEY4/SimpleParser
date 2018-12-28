package com.ruegnerlukas.simpleparser.expressions;

import com.ruegnerlukas.simpleparser.grammar.Rule;
import com.ruegnerlukas.simpleparser.tokens.Token;

import java.util.Set;

public class RuleExpression extends Expression {


	public final Rule rule;




	public RuleExpression(Rule rule) {
		super(ExpressionType.RULE);
		this.rule = rule;
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
	public String toString() {
		return "RULE:"+Integer.toHexString(this.hashCode())+ ": " + rule.getName();
	}



}

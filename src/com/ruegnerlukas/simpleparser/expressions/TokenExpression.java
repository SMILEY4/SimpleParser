package com.ruegnerlukas.simpleparser.expressions;

import com.ruegnerlukas.simpleparser.tokens.Token;

import java.util.Set;

public class TokenExpression extends Expression {


	public Token token;




	public TokenExpression(Token token) {
		super(ExpressionType.TOKEN);
		this.token = token;
	}




	public boolean collectPossibleTokens(Expression start, Set<Token> tokens) {
		return true;
	}




	public void collectPossibleTokens(Set<Token> tokens) {
		tokens.add(token);
	}





	@Override
	public String toString() {
		return "TOKEN:"+Integer.toHexString(this.hashCode())+ ": " + token.getSymbol();
	}

}
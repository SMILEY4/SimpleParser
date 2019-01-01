package com.ruegnerlukas.v2.simpleparser.grammar.expressions;

import com.ruegnerlukas.v1.simpleparser.tokens.Token;

public class TokenExpression extends Expression {


	public Token token;




	public TokenExpression(Token token) {
		super(ExpressionType.TOKEN);
		this.token = token;
	}



	@Override
	public String toString() {
		return "TOKEN:"+Integer.toHexString(this.hashCode())+ ": " + token.getSymbol();
	}

}
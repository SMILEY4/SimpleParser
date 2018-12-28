package com.ruegnerlukas.simpleparser.expressions;

import com.ruegnerlukas.simpleparser.tokens.Token;

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
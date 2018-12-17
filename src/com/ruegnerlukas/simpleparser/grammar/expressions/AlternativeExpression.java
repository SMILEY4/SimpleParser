package com.ruegnerlukas.simpleparser.grammar.expressions;

import com.ruegnerlukas.simpleparser.error.ErrorMessages;
import com.ruegnerlukas.simpleparser.grammar.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * X -> E0 | E1 | ... | En
 * */
public class AlternativeExpression extends Expression {

	
	public List<Expression> expressions = new ArrayList<Expression>();

	
	
	
	public AlternativeExpression(Expression... expressions) {
		for(Expression expr : expressions) {
			this.expressions.add(expr);
		}
	}

	
	

	@Override
	public Result apply(List<Token> consumed, List<Token> tokens) {

		System.out.println("APPLY " + this);

		for(Expression expr : expressions) {
			Result resultExpr = expr.apply(consumed, tokens);

			if(resultExpr.state == Result.State.SUCCESS) {
				return new Result(Result.State.SUCCESS, resultExpr.node);
			}

			if(resultExpr.state == Result.State.END_OF_STREAM) {
				return new Result(Result.State.ERROR, null, ErrorMessages.genMessage_endOfStream());
			}

			if(resultExpr.state == Result.State.UNEXPECTED_SYMBOL) {
				continue;
			}

			if(resultExpr.state == Result.State.ERROR) {
				continue;
			}

		}

		return new Result(Result.State.ERROR, null, ErrorMessages.genMessage_unexpectedSymbol(consumed, tokens));
	}




	@Override
	public String toString() {
		return "\"" + "ALTERNATIVE:"+Integer.toHexString(this.hashCode())+"\"";
	}




	@Override
	public void printAsDotGraph(Set<Expression> visited) {
		if(visited.contains(this)) {
			return;
		}
		visited.add(this);


		for(Expression e : expressions) {
			System.out.println("    " + this + " -> " + e + ";");
		}
		for(Expression e : expressions) {
			e.printAsDotGraph(visited);
		}

	}

}

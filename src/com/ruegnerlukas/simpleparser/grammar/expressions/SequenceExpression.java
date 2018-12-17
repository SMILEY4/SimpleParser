package com.ruegnerlukas.simpleparser.grammar.expressions;

import com.ruegnerlukas.simpleparser.error.ErrorMessages;
import com.ruegnerlukas.simpleparser.grammar.Token;
import com.ruegnerlukas.simpleparser.tree.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * X -> E0 E1 ... EN
 * */
public class SequenceExpression extends Expression {


	public List<Expression> expressions = new ArrayList<Expression>();




	public SequenceExpression(Expression... expressions) {
		for(Expression expr : expressions) {
			this.expressions.add(expr);
		}
	}

	


	@Override
	public Result apply(List<Token> consumed, List<Token> tokens) {

		System.out.println("APPLY " + this);

		Node node = new MidNode(Integer.toHexString(this.hashCode()));

		for (Expression expr : expressions) {

			Result resultExpr = expr.apply(consumed, tokens);

			if (resultExpr.state == Result.State.SUCCESS) {
				node.children.add(resultExpr.node);
			}
			if (resultExpr.state == Result.State.END_OF_STREAM) {
				if (expressions.indexOf(expr) != expressions.size() - 1) {
					return new Result(Result.State.ERROR, node, ErrorMessages.genMessage_endOfStream());
				}
			}
			if (resultExpr.state == Result.State.UNEXPECTED_SYMBOL) {
				return new Result(Result.State.ERROR, null, ErrorMessages.genMessage_unexpectedSymbol(consumed, tokens));
			}
			if (resultExpr.state == Result.State.ERROR) {
				return new Result(Result.State.ERROR, null, resultExpr.message);
			}

		}

		return new Result(Result.State.SUCCESS, node);
	}




	@Override
	public String toString() {
		return "\"" + "SEQUENCE:"+Integer.toHexString(this.hashCode())+"\"";
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







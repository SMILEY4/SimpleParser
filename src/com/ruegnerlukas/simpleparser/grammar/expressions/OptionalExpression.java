package com.ruegnerlukas.simpleparser.grammar.expressions;

import com.ruegnerlukas.simpleparser.grammar.Token;
import com.ruegnerlukas.simpleparser.tree.EmptyNode;

import java.util.List;
import java.util.Set;

/**
 * X -> [E]
 *  => none or one E
 * */
public class OptionalExpression extends Expression {

	
	public Expression expression = null;
	
	
	
	
	public OptionalExpression(Expression expression) {
		this.expression = expression;
	}

	
	

	@Override
	public Result apply(List<Token> tokens) {

		if(tokens.isEmpty()) {
			return new Result(Result.State.END_OF_STREAM, null);

		} else {

			Result resultExpr = expression.apply(tokens);

			if(resultExpr.state == Result.State.SUCCESS) {
				return new Result(Result.State.SUCCESS, resultExpr.node);
			}
			if(resultExpr.state == Result.State.END_OF_STREAM) {
				return new Result(Result.State.END_OF_STREAM, resultExpr.node);
			}
			if(resultExpr.state == Result.State.UNEXPECTED_SYMBOL) {
				return new Result(Result.State.SUCCESS, new EmptyNode());
			}
			if(resultExpr.state == Result.State.ERROR) {
				return new Result(Result.State.ERROR, null, resultExpr.message);
			}

			return new Result(Result.State.ERROR, null, this + ": Undefined result state.");
		}


//		if(tokens.isEmpty()) {
//			return new EmptyNode();
//		} else {
//			Node n = expression.apply(tokens);
//			if(n instanceof EmptyNode) {
//				return new EmptyNode();
//			} else {
//				return n;
//			}
//		}
	}




	@Override
	public String toString() {
		return "\"" + "OPTIONAL:"+Integer.toHexString(this.hashCode()) + "\"";
	}




	@Override
	public void printAsDotGraph(Set<Expression> visited) {
		if(visited.contains(this)) {
			return;
		}
		visited.add(this);


		System.out.println("    " + this + " -> " + expression + ";");
		expression.printAsDotGraph(visited);

	}


}

package com.ruegnerlukas.simpleparser.grammar.expressions;

import com.ruegnerlukas.simpleparser.ErrorMessages;
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
	public Result apply(List<Token> consumed, List<Token> tokens, List<Expression> trace) {
		if(trace != null) {
			trace.add(this);
		}

		Node node = new PlaceholderNode(Integer.toHexString(this.hashCode()));

		for (Expression expr : expressions) {

			Result resultExpr = expr.apply(consumed, tokens, trace);

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
	public void createDotGraph(Set<Expression> visited, StringBuilder builder) {
		if(visited.contains(this)) {
			return;
		}
		visited.add(this);

		for(Expression e : expressions) {
			builder.append("    ").append(this).append(" -> ").append(e).append(';').append(System.lineSeparator());
		}
		for(Expression e : expressions) {
			e.createDotGraph(visited, builder);
		}

	}
}







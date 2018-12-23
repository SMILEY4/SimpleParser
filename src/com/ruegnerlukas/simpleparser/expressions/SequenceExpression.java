package com.ruegnerlukas.simpleparser.expressions;

import com.ruegnerlukas.simpleparser.tokens.Token;
import com.ruegnerlukas.simpleparser.tree.Node;
import com.ruegnerlukas.simpleparser.tree.PlaceholderNode;
import com.ruegnerlukas.simpleparser.tree.TraceElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SequenceExpression extends Expression {


	public List<Expression> expressions = new ArrayList<>();




	/**
	 * X -> E0 E1 ... EN
	 * */
	public SequenceExpression(Expression... expressions) {
		for(Expression e : expressions) {
			e.addParent(this);
			this.expressions.add(e);
		}
	}




	@Override
	public Result apply(List<Token> consumed, List<Token> tokens, List<TraceElement> trace) {
		TraceElement traceElement = null;
		if(trace != null) {
			traceElement = new TraceElement(this, Result.State.MATCH);
			trace.add(traceElement);
		}

		Node node = new PlaceholderNode(Integer.toHexString(this.hashCode()));

		for (Expression expr : expressions) {

			// get possible tokens at cursor
//			if(!tokens.isEmpty() && tokens.get(0).getType() == TokenType.CURSOR) {
//				consumed.add(tokens.remove(0));
//
//				SequenceExpression sequenceExpression = new SequenceExpression();
//				for(int i=expressions.indexOf(expr); i<expressions.size(); i++) {
//					sequenceExpression.expressions.add(expressions.get(i));
//				}
//
//				Set<Token> possible = new HashSet<>();
//				Set<Expression> visited = new HashSet<>();
//				visited.add(this);
//				sequenceExpression.collectPossibleTokens(visited, possible);
//
//				if(getParent() != null) {
//					getParent().collectPossibleTokens(this, visited, possible);
//				}
//
//				System.out.println("-> " + visited.size());
//				for(Token t : possible) {
//					System.out.println("   " + t);
//				}
//
//			}

			Result resultExpr = expr.apply(consumed, tokens, trace);

			if (resultExpr.state == Result.State.MATCH) {
				node.children.add(resultExpr.node);
			}
			if (resultExpr.state == Result.State.NO_MATCH) {
				if(traceElement != null) { traceElement.state = Result.State.NO_MATCH; }
				return resultExpr;
			}
			if (resultExpr.state == Result.State.ERROR) {
				if(traceElement != null) { traceElement.state = Result.State.ERROR; }
				return resultExpr;
			}

		}

		return new Result(node);
	}




	@Override
	public boolean collectPossibleTokens(Set<Expression> visited, Set<Token> possibleTokens) {
//		if(visited.contains(this)) {
//			return false;
//		} else {
			visited.add(this);
			for(Expression expression : expressions) {
				boolean opt = expression.collectPossibleTokens( visited, possibleTokens);
				if(!opt) {
					return false;
				}
			}
			return true;
//		}
	}



	@Override
	public boolean collectPossibleTokens(Expression start, Set<Expression> visited, Set<Token> possibleTokens) {
		System.out.println("collect @" + this + "  root=" + isRoot);

		if(visited.contains(this)) {
			return false;
		} else {
			visited.add(this);

			SequenceExpression sequence = new SequenceExpression();

			int index = expressions.indexOf(start);

			if(index == expressions.size()-1) {
				return false;

			} else {
				for(int i=index+1; i<expressions.size(); i++) {
					sequence.expressions.add(expressions.get(i));
				}
				sequence.collectPossibleTokens(visited, possibleTokens);

				for(Expression parent : getParents()) {
					parent.collectPossibleTokens(this, visited, possibleTokens);
				}
			}

			return true;
		}
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
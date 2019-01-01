//package com.ruegnerlukas.simpleparser.systems;
//
//import Error;
//import com.ruegnerlukas.simpleparser.expressions.*;
//import Grammar;
//import Token;
//import TokenType;
//import Node;
//import PlaceholderNode;
//import RuleNode;
//import TerminalNode;
//
//import java.util.*;
//
//
//public class ExpressionProcessorOLD {
//
//
//	private static List<TraceElement> trace = new ArrayList<>();
//
//
//
//
//	public static List<TraceElement> getLastTrace() {
//		return trace;
//	}
//
//
//
//
//	public static Result apply(Grammar grammar, List<Token> tokens) {
//		return apply(grammar.getRule(grammar.getStartingRule()).getExpression(), tokens);
//	}
//
//
//
//
//	public static Result apply(Expression expression, List<Token> tokens) {
//		trace.clear();
//		List<Token> consumed = new ArrayList<>();
//		List<Token> tokenList = new ArrayList<>(tokens);
//		Result result = apply(expression, consumed, tokenList);
//
//		if(result.state == Result.State.NO_MATCH) {
//			result = Result.error(
//				result.node,
//				result.error == null ? new Error(Error.Type.UNKNOWN_ERROR, 0, tokens.size()) : result.error
//			);
//		}
//
//		if(tokenList.isEmpty()) {
//			return result;
//
//		} else {
//			boolean remainingOptional = true;
//			for(Token t : tokens) {
//				if(t.getType() != TokenType.IGNORABLE) {
//					remainingOptional = false;
//					break;
//				}
//			}
//			if(!remainingOptional) {
//				return Result.error(
//						result.node,
//						result.error == null ? new Error(Error.Type.SYMBOLS_REMAINING, consumed.size(), consumed.size()) : result.error
//				);
//			}
//			return result;
//		}
//
//	}
//
//
//
//
//	private static Result apply(Expression expression, List<Token> consumed, List<Token> tokens) {
//
//		TraceElement traceElement = new TraceElement();
//		trace.add(traceElement);
//
//		Result result = null;
//
//		if(ExpressionType.ALTERNATIVE == expression.getType()) {
//			AlternativeExpression alternativeExpression = (AlternativeExpression)expression;
//			result = applyAlternative(alternativeExpression, consumed, tokens);
//		}
//
//		if(ExpressionType.OPTIONAL == expression.getType()) {
//			OptionalExpression optionalExpression = (OptionalExpression)expression;
//			result = applyOptional(optionalExpression, consumed, tokens);
//		}
//
//		if(ExpressionType.REPETITION == expression.getType()) {
//			RepetitionExpression repetitionExpression = (RepetitionExpression)expression;
//			result = applyRepetition(repetitionExpression, consumed, tokens);
//		}
//
//		if(ExpressionType.RULE == expression.getType()) {
//			RuleExpression ruleExpression = (RuleExpression)expression;
//			result = applyRule(ruleExpression, consumed, tokens);
//		}
//
//		if(ExpressionType.SEQUENCE == expression.getType()) {
//			SequenceExpression sequenceExpression = (SequenceExpression)expression;
//			result = applySequence(sequenceExpression, consumed, tokens);
//		}
//
//		if(ExpressionType.TOKEN == expression.getType()) {
//			TokenExpression tokenExpression = (TokenExpression)expression;
//			result = applyToken(tokenExpression, consumed, tokens);
//		}
//
//		traceElement.setExpression(expression);
//		traceElement.setState(result.state);
//		return result;
//	}
//
//
//
//
//	private static Result applyAlternative(AlternativeExpression expression, List<Token> consumed, List<Token> tokens) {
//
//		// no tokens remaining -> is optional -> MATCH; else -> EOS-ERROR
//		if(tokens.isEmpty()) {
//			if(OptionalCheck.isOptional(expression)) {
//				return Result.match(
//						new PlaceholderNode().setExpression(expression)
//				);
//
//			} else {
//				return Result.error(
//						new PlaceholderNode().setExpression(expression).setError(),
//						new Error(Error.Type.UNEXPECTED_END_OF_INPUT, consumed.size(), consumed.size())
//				);
//			}
//		}
//
//		// prepare
//		int indexStart = consumed.size()+1;
//		Token tokenStart = tokens.get(0);
//		List<Result> resultsMatched = new ArrayList<>();
//		Result result = null;
//
//		// apply each expression
//		for(Expression expr : expression.expressions) {
//			result = apply(expr, consumed, tokens);
//
//			// expression matches next token(s) -> consumed token: return result -> did not consume: continue
//			if(result.state == Result.State.MATCH) {
//				resultsMatched.add(result);
//				if(tokens.isEmpty() || tokenStart != tokens.get(0)) {
//					return result;
//				}
//
//			// expression does not match token(s)
//			} else if(result.state == Result.State.NO_MATCH) {
//				continue;
//
//			// expression encountered error -> return error/result
//			} else if(result.state == Result.State.ERROR) {
//				return result;
//			}
//		}
//
//		// return if matched
//		if(resultsMatched.size() > 0) {
//			return resultsMatched.get(0);
//
//		// nothing matched
//		} else {
//
//			// ... because no tokens remaining
//			if(tokens.isEmpty()) {
//				return Result.error(
//						new PlaceholderNode(result.node).setExpression(expression).setError(),
//						new Error(Error.Type.UNEXPECTED_END_OF_INPUT, consumed.size(), consumed.size())
//				);
//
//			// ... because unexpected symbol
//			} else {
//				Set<Token> expected = new HashSet<>();
//				RecommendationProcessor.collectPossibleTokens(expression, expected);
//				return Result.error(
//						new PlaceholderNode().setExpression(expression).setError(),
//						new Error(Error.Type.UNEXPECTED_SYMBOL, consumed.size(), consumed.size(), expected, tokenStart)
//				);
//			}
//		}
//
//
//	}
//
//
//
//
//	private static Result applyOptional(OptionalExpression expression, List<Token> consumed, List<Token> tokens) {
//
//		// no tokens remaining -> MATCH
//		if(tokens.isEmpty()) {
//			return Result.match(
//					new PlaceholderNode().setExpression(expression)
//			);
//
//
//		// tokens remaining
//		} else {
//
//			// apply expression
//			Result result = apply(expression.expression, consumed, tokens);
//
//			// is matching
//			if(result.state == Result.State.MATCH) {
//				return result;
//			}
//
//			// does not match
//			if(result.state == Result.State.NO_MATCH) {
//				return Result.match( result.node );
//			}
//
//			// encountered error
//			if(result.state == Result.State.ERROR) {
//				return result;
//			}
//
//			// none of the above
//			return Result.error(
//					new PlaceholderNode().setExpression(expression).setError(),
//					new Error(Error.Type.INTERNAL_ERROR, consumed.size(), consumed.size())
//			);
//		}
//
//	}
//
//
//
//
//	private static Result applyRepetition(RepetitionExpression expression, List<Token> consumed, List<Token> tokens) {
//
//		// create node
//		Node node = new PlaceholderNode().setExpression(expression);
//
//		// repeat until end (if possible)
//		while (!tokens.isEmpty()) {
//
//			// apply expression
//			Result result = apply(expression.expression, consumed, tokens);
//
//			// matching -> add result and continue
//			if (result.state == Result.State.MATCH) {
//				node.addChild(result.node);
//				continue;
//			}
//
//			// does not match -> return matching nodes
//			if (result.state == Result.State.NO_MATCH) {
//				return Result.match( node );
//			}
//
//			// encountered error -> return error/result
//			if (result.state == Result.State.ERROR) {
//				return result;
//			}
//		}
//
//		// no tokens remaining -> return matching nodes
//		return Result.match( node );
//
//	}
//
//
//
//
//	private static Result applyRule(RuleExpression expression, List<Token> consumed, List<Token> tokens) {
//
//		// apply expression
//		Result result = apply(expression.rule.getExpression(), consumed, tokens);
//
//		// matching
//		if (result.state == Result.State.MATCH) {
//			Node node = new RuleNode(expression.rule).setExpression(expression);
//			node.addChild(result.node);
//			return Result.match( node );
//
//		} else {
//			return result;
//		}
//
//	}
//
//
//
//
//	private static Result applySequence(SequenceExpression expression, List<Token> consumed, List<Token> tokens) {
//
//		// create node
//		Node node = new PlaceholderNode().setExpression(expression);
//
//		// apply each expression
//		for (Expression expr : expression.expressions) {
//			Result result = apply(expr, consumed, tokens);
//
//			// matching -> add node
//			if (result.state == Result.State.MATCH) {
//				node.addChild(result.node);
//			}
//
//			// does not match -> return nomatch and matched nodes
//			if (result.state == Result.State.NO_MATCH) {
//				node.addChild(result.node);
//				return Result.noMatch(node.setError());
//			}
//
//			// encountered error
//			if (result.state == Result.State.ERROR) {
//				node.addChild(result.node);
//				return Result.error(node.setError(), result.error);
//			}
//
//		}
//
//		return Result.match(node);
//	}
//
//
//
//
//	private static Result applyToken(TokenExpression expression, List<Token> consumed, List<Token> tokens) {
//
//
//		// no tokens remaining
//		if (tokens.isEmpty()) {
//			return Result.noMatch(
//					new PlaceholderNode().setExpression(expression).setError(),
//					new Error(Error.Type.UNEXPECTED_END_OF_INPUT, consumed.size(), consumed.size())
//			);
//
//
//		// tokens remaining
//		} else {
//
//			// get next
//			Token next = tokens.get(0);
//
//			// next is undefined -> ERROR: undefined symbol
//			if (next.getType() == TokenType.UNDEFINED) {
//				return Result.error(
//						new PlaceholderNode().setExpression(expression).setError(),
//						new Error(Error.Type.ILLEGAL_CHARACTER, consumed.size(), consumed.size(), new HashSet<>(Collections.singleton(expression.token)), next)
//				);
//
//			// next is ignorable -> consume + apply again
//			} else if (next.getType() == TokenType.IGNORABLE) {
//				consumed.add(tokens.remove(0));
//				return applyToken(expression, consumed, tokens);
//
//			// next is matching token -> MATCH
//			} else if (next == expression.token) {
//				consumed.add(tokens.remove(0));
//
//				return Result.match(
//						new TerminalNode(expression.token).setExpression(expression)
//				);
//
//			// next is not matching token -> NO_MATCH: unexpected symbol
//			} else {
//				return Result.noMatch(
//						new PlaceholderNode().setExpression(expression).setError(),
//						new Error(Error.Type.UNEXPECTED_SYMBOL, consumed.size(), consumed.size(), new HashSet<>(Collections.singleton(expression.token)), next)
//				);
//
//			}
//
//		}
//
//	}
//
//
//}

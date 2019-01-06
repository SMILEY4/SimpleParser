package com.ruegnerlukas.v1.simpleparser.systems;

import com.ruegnerlukas.v1.simpleparser.errors.Error;
import com.ruegnerlukas.v1.simpleparser.expressions.*;
import com.ruegnerlukas.v1.simpleparser.grammar.Grammar;
import com.ruegnerlukas.v1.simpleparser.tokens.Token;
import com.ruegnerlukas.v1.simpleparser.tokens.TokenType;
import com.ruegnerlukas.v1.simpleparser.tree.Node;
import com.ruegnerlukas.v1.simpleparser.tree.PlaceholderNode;
import com.ruegnerlukas.v1.simpleparser.tree.RuleNode;
import com.ruegnerlukas.v1.simpleparser.tree.TerminalNode;

import java.util.*;


public class ExpressionProcessor {


	private static List<TraceElement> trace = new ArrayList<>();




	public static List<TraceElement> getLastTrace() {
		return trace;
	}




	public static Node apply(Grammar grammar, List<Token> tokens) {
		return apply(grammar.getRule(grammar.getStartingRule()).getExpression(), tokens);
	}




	public static Node apply(Expression expression, List<Token> tokens) {
		trace.clear();
		List<Token> consumed = new ArrayList<>();
		List<Token> tokenList = new ArrayList<>(tokens);

		Node root = new PlaceholderNode();
		apply(root, expression, consumed, tokenList);

		if(state == Result.State.NO_MATCH) {
			if(errors.isEmpty()) {
				error(new Error(Error.Type.UNKNOWN_ERROR, 0, tokens.size()));
			} else {
				error(errors.get(errors.size()-1));
			}
		}

		if(tokenList.isEmpty()) {
			return root;

		} else {
			boolean remainingOptional = true;
			for(Token t : tokens) {
				if(t.getType() != TokenType.IGNORABLE) {
					remainingOptional = false;
					break;
				}
			}
			if(!remainingOptional) {
				if(errors.isEmpty()) {
					error(new Error(Error.Type.UNKNOWN_ERROR, 0, tokens.size()));
				} else {
					error(errors.get(errors.size()-1));
				}
			}
			return root;
		}

	}




	private static void apply(Node parent, Expression expression, List<Token> consumed, List<Token> tokens) {

		TraceElement traceElement = new TraceElement();
		trace.add(traceElement);

		if(ExpressionType.ALTERNATIVE == expression.getType()) {
			AlternativeExpression alternativeExpression = (AlternativeExpression)expression;
			applyAlternative(parent, alternativeExpression, consumed, tokens);
		}

		if(ExpressionType.OPTIONAL == expression.getType()) {
			OptionalExpression optionalExpression = (OptionalExpression)expression;
			applyOptional(parent, optionalExpression, consumed, tokens);
		}

		if(ExpressionType.REPETITION == expression.getType()) {
			RepetitionExpression repetitionExpression = (RepetitionExpression)expression;
			applyRepetition(parent, repetitionExpression, consumed, tokens);
		}

		if(ExpressionType.RULE == expression.getType()) {
			RuleExpression ruleExpression = (RuleExpression)expression;
			applyRule(parent, ruleExpression, consumed, tokens);
		}

		if(ExpressionType.SEQUENCE == expression.getType()) {
			SequenceExpression sequenceExpression = (SequenceExpression)expression;
			applySequence(parent, sequenceExpression, consumed, tokens);
		}

		if(ExpressionType.TOKEN == expression.getType()) {
			TokenExpression tokenExpression = (TokenExpression)expression;
			applyToken(parent, tokenExpression, consumed, tokens);
		}

	}




	private static void applyAlternative(Node parent, AlternativeExpression expression, List<Token> consumed, List<Token> tokens) {

		// no tokens remaining -> is optional -> MATCH; else -> EOS-ERROR
		if(tokens.isEmpty()) {
			if(OptionalCheck.isOptional(expression)) {
				match();
				return;
			} else {
				error(new Error(Error.Type.UNEXPECTED_END_OF_INPUT, consumed.size(), consumed.size()));
				return;
			}
		}

		// prepare
		int indexStart = consumed.size()+1;
		Token tokenStart = tokens.get(0);

		List<Node> matchedNodes = new ArrayList<>();

		// apply each expression
		for(Expression expr : expression.expressions) {

			Node tmpParent = new PlaceholderNode().setExpression(expression);
			parent.addChild(tmpParent);
			apply(tmpParent, expr, consumed, tokens);
			parent.removeChild(tmpParent);

			// expression matches peek token(s) -> consumed token: return result -> did not consume: continue
			if(state == Result.State.MATCH) {
				matchedNodes.add(tmpParent);
				if(tokens.isEmpty() || tokenStart != tokens.get(0)) {
					parent.addChild(tmpParent);
					match();
					return;
				}

			// expression does not match token(s)
			} else if(state == Result.State.NO_MATCH) {
				continue;

			// expression encountered error -> return error/result
			} else if(state == Result.State.ERROR) {
				error();
				return;
			}
		}

		// return if matched
		if(matchedNodes.size() > 0) {
			parent.addChild(matchedNodes.get(0));
			match();
			return;

		// nothing matched
		} else {

			// ... because no tokens remaining
			if(tokens.isEmpty()) {
				error(new Error(Error.Type.UNEXPECTED_END_OF_INPUT, consumed.size(), consumed.size()));
				return;

			// ... because unexpected symbol
			} else {
				Set<Token> expected = new HashSet<>();
				RecommendationProcessor.collectPossibleTokens(expression, expected);
				error(new Error(Error.Type.UNEXPECTED_SYMBOL, consumed.size(), consumed.size(), expected, tokenStart));
				return;
			}
		}


	}




	private static void applyOptional(Node parent, OptionalExpression expression, List<Token> consumed, List<Token> tokens) {

		// no tokens remaining -> MATCH
		if(tokens.isEmpty()) {
			parent.addChild(new PlaceholderNode().setExpression(expression));
			match();


		// tokens remaining
		} else {

			// apply expression
			apply(parent, expression.expression, consumed, tokens);

			// is matching
			if(state == Result.State.MATCH) {
				match();
				return;
			}

			// does not match
			if(state == Result.State.NO_MATCH) {
				match();
				return;
			}

			// encountered error
			if(state == Result.State.ERROR) {
				error();
				return;
			}

			// none of the above
			error(new Error(Error.Type.INTERNAL_ERROR, consumed.size(), consumed.size()));
		}

	}




	private static void applyRepetition(Node parent, RepetitionExpression expression, List<Token> consumed, List<Token> tokens) {

		Node node = new PlaceholderNode().setExpression(expression);
		parent.addChild(node);

		// repeat until end (if possible)
		while (!tokens.isEmpty()) {

			// apply expression
			apply(node, expression.expression, consumed, tokens);

			// matching -> add result and continue
			if (state == Result.State.MATCH) {
				match();
				continue;
			}

			// does not match -> return matching nodes
			if (state == Result.State.NO_MATCH) {
				match();
				return;
			}

			// encountered error -> return error/result
			if (state == Result.State.ERROR) {
				error();
				return;
			}
		}

		// no tokens remaining -> return matching nodes
		match();

	}




	private static void applyRule(Node parent, RuleExpression expression, List<Token> consumed, List<Token> tokens) {

		// apply expression
		Node node = new RuleNode(expression.rule).setExpression(expression);
		parent.addChild(node);

		apply(node, expression.rule.getExpression(), consumed, tokens);


	}




	private static void applySequence(Node parent, SequenceExpression expression, List<Token> consumed, List<Token> tokens) {

		// create node
		Node node = new PlaceholderNode().setExpression(expression);
		parent.addChild(node);

		// apply each expression
		for (Expression expr : expression.expressions) {
			apply(node, expr, consumed, tokens);

			// matching -> add node
			if (state == Result.State.MATCH) {
				match();
				continue;
			}

			// does not match -> return nomatch and matched nodes
			if (state == Result.State.NO_MATCH) {
				noMatch();
				return;
			}

			// encountered error
			if (state == Result.State.ERROR) {
				error();
				return;
			}

		}

		match();
	}




	private static void applyToken(Node parent, TokenExpression expression, List<Token> consumed, List<Token> tokens) {


		// no tokens remaining
		if (tokens.isEmpty()) {
			noMatch(new Error(Error.Type.UNEXPECTED_END_OF_INPUT, consumed.size(), consumed.size()));


		// tokens remaining
		} else {

			// get peek
			Token next = tokens.get(0);

			// peek is undefined -> ERROR: undefined symbol
			if (next.getType() == TokenType.UNDEFINED) {
				error(new Error(Error.Type.ILLEGAL_CHARACTER, consumed.size(), consumed.size(), new HashSet<>(Collections.singleton(expression.token)), next));

			// peek is ignorable -> consume + apply again
			} else if (next.getType() == TokenType.IGNORABLE) {
				consumed.add(tokens.remove(0));
				applyToken(parent, expression, consumed, tokens);

			// peek is matching token -> MATCH
			} else if (next == expression.token) {
				consumed.add(tokens.remove(0));
				parent.addChild(new TerminalNode(expression.token).setExpression(expression));
				match();

			// peek is not matching token -> NO_MATCH: unexpected symbol
			} else {
				noMatch(new Error(Error.Type.UNEXPECTED_SYMBOL, consumed.size(), consumed.size(), new HashSet<>(Collections.singleton(expression.token)), next));
			}

		}

	}




	public static List<Error> errors = new ArrayList<>();
	public static Result.State state = Result.State.MATCH;


	public static void match() {
		state = Result.State.MATCH;
	}


	public static void noMatch() {
		state = Result.State.NO_MATCH;
	}


	public static void noMatch(Error error) {
		state = Result.State.NO_MATCH;
		errors.add(error);
	}


	public static void error() {
		state = Result.State.ERROR;
	}


	public static void error(Error error) {
		state = Result.State.ERROR;
		errors.add(error);
	}

}

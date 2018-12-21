package com.ruegnerlukas.simpleparser.grammar;

import com.ruegnerlukas.simpleparser.expressions.*;
import com.ruegnerlukas.simpleparser.grammar.Grammar;
import com.ruegnerlukas.simpleparser.grammar.Rule;

import java.security.InvalidParameterException;

public class GrammarBuilder {

	
	private Grammar grammar = new Grammar();
	
	
	
	
	/**
	 * define the starting/root production rule of the grammar
	 * @param rule			the name of the production rule
	 * @param expression	the expression for the rule
	 * */
	public void defineRootNonTerminal(String rule, Expression expression) {
		if(grammar.getRule(rule) == null) {
			grammar.addRules(rule);
		}
		grammar.defineRule(rule, expression);
		grammar.setStartingRule(rule);
	}
	
	
	
	
	/**
	 * define the a production rule of the grammar
	 * @param rule			the name of the production rule
	 * @param expression	the expression for the rule
	 * */
	public void defineNonTerminal(String rule, Expression expression) {
		if(grammar.getRule(rule) == null) {
			grammar.addRules(rule);
		}
		grammar.defineRule(rule, expression);
	}
	
	
	
	
	/**
	 * @return "E0 | E1 | ... | En"
	 * */
	public Expression alternative(Expression... expressions) {
		return new AlternativeExpression(expressions);
	}
	
	
	
	
	/**
	 * @return "T0 | T1 | ... | Tn"
	 * */
	public Expression alternative(String... terminals) {
		Expression[] terminalList = new Expression[terminals.length];
		int i = 0;
		for(String terminal : terminals) {
			terminalList[i++] = terminal(terminal);
		}
		return new AlternativeExpression(terminalList);
	}




	/**
	 * @return "Tmin | ... | Tmax"
	 * */
	public Expression alternative(int min, int max) {
		if(min >= max) {
			throw new InvalidParameterException("min must be smaller than max");
		}

		Expression[] terminalList = new Expression[max-min];
		int i = 0;
		for(int j=min; j<=max; j++) {
			terminalList[i++] = terminal(Integer.toString(j));
		}
		return new AlternativeExpression(terminalList);
	}
	
	
	
	
	/**
	 * @return "[E]"
	 * */
	public Expression optional(Expression expression) {
		return new OptionalExpression(expression);
	}	
	
	
	
	
	/**
	 * @return "{E}"   or   "(E)*"
	 */
	public Expression zeroOrMore(Expression expression) {
		return new RepetitionExpression(expression);
	}	
	
	
	
	/**
	 * @param nonTerminal the name of the Non-Terminal / Production Rule
	 * @return NT
	 * */
	public Expression nonTerminal(String nonTerminal) {
		if(grammar.getRule(nonTerminal) == null) {
			grammar.addRules(nonTerminal);
		}
		return new RuleExpression(grammar.getRule(nonTerminal));
	}
	
	
	
	
	/**
	 * @return E0 E1 ... En
	 * */
	public Expression sequence(Expression... expressions) {
		return new SequenceExpression(expressions);
	}
	
	
	
	
	/**
	 * @param terminal the terminal/tokens/symbol
	 * @return T
	 * */
	public Expression terminal(String terminal) {
		if(grammar.getToken(terminal) == null) {
			grammar.addTokens(terminal);
		}
		return new TokenExpression(grammar.getToken(terminal));
	}
	
	
	
	/**
	 * @return the grammar
	 * */
	public Grammar get() {
		return this.grammar;
	}
	
	
	/**
	 * @return true, if the grammar is valid
	 * */
	public boolean checkValid() {
		boolean validRules = validateRules();
		return validRules;
	}
	
	
	
	
	private boolean validateRules() {
		for(String name : grammar.getRules()) {
			Rule rule = grammar.getRule(name);
			if(rule.expression == null) {
				return false;
			}
		}
		return true;
	}
	
	
}

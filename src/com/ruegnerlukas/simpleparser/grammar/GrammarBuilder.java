package com.ruegnerlukas.simpleparser.grammar;


import com.ruegnerlukas.simpleparser.expressions.*;

import java.security.InvalidParameterException;

public class GrammarBuilder {


	private Grammar grammar = new Grammar();




	/**
	 * define the starting production rule of the grammar. This rule can not be defined twice. The first definition will be kept.
	 *
	 * @param rule       the name of the production rule
	 * @param expression the expression for the rule
	 */
	public void defineRootNonTerminal(String rule, Expression expression) {
		if (grammar.hasStartingRule()) {
			return;
		}
		if (grammar.getRule(rule) == null) {
			grammar.addRules(rule);
		}
		grammar.defineRule(rule, expression);
		grammar.setStartingRule(rule);
	}




	/**
	 * define a new production rule of the grammar
	 *
	 * @param rule       the name of the production rule
	 * @param expression the expression for the rule
	 */
	public void defineNonTerminal(String rule, Expression expression) {
		if (grammar.getRule(rule) == null) {
			grammar.addRules(rule);
		}
		grammar.defineRule(rule, expression);
	}




	/**
	 * "E0 | E1 | ... | En"
	 */
	public Expression alternative(Expression... expressions) {
		return new AlternativeExpression(expressions);
	}




	/**
	 * "T0 | T1 | ... | Tn"
	 */
	public Expression alternative(String... terminals) {
		Expression[] terminalList = new Expression[terminals.length];
		int i = 0;
		for (String terminal : terminals) {
			terminalList[i++] = terminal(terminal);
		}
		return new AlternativeExpression(terminalList);
	}




	/**
	 * "Tmin | ... | Tmax"
	 */
	public Expression alternative(int min, int max) {
		if (min >= max) {
			throw new InvalidParameterException("min must be smaller than max");
		}

		Expression[] terminalList = new Expression[max - min + 1];
		int i = 0;
		for (int j = min; j <= max; j++) {
			terminalList[i++] = terminal(Integer.toString(j));
		}
		return new AlternativeExpression(terminalList);
	}




	/**
	 * "[E]"
	 */
	public Expression optional(Expression expression) {
		return new OptionalExpression(expression);
	}




	/**
	 * "{E}"   or   "(E)*"
	 */
	public Expression zeroOrMore(Expression expression) {
		return new RepetitionExpression(expression);
	}




	/**
	 * "NT"
	 *
	 * @param nonTerminal the name of the Non-Terminal / Production Rule
	 */
	public Expression nonTerminal(String nonTerminal) {
		if (grammar.getRule(nonTerminal) == null) {
			grammar.addRules(nonTerminal);
		}
		return new RuleExpression(grammar.getRule(nonTerminal));
	}




	/**
	 * "E0 E1 ... En"
	 */
	public Expression sequence(Expression... expressions) {
		return new SequenceExpression(expressions);
	}




	/**
	 * "T"
	 *
	 * @param terminal the terminal/tokens/symbol
	 */
	public Expression terminal(String terminal) {
		if (grammar.getToken(terminal) == null) {
			grammar.addTokens(terminal);
		}
		return new TokenExpression(grammar.getToken(terminal));
	}




	/**
	 * {name:String}
	 *
	 * @param varname  the name of the variable
	 * @param datatype the type of the variable. can be String, Integer, Double, Boolean
	 */
	public Expression variable(String varname, Class<?> datatype) {
		if (grammar.getVariable(varname) == null) {
			grammar.addVariable(varname, datatype);
		}
		return new VariableExpression(grammar.getVariable(varname));
	}




	/**
	 * @return the created grammar
	 */
	public Grammar get() {
		return this.grammar;
	}

}
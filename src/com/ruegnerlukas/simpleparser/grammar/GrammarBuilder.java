package com.ruegnerlukas.simpleparser.grammar;

import com.ruegnerlukas.simpleparser.grammar.expressions.AlternativeExpression;
import com.ruegnerlukas.simpleparser.grammar.expressions.Expression;
import com.ruegnerlukas.simpleparser.grammar.expressions.OptionalExpression;
import com.ruegnerlukas.simpleparser.grammar.expressions.RepetitionExpression;
import com.ruegnerlukas.simpleparser.grammar.expressions.RuleExpression;
import com.ruegnerlukas.simpleparser.grammar.expressions.SequenceExpression;
import com.ruegnerlukas.simpleparser.grammar.expressions.TokenExpression;

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
	 * @param terminal the terminal/token/symbol
	 * @return T
	 * */
	public Expression terminal(String terminal) {
		if(grammar.getToken(terminal) == null) {
			grammar.addTokens(terminal);
		}
		return new TokenExpression(grammar.getToken(terminal));
	}
	
	
	
	
	/**
	 * validates and returns the grammar
	 * @return the grammar
	 * */
	public Grammar get() {
		return this.grammar;
	}
	
	
}
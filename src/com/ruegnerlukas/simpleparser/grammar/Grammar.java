package com.ruegnerlukas.simpleparser.grammar;


import com.ruegnerlukas.simpleparser.Token;
import com.ruegnerlukas.simpleparser.Variable;
import com.ruegnerlukas.simpleparser.expressions.Expression;

import java.util.*;

public class Grammar {


	private Map<String, Rule> rules = new HashMap<>();
	private Map<String, Token> tokens = new HashMap<>();
	private Map<String, Variable> variables = new HashMap<>();
	private Rule startingRule;




	protected Grammar() {
	}




	/**
	 * adds new rules to this grammar. The rules have to be defined with {@code defineRule(...)}.
	 * A rule with a given name can only exists once and is case-insensitive.
	 * If a rule with the name already exists, it does not get replaced.
	 *
	 * @param names the name(s) of the rules (case-insensitive)
	 */
	protected void addRules(String... names) {
		for (String name : names) {
			if (getRule(name) == null) {
				Rule rule = new Rule(name.toUpperCase());
				rules.put(rule.getName(), rule);
			}
		}
	}




	/**
	 * adds new tokens/symbols to this grammar.
	 * A given symbol can only exist once. If a token already exists, it does not get replaced.
	 *
	 * @param symbols the tokens/symbols to add
	 */
	protected void addTokens(String... symbols) {
		for (String symbol : symbols) {
			if (getToken(symbol) == null) {
				Token token = new Token(symbol);
				tokens.put(token.getSymbol(), token);
			}
		}
	}




	/**
	 * adds a new variable with given name and type to this grammar.
	 * The name of the variable must be unique or it will not be added to the list
	 *
	 * @param varname  the name of the variable
	 * @param datatype the type of the variable
	 */
	protected void addVariable(String varname, Class<?> datatype) {
		if (getVariable(varname) == null) {
			this.variables.put(varname, new Variable(varname, datatype));
		}
	}




	/**
	 * adds new variables to this grammar.
	 * The name of a variable must be unique or it will not be added to the list
	 *
	 * @param variables the variables to add
	 */
	protected void addVariables(Variable... variables) {
		for (Variable variable : variables) {
			if (getVariable(variable.varname) == null) {
				this.variables.put(variable.varname, variable);
			}
		}
	}




	/**
	 * Defines the rule with the given name. The rule has to be added with {@code addRules(...)} first.
	 *
	 * @param name       the name of the rule (case-insensitive)
	 * @param expression the expression for the given rule
	 * @return false, if the rule with the given name does not exist
	 */
	protected boolean defineRule(String name, Expression expression) {
		Rule rule = rules.get(name.toUpperCase());
		if (rule == null) {
			return false;
		} else {
			rule.define(expression);
			return true;
		}
	}




	/**
	 * Sets the starting rule of this grammar.
	 *
	 * @param name the name of the rule (case-insensitive)
	 * @return false, if the rule with the given name does not exist
	 */
	protected boolean setStartingRule(String name) {
		Rule rule = getRule(name);
		if (rule == null) {
			return false;
		} else {
			startingRule = rule;
			return true;
		}
	}




	/**
	 * @return the rule with the given name (case-insensitive)
	 */
	public Rule getRule(String name) {
		return rules.get(name.toUpperCase());
	}




	/**
	 * @return a unmodifiable set of the names of all rules
	 */
	public Set<String> getRules() {
		return Collections.unmodifiableSet(rules.keySet());
	}




	/**
	 * @return the name of the starting rule
	 */
	public String getStartingRule() {
		return startingRule.getName();
	}




	/**
	 * @return true, if the grammar has a starting rule
	 */
	public boolean hasStartingRule() {
		return startingRule != null;
	}




	/**
	 * @return the token with the given symbol or null
	 */
	protected Token getToken(String symbol) {
		return tokens.get(symbol);
	}




	/**
	 * @return a copy of the list of all tokens
	 */
	public List<Token> getTokens() {
		return new ArrayList<>(tokens.values());
	}




	/**
	 * @return the variable with the given name or null
	 */
	public Variable getVariable(String varname) {
		return variables.get(varname);
	}




	/**
	 * @return a copy of the list of all variables
	 */
	public List<Variable> getVariables() {
		return new ArrayList<>(variables.values());
	}


}
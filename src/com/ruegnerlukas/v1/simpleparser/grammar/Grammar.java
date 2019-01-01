package com.ruegnerlukas.v1.simpleparser.grammar;

import com.ruegnerlukas.v1.simpleparser.expressions.Expression;
import com.ruegnerlukas.v1.simpleparser.tokens.Token;

import java.util.*;
import java.util.Map.Entry;

public class Grammar {

	
	private Map<String,Rule> rules = new HashMap<>();
	private Map<String,Token> tokens = new HashMap<>();
	private Rule startingRule;




	protected Grammar() {
	}






	/**
	 * adds new rules to this grammar. The rules have to be defined with {@code defineRule(...)}.
	 * A rule with a given name can only exists once and is case-insensitive.
	 * If a rule with the name already exists, it does not get replaced.
	 * @param names the name(s) of the rules (case-insensitive)
	 * */
	protected void addRules(String... names) {
		for(String name : names) {
			if(getRule(name) == null) {
				Rule rule = new Rule(name.toUpperCase());
				rules.put(rule.getName(), rule);
			}
		}
	}




	/**
	 * adds new token/symbol to this grammar.
	 * A given symbol can only exist once. If a token already exists, it does not get replaced.
	 * @param symbols the tokens/symbols to add
	 * */
	protected void addTokens(String... symbols) {
		for(String symbol : symbols) {
			if(getToken(symbol) == null) {
				Token token = Token.token(symbol);
				tokens.put(token.getSymbol(), token);
			}
		}
	}




	/**
	 * Defines the rule with the given name. The rule has to be added with {@code addRules(...)} first.
	 * @param name the name of the rule (case-insensitive)
	 * @param expression the expression for the given rule
	 * @return false, if the rule with the given name does not exist
	 * */
	protected boolean defineRule(String name, Expression expression) {
		Rule rule = rules.get(name.toUpperCase());
		if(rule == null) {
			return false;
		} else {
			rule.define(expression);
			return true;
		}
	}




	/**
	 * Sets the starting rule of this grammar.
	 * @param name the name of the rule (case-insensitive)
	 * @return false, if the rule with the given name does not exist
	 * */
	protected boolean setStartingRule(String name) {
		Rule rule = getRule(name);
		if(rule == null) {
			return false;
		} else {
			startingRule = rule;
			return true;
		}
	}




	/**
	 * @return the rule with the given name (case-insensitive)
	 * */
	public Rule getRule(String name) {
		return rules.get(name.toUpperCase());
	}




	/**
	 * @return a unmodifiable set of the names of all rules
	 * */
	public Set<String> getRules() {
		return Collections.unmodifiableSet(rules.keySet());
	}




	/**
	 * @return the name of the starting rule
	 * */
	public String getStartingRule() {
		return startingRule.getName();
	}




	/**
	 * @return true, if the grammar has a starting rule
	 * */
	public boolean hasStartingRule() {
		return startingRule != null;
	}




	/**
	 * @return the token with the given symbol
	 * */
	protected Token getToken(String symbol) {
		return tokens.get(symbol);
	}




	/**
	 * @return a copy of the list of all tokens
	 * */
	public List<Token> getTokens() {
		List<Token> list = new ArrayList<>();
		for(Entry<String, Token> entry : tokens.entrySet()) {
			list.add(entry.getValue());
		}
		return list;
	}


}
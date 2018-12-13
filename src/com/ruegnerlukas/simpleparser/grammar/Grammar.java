package com.ruegnerlukas.simpleparser.grammar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.ruegnerlukas.simpleparser.grammar.expressions.Expression;

public class Grammar {

	
	private Map<String,Rule> rules = new HashMap<String,Rule>();
	private Map<String,Token> tokens = new HashMap<String,Token>();
	private String startingRule;
	
	
	
	
	protected Grammar() {
	}
	
	
	
	
	protected void addRules(String... names) {
		for(String name : names) {
			Rule rule = new Rule(name.toUpperCase());
			rules.put(rule.name, rule);
		}
	}
	
	
	
	
	protected boolean addTokens(String... symbols) {
		for(String symbol : symbols) {
			Token atom = new Token(symbol);
			tokens.put(atom.symbol, atom);
		}
		return true;
	}
	
	
	
	
	protected boolean defineRule(String name, Expression op) {
		Rule rule = rules.get(name.toUpperCase());
		if(rule == null) {
			return false;
		} else {
			rule.expression = op;
			return true;
		}
	}
	
	
	
	
	protected boolean setStartingRule(String name) {
		this.startingRule = name;
		return rules.containsKey(name.toUpperCase());
	}
	
	
	
	
	public Rule getRule(String name) {
		return rules.get(name.toUpperCase());
	}
	
	
	
	
	public Set<String> getRules() {
		return Collections.unmodifiableSet(rules.keySet());
	}
	
	
	
	
	public String getStartingRule() {
		return startingRule;
	}
	
	
	
	
	public Token getToken(String symbol) {
		return tokens.get(symbol);
	}
	
	
	
	
	public List<Token> getTokens() {
		List<Token> list = new ArrayList<Token>();
		for(Entry<String,Token> entry : tokens.entrySet()) {
			list.add(entry.getValue());
		}
		return list;
	}
	
	
	
	
}

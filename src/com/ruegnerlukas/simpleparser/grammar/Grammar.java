package com.ruegnerlukas.simpleparser.grammar;

import com.ruegnerlukas.simpleparser.expressions.Expression;
import com.ruegnerlukas.simpleparser.tokens.Token;

import java.util.*;
import java.util.Map.Entry;

public class Grammar {

	
	private Map<String,Rule> rules = new HashMap<String,Rule>();
	private Map<String,Token> tokens = new HashMap<String,Token>();
	private String startingRule;




	public Grammar() {
	}




	public void addRules(String... names) {
		for(String name : names) {
			Rule rule = new Rule(name.toUpperCase());
			rules.put(rule.name, rule);
		}
	}




	public boolean addTokens(String... symbols) {
		for(String symbol : symbols) {
			Token atom = new Token(symbol);
			tokens.put(atom.symbol, atom);
		}
		return true;
	}




	public boolean defineRule(String name, Expression op) {
		Rule rule = rules.get(name.toUpperCase());
		if(rule == null) {
			return false;
		} else {
			rule.expression = op;
			return true;
		}
	}




	public boolean setStartingRule(String name) {
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
		for(Entry<String, Token> entry : tokens.entrySet()) {
			list.add(entry.getValue());
		}
		return list;
	}




	public String createDotGraph() {
		Expression start = getRule(getStartingRule()).expression;
		Set<Expression> visited = new HashSet<>();

		StringBuilder builder = new StringBuilder();
		builder.append("digraph G {").append(System.lineSeparator());
		builder.append("    node [style=filled];").append(System.lineSeparator());
		builder.append("    X -> ").append(start).append(System.lineSeparator());
		builder.append("    X [color=\"1.0 1.0 1.0\"];").append(System.lineSeparator());
		start.createDotGraph(visited, builder);
		builder.append('}').append(System.lineSeparator());

		return builder.toString();
	}
	
	
	
}

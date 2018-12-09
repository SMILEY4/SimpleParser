package com.ruegnerlukas.simpleparser.grammar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.ruegnerlukas.simpleparser.grammar.ruleops.Op;

public class Grammar {

	
	private Map<String,Rule> rules = new HashMap<String,Rule>();
	private Map<String,Atom> atoms = new HashMap<String,Atom>();
	private String startingRule;
	
	
	
	
	public boolean predefineRules(String... names) {
		rules.clear();
		for(String name : names) {
			Rule rule = new Rule(name.toUpperCase());
			rules.put(rule.name, rule);
		}
		return true;
	}
	
	
	
	public boolean predefineAtoms(String... symbols) {
		atoms.clear();
		for(String symbol : symbols) {
			Atom atom = new Atom(symbol);
			atoms.put(atom.symbol, atom);
		}
		return true;
	}
	
	
	
	public boolean defineRule(String name, Op op) {
		Rule rule = rules.get(name);
		if(rule == null) {
			return false;
		} else {
			rule.op = op;
			return true;
		}
	}
	
	
	
	public boolean setStartingRule(String name) {
		this.startingRule = name;
		return rules.containsKey(name);
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
	
	
	
	
	public Atom getAtom(String symbol) {
		return atoms.get(symbol);
	}
	
	
	
	
	public List<Atom> getAtoms() {
		List<Atom> list = new ArrayList<Atom>();
		for(Entry<String,Atom> entry : atoms.entrySet()) {
			list.add(entry.getValue());
		}
		return list;
	}
	
	
}

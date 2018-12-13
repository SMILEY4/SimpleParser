package com.ruegnerlukas.simpleparser.grammar.expressions;

import java.util.List;

import com.ruegnerlukas.simpleparser.grammar.Token;
import com.ruegnerlukas.simpleparser.tree.Node;

public abstract class Expression {
	public abstract Node apply(List<Token> tokens);
}

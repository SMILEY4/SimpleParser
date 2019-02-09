# SimpleParser


### Creating a grammar

```java
// OR_EXPRESSION 	-> AND_EXPRESSION {"or" AND_EXPRESSION}
// AND_EXPRESSION 	-> COMPONENT {"and" COMPONENT}
// COMPONENT 		-> STATEMENT | ( "(" EXPRESSION ")"
// STATEMENT		-> "expr"

GrammarBuilder gb = new GrammarBuilder();

// OR_EXPRESSION 	-> AND_EXPRESSION {"or" AND_EXPRESSION}
gb.defineRootNonTerminal("OR_EXPRESSION",
 gb.sequence(
  gb.nonTerminal("AND_EXPRESSION"),
  gb.zeroOrMore(
   gb.sequence(
    gb.terminal("or"),
    gb.nonTerminal("AND_EXPRESSION")
   )
  )
 )
);

// AND_EXPRESSION 	-> COMPONENT {"and" COMPONENT}
gb.defineNonTerminal("AND_EXPRESSION",
 gb.sequence(
  gb.nonTerminal("COMPONENT"),
  gb.zeroOrMore(
   gb.sequence(
    gb.terminal("and"),
    gb.nonTerminal("COMPONENT")
   )
  )
 )
);

// COMPONENT 		-> STATEMENT | ( "(" EXPRESSION ")"
gb.defineNonTerminal("COMPONENT",
 gb.alternative(
  gb.nonTerminal("STATEMENT"),
  gb.sequence(
   gb.terminal("("),
   gb.nonTerminal("OR_EXPRESSION"),
   gb.terminal(")")
  )
 )
);

// STATEMENT	-> "expr"
gb.defineNonTerminal("STATEMENT", gb.terminal("e"));
```



### Parsing a String

```java
StringParser parser = new StringParser(grammar);
ParserResult result = parser.parse(strInput, false, false);
```



### Visualizing Grammar or ParserResult as a tree/graph

```java
String tree = DotTreeBuilder.build(result.getRoot())
String graph = DotGrammarBuilder.build(grammar, result.getTrace());
```
"tree" and "graph" can be visualized for example with "Graphviz" or in the browser with http://viz-js.com/

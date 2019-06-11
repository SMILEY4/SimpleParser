# SimpleParser


### Features
- fast to setup and easy to use
- can parse ebnf-grammar
   - terminal strings
   - groupings
   - optionals
   - alternations
   - repetitions
   - rules
   - variables (not ebnf, can be any string,number or boolean)
- result as detailed or minimized tree (unnecessary nodes can be removed)



### Creating a grammar

```java
// OR_EXPRESSION 	-> AND_EXPRESSION {"or" AND_EXPRESSION}
// AND_EXPRESSION 	-> COMPONENT {"and" COMPONENT}
// COMPONENT 		-> STATEMENT | ( "(" EXPRESSION ")"
// STATEMENT		-> "e"

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

// STATEMENT	-> "e"
gb.defineNonTerminal("STATEMENT", gb.terminal("e"));

// GRAMMAR
Grammar grammar = gb.get();
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

![alt text](https://i.imgur.com/HB8rLNe.png)
the grammar as a graph

![alt text](https://i.imgur.com/JdJHDs9.png)
resuling tree of the input "e and (e or e)"



### Debugging

If the input does not match the grammar, an error is returned with a partial tree that also contains error-nodes specifying what went wrong.

![alt text](https://i.imgur.com/jkrygwC.png)
resuling tree of the input "e and (

While parsing an input, the parser generates a "trace". A trace records every step and its state (match,no-match,error).
The output trace can be output together with the final tree as a .dot-graph.

![alt text](https://i.imgur.com/lWg9Ic8.png)
resuling tree of the input "e and (


//package com.ruegnerlukas.test;
//
//import com.ruegnerlukas.simpleparser.grammar.Grammar;
//import com.ruegnerlukas.simpleparser.tokens.Token;
//import com.ruegnerlukas.simpleparser.tokens.UndefinedToken;
//import com.ruegnerlukas.simpleparser.expressions.Expression;
//import com.ruegnerlukas.simpleparser.grammar.GrammarBuilder;
//import com.ruegnerlukas.simpleparser.expressions.Result;
//import com.ruegnerlukas.simpleparser.tokens.Tokenizer;
//import com.ruegnerlukas.simpleparser.tree.Node;
//import com.ruegnerlukas.simpleparser.tree.TreeBuilder;
//import com.ruegnerlukas.utils.GraphViz;
//
//import javax.swing.*;
//import javax.swing.border.LineBorder;
//import javax.swing.event.DocumentEvent;
//import javax.swing.event.DocumentListener;
//import javax.swing.text.DefaultStyledDocument;
//import javax.swing.text.Style;
//import javax.swing.text.StyleConstants;
//import javax.swing.text.StyleContext;
//import java.awt.*;
//import java.awt.event.ComponentEvent;
//import java.awt.event.ComponentListener;
//import java.awt.image.BufferedImage;
//import java.util.List;
//
//public class GraphRenderer {
//
//	public static final Grammar GRAMMAR = buildGrammar();
//
//	public static BufferedImage imgGraph = null;
//
//
//
//	public static void main(String[] args) {
//
//		System.out.println(GRAMMAR.createDotGraph());
//
//		JFrame frame = new JFrame();
//		frame.setTitle("Simple Parser Test - Lukas Ruegner (2018)");
//		frame.setSize(1280, 900);
//		frame.setLocationRelativeTo(null);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setResizable(true);
//
//		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
//
//		// tokens
//		final JLabel labelTokens = new JLabel();
//		labelTokens.setBorder(new LineBorder(Color.BLACK));
//		labelTokens.setMinimumSize(new Dimension(10, 50));
//		labelTokens.setMaximumSize(new Dimension(10000, 50));
//
//		// graph rendering
//		final JPanel graphPanel = new JPanel() {
//			@Override
//			public void paintComponent(Graphics gfx) {
//				super.paintComponent(gfx);
//
//				if(imgGraph == null) {
//					return;
//				}
//
//				Graphics2D g = (Graphics2D)gfx;
//				g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//
//				int screenWidth = this.getWidth();
//				int screenHeight = this.getHeight();
//
//				int imgWidth = imgGraph.getWidth();
//				int imgHeight = imgGraph.getHeight();
//
//				float sx = (float)screenWidth / (float)imgWidth;
//				float sy = (float)screenHeight / (float)imgHeight;
//				float s = Math.min(sx, sy);
//
//				g.drawImage(imgGraph, 0, 0, (int)(imgWidth*s), (int)(imgHeight*s), null);
//			}
//		};
//
//		// styles
//		final StyleContext styleContext = new StyleContext();
//		final DefaultStyledDocument doc = new DefaultStyledDocument(styleContext);
//
//		final Style defaultStyle = styleContext.addStyle("Default", null);
//		defaultStyle.addAttribute(StyleConstants.Foreground, Color.BLACK);
//		defaultStyle.addAttribute(StyleConstants.FontSize, 14);
//		defaultStyle.addAttribute(StyleConstants.FontFamily, "Monospaced");
//		defaultStyle.addAttribute(StyleConstants.Bold, false);
//
//		final Style undefinedStyle = styleContext.addStyle("Undefined", null);
//		undefinedStyle.addAttribute(StyleConstants.Foreground, Color.RED);
//		undefinedStyle.addAttribute(StyleConstants.FontSize, 14);
//		undefinedStyle.addAttribute(StyleConstants.FontFamily, "Monospaced");
//		undefinedStyle.addAttribute(StyleConstants.Bold, true);
//
//		final Style errorStyle = styleContext.addStyle("SyntaxError", null);
//		errorStyle.addAttribute(StyleConstants.Foreground, Color.RED);
//		errorStyle.addAttribute(StyleConstants.FontSize, 14);
//		errorStyle.addAttribute(StyleConstants.FontFamily, "Monospaced");
//		errorStyle.addAttribute(StyleConstants.Bold, true);
//		errorStyle.addAttribute(StyleConstants.Underline, true);
//
//		// text input
//		JTextPane inputField = new JTextPane(doc);
//		inputField.setMaximumSize(new Dimension(100000, 50));
//		inputField.getDocument().addDocumentListener(new DocumentListener() {
//			@Override
//			public void insertUpdate(DocumentEvent e) {
//				update(e);
//			}
//			@Override
//			public void removeUpdate(DocumentEvent e) {
//				update(e);
//			}
//			@Override
//			public void changedUpdate(DocumentEvent e) {
//			}
//
//
//			private void update(DocumentEvent e) {
//				String strInput = inputField.getText();
//
//				Tokenizer tokenizer = new Tokenizer(strInput, GRAMMAR);
//				final List<Token> tokens = tokenizer.tokenize(true);
//
//				Expression.possible.clear();
//				TreeBuilder treeBuilder = new TreeBuilder();
//				final Result result = treeBuilder.build(GRAMMAR, tokens);
//				final Node root = result.node;
//
//
//				final DefaultStyledDocument doc = (DefaultStyledDocument)e.getDocument();
//				SwingUtilities.invokeLater(new Runnable() {
//					@Override
//					public void run() {
//
//						doc.setCharacterAttributes(0, strInput.length(), defaultStyle, true);
//
//						int offset = 0;
//						for(Token token : tokens) {
//							if(token instanceof UndefinedToken) {
//								doc.setCharacterAttributes(offset, token.symbol.length(), undefinedStyle, true);
//							}
//							offset += token.symbol.length();
//						}
//
//						if(result.state != Result.State.MATCH) {
//							int errorIndex = 0;
//							for(int i=0; i<result.errorTokenIndex; i++) {
//								errorIndex += tokens.get(i).symbol.length();
//							}
//
//							if(errorIndex == strInput.length()) {
//								errorIndex -= tokens.get(tokens.size()-1).symbol.length();
//								doc.setCharacterAttributes(errorIndex, strInput.length()+1, errorStyle, true);
//							} else {
//								doc.setCharacterAttributes(errorIndex, strInput.length(), errorStyle, true);
//							}
//
//						}
//
//
//					}
//				});
//
//				String strPossible = "";
//				for(Token token : Expression.possible) {
//					strPossible += token.symbol + "  ";
//				}
//				labelTokens.setText(strPossible);
//
//				System.out.println();
//				System.out.println(strInput);
//				if(result.state != Result.State.MATCH) {
//					System.err.println(result.message);
//				}
////
////				String dot = root.createDotTree();
////				imgGraph = renderDotGraph(dot);
////				graphPanel.repaint();
//
//			}
//
//
//		});
//		frame.getContentPane().add(inputField);
//
//		frame.getContentPane().add(labelTokens);
//
//		frame.getContentPane().addComponentListener(new ComponentListener() {
//			@Override public void componentResized(ComponentEvent e) {
//				graphPanel.repaint();
//			}
//			@Override public void componentMoved(ComponentEvent e) { }
//			@Override public void componentShown(ComponentEvent e) { }
//			@Override public void componentHidden(ComponentEvent e) { }
//		});
//		frame.getContentPane().add(graphPanel);
//
//		frame.setVisible(true);
//
//	}
//
//
//
//
//	public static BufferedImage renderDotGraph(String dot) {
//		GraphViz gv = new GraphViz();
//		gv.add(dot);
//		gv.decreaseDpi();
//		gv.decreaseDpi();
//		return gv.writeGraphToImage(gv.getGraph(gv.getDotSource(), "png"));
//	}
//
//
//
//
//	public static Grammar buildGrammar() {
//
//		GrammarBuilder gb = new GrammarBuilder();
//
//		// OR_EXPRESSION 	-> AND_EXPRESSION {"or" AND_EXPRESSION}
//		gb.defineRootNonTerminal("OR_EXPRESSION",
//				gb.sequence(
//						gb.nonTerminal("AND_EXPRESSION"),
//						gb.zeroOrMore(
//								gb.sequence(
//										gb.terminal("or"),
//										gb.nonTerminal("AND_EXPRESSION")
//								)
//						)
//				)
//		);
//
//		// AND_EXPRESSION 	-> COMPONENT {"and" COMPONENT}
//		gb.defineNonTerminal("AND_EXPRESSION",
//				gb.sequence(
//						gb.nonTerminal("COMPONENT"),
//						gb.zeroOrMore(
//								gb.sequence(
//										gb.terminal("and"),
//										gb.nonTerminal("COMPONENT")
//								)
//						)
//				)
//		);
//
//		// COMPONENT 		-> STATEMENT | ( "(" EXPRESSION ")"
//		gb.defineNonTerminal("COMPONENT",
//				gb.alternative(
//						gb.nonTerminal("STATEMENT"),
//						gb.sequence(
//								gb.terminal("("),
//								gb.nonTerminal("OR_EXPRESSION"),
//								gb.terminal(")")
//						)
//				)
//		);
//
//		// STATEMENT	-> "expr"
//		gb.defineNonTerminal("STATEMENT", gb.terminal("e"));
//
//		return gb.get();
//	}
//
//
//}

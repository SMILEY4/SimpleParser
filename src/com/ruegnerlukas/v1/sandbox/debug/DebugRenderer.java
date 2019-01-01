package com.ruegnerlukas.v1.sandbox.debug;

import com.ruegnerlukas.v1.simpleparser.grammar.Grammar;
import com.ruegnerlukas.v1.simpleparser.grammar.GrammarBuilder;
import com.ruegnerlukas.v1.simpleparser.tokens.Token;
import com.ruegnerlukas.v1.simpleparser.tokens.Tokenizer;
import com.ruegnerlukas.v1.simpleparser.tree.Node;
import com.ruegnerlukas.v1.simpleparser.systems.DotGraphBuilder;
import com.ruegnerlukas.v1.simpleparser.systems.ExpressionProcessor;
import com.ruegnerlukas.v1.utils.GraphViz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DebugRenderer {


	public static BufferedImage imgGraph = null;




	public static void main(String[] args) {

		Grammar grammar = buildGrammar();

		Set<String> ignorables = new HashSet<>();
		ignorables.add(" ");
		Tokenizer tokenizer = new Tokenizer(grammar);
		List<Token> tokens = tokenizer.tokenize("e and (e or e)", ignorables, false);

		System.out.println(tokens);

		Node root = ExpressionProcessor.apply(grammar, tokens);

		System.out.println(DotGraphBuilder.build(root));

		System.out.println("Captured " + DebugManager.data.size() + " data-points.");

		setupDisplay();
	}




	public static void setupDisplay() {

		JFrame frame = new JFrame();
		frame.setTitle("Simple Parser Test - Lukas Ruegner (2018)");
		frame.setSize(1280, 900);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);

		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));

		// graph rendering
		final JPanel graphPanel = new JPanel() {
			@Override
			public void paintComponent(Graphics gfx) {
				super.paintComponent(gfx);

				if(imgGraph == null) {
					return;
				}

				Graphics2D g = (Graphics2D)gfx;
				g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

				int screenWidth = this.getWidth();
				int screenHeight = this.getHeight();

				int imgWidth = imgGraph.getWidth();
				int imgHeight = imgGraph.getHeight();

				float sx = (float)screenWidth / (float)imgWidth;
				float sy = (float)screenHeight / (float)imgHeight;
				float s = Math.min(sx, sy);

				g.drawImage(imgGraph, 0, 0, (int)(imgWidth*s), (int)(imgHeight*s), null);
			}
		};

		frame.addKeyListener(new KeyListener() {
			@Override public void keyTyped(KeyEvent e) {}
			@Override public void keyPressed(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyChar() == ' ') {
					System.out.println("next " + DebugManager.index);
					DebugData data = DebugManager.getNext();
					imgGraph = renderDotGraph(data.getGraph());
					graphPanel.repaint();
				}
				if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
					System.out.println("reset");
				}
			}
		});

		frame.getContentPane().addComponentListener(new ComponentListener() {
			@Override public void componentResized(ComponentEvent e) {
				graphPanel.repaint();
			}
			@Override public void componentMoved(ComponentEvent e) { }
			@Override public void componentShown(ComponentEvent e) { }
			@Override public void componentHidden(ComponentEvent e) { }
		});
		frame.getContentPane().add(graphPanel);

		frame.setVisible(true);
	}




	public static BufferedImage renderDotGraph(String dot) {
		GraphViz gv = new GraphViz();
		gv.add(dot);
		gv.decreaseDpi();
		gv.decreaseDpi();
		return gv.writeGraphToImage(gv.getGraph(gv.getDotSource(), "png"));
	}




	public static Grammar buildGrammar() {

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

		return gb.get();
	}

}

package com.ruegnerlukas.dotGraph;

import java.awt.*;

class DotUtils {


	/**
	 * @return the given rgb-color as a hsv-color-string
	 */
	static String color(int r, int g, int b) {
		final float[] hsb = Color.RGBtoHSB(r, g, b, null);
		return hsb[0] + " " + hsb[1] + " " + hsb[2];
	}




	/**
	 * appends the connection "from -> to" to the given StringBuilder
	 */
	static void appendConnection(StringBuilder builder, String from, String to) {
		builder.append("    ")
				.append(from)
				.append(" -> ")
				.append(to)
				.append(';')
				.append(System.lineSeparator());
	}




	/**
	 * appends the connection "from -> to" with the given label to the given StringBuilder
	 */
	static void appendConnection(StringBuilder builder, String from, String to, String label) {
		builder.append("    ")
				.append(from)
				.append(" -> ")
				.append(to)
				.append(" [label=\"")
				.append(label)
				.append("\"]")
				.append(';')
				.append(System.lineSeparator());
	}




	/**
	 * appends the connection "from -> to" with the given label and color to the given StringBuilder
	 */
	static void appendConnection(StringBuilder builder, String from, String to, String label, int r, int g, int b) {
		builder.append("    ")
				.append(from)
				.append(" -> ")
				.append(to)
				.append(" [label=\"")
				.append(label)
				.append("\"")
				.append(" color=\"")
				.append(color(r, g, b))
				.append("\"]")
				.append(';')
				.append(System.lineSeparator());
	}




	/**
	 * appends the style/rgb-color of the given node to the given StringBuilder
	 */
	static void appendStyle(StringBuilder builder, String node, int r, int g, int b) {
		final String strColor = "[color=\"" + DotUtils.color(r, g, b) + "\"];";
		builder.append("    ")
				.append(node)
				.append(" ").append(strColor)
				.append(System.lineSeparator());
	}


}

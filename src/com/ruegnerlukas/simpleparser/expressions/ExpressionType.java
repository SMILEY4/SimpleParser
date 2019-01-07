package com.ruegnerlukas.simpleparser.expressions;

public enum ExpressionType {

	/** X = E1 | E2 | ... |  */
	ALTERNATIVE,

	/** X = [E] */
	OPTIONAL,

	/** X = {E} */
	REPETITION,

	/** X = A */
	RULE,

	/** X = E1 E2 ... En */
	SEQUENCE,

	/** X = "x" */
	TOKEN
}

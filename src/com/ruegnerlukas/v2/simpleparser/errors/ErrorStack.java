package com.ruegnerlukas.v2.simpleparser.errors;

import com.ruegnerlukas.v2.simpleparser.grammar.State;

import java.util.ArrayList;
import java.util.List;

public class ErrorStack {


	public List<ErrorElement> errors = new ArrayList<>();




	public void addError(Error error, Object origin, State state) {
		errors.add(new ErrorElement(error, origin, state));
	}




	public void addError(ErrorElement error) {
		errors.add(error);
	}




	public void addError(ErrorStack stack) {
		errors.addAll(stack.errors);
	}


}

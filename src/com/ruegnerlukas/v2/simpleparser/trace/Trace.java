package com.ruegnerlukas.v2.simpleparser.trace;

import java.util.ArrayList;
import java.util.List;

public class Trace {


	private List<TraceElement> elements = new ArrayList<>();




	public void add(TraceElement traceElement) {
		elements.add(traceElement);
	}




	public List<TraceElement> getElements() {
		return this.elements;
	}


}

package com.ruegnerlukas.simpleparser;

public class Variable {


	public final String varname;
	public final Class<?> datatype;
	public Object value;




	public Variable(String varname, Class<?> datatype) {
		this.varname = varname;
		this.datatype = datatype;
	}


}

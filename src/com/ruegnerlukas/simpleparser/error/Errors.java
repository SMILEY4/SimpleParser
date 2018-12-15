package com.ruegnerlukas.simpleparser.error;

import java.util.ArrayList;
import java.util.List;

public class Errors {


	public enum ErrorLevel {
		WARNING,
		ERROR
	}




	private static List<ErrorMessage> errors = new ArrayList<ErrorMessage>();




	public static void spawnError(Object origin, ErrorLevel level, String message) {
		errors.add(new ErrorMessage(origin, level, message));
	}




	public static  boolean hasErrors() {
		return !errors.isEmpty();
	}




	public static List<ErrorMessage> getErrors() {
		List<ErrorMessage> returnList = new ArrayList<ErrorMessage>(errors.size());
		returnList.addAll(errors);
		errors.clear();
		return returnList;
	}




	public static ErrorMessage getNextError(){
		if(hasErrors()) {
			return errors.remove(errors.size()-1);
		} else {
			return null;
		}
	}

}

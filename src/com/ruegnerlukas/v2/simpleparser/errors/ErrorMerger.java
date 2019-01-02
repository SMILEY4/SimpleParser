package com.ruegnerlukas.v2.simpleparser.errors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ErrorMerger {


	public static List<Error> mergeErrors(ErrorStack stack) {

		// sort errors into list
//		List<GenericError> genericErrors = new ArrayList<>();
		List<IllegalSymbolError> illegalSymbolErrors = new ArrayList<>();
//		List<InternalError> internalErrors = new ArrayList<>();
		List<SymbolsRemainingError> symbolsRemainingErrors = new ArrayList<>();
		List<UnexpectedEndOfInputError> unexpectedEndOfInputErrors = new ArrayList<>();
		List<UnexpectedSymbolError> unexpectedSymbolErrors = new ArrayList<>();

		for(ErrorElement element : stack.errors) {
			Error error = element.error;

//			if(error.getType() == ErrorType.ERROR) {
//				genericErrors.add((GenericError)error);
//			}
			if(error.getType() == ErrorType.ILLEGAL_SYMBOL) {
				illegalSymbolErrors.add((IllegalSymbolError)error);
			}
//			if(error.getType() == ErrorType.INTERNAL_ERROR) {
//				internalErrors.add((InternalError)error);
//			}
			if(error.getType() == ErrorType.SYMBOLS_REMAINING) {
				symbolsRemainingErrors.add((SymbolsRemainingError)error);
			}
			if(error.getType() == ErrorType.UNEXPECTED_END_OF_INPUT) {
				unexpectedEndOfInputErrors.add((UnexpectedEndOfInputError)error);
			}
			if(error.getType() == ErrorType.UNEXPECTED_SYMBOL) {
				unexpectedSymbolErrors.add((UnexpectedSymbolError)error);
			}
		}


		// merge errors
		List<Error> mergedErrors = new ArrayList<>();

		if(!illegalSymbolErrors.isEmpty()) {
			Map<Integer,IllegalSymbolError> map = new HashMap<>();
			for(IllegalSymbolError error : illegalSymbolErrors) {
				if(!map.containsKey(error.index)) {
					map.put(error.index, new IllegalSymbolError(error.expected, error.actual, error.index));
				} else {
					map.get(error.index).expected.addAll(error.expected);
				}
			}
			mergedErrors.addAll(map.values());
		}

		if(!unexpectedEndOfInputErrors.isEmpty()) {
			UnexpectedEndOfInputError unexpectedEndOfInputError = new UnexpectedEndOfInputError(unexpectedEndOfInputErrors.get(0).expected);
			for(int i=1; i<unexpectedEndOfInputErrors.size(); i++) {
				unexpectedEndOfInputError.expected.addAll(unexpectedEndOfInputErrors.get(i).expected);
			}
			mergedErrors.add(unexpectedEndOfInputError);
		}

		if(!unexpectedSymbolErrors.isEmpty()) {
			Map<Integer,UnexpectedSymbolError> map = new HashMap<>();
			for(UnexpectedSymbolError error : unexpectedSymbolErrors) {
				if(!map.containsKey(error.index)) {
					map.put(error.index, new UnexpectedSymbolError(error.expected, error.actual, error.index));
				} else {
					map.get(error.index).expected.addAll(error.expected);
				}
			}
			mergedErrors.addAll(map.values());
		}

		if(!symbolsRemainingErrors.isEmpty()) {
			Map<Integer,SymbolsRemainingError> map = new HashMap<>();
			for(SymbolsRemainingError error : symbolsRemainingErrors) {
				if(!map.containsKey(error.index)) {
					map.put(error.index, new SymbolsRemainingError(error.index, error.remaining));
				} else {
					map.get(error.index).remaining.addAll(error.remaining);
				}
			}
			mergedErrors.addAll(map.values());
		}


		return mergedErrors;
	}

}

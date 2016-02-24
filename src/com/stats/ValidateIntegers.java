package com.stats;

public class ValidateIntegers {

	/**
	 * validate input number as parsable integers
	 * 
	 * @param input
	 * @return
	 */
	public static boolean isParsable(String input) {
		boolean parsable = true;
		try {
			Integer.parseInt(input);
		} catch (NumberFormatException e) {
			parsable = false;
		}
		return parsable;
	}

}

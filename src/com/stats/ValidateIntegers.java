/*
 * Copyright 2016 (C) Dr Mahmoud El-Haj
 * 
 * Created on : Feb-2016
 * Author     : drelhaj (https://github.com/drelhaj/)
 */

package com.stats;
/**
 * simple integer validator
 * @author elhaj
 *
 */
public class ValidateIntegers {

	/**
	 * validate input number as parsable integers
	 * 
	 * @param input
	 * @return boolean value
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

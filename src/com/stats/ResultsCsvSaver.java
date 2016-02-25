/*
 * Copyright 2016 (C) Dr Mahmoud El-Haj
 * 
 * Created on : Feb-2016
 * Author     : drelhaj (https://github.com/drelhaj/)
 */

package com.stats;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Create CSV files to save the results of the frequency and LLH values.
 * 
 * @author elhaj
 *
 */
public class ResultsCsvSaver {
	/**
	 * Saves the input keywords and the resulting LLH or word Frequencies to a
	 * CSV (or any other text) file format. This methods creates two columns
	 * only taking two arrays as input.
	 * 
	 * @param keywords
	 * @param size
	 * @param outputFile
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public static void saveResultsToCSVTwoColumns(String[] keywords, double[] size, String outputFile)
			throws FileNotFoundException, UnsupportedEncodingException {

		if (outputFile.length() < 1) {
			System.out.println("Output file not valid!");// more validation
															// needed
			System.exit(0);
		} else {

			PrintWriter writer;

			writer = new PrintWriter(outputFile, "UTF-8");
			writer.println("text" + "," + "size");
			writer.flush();

			for (int i = 0; i < keywords.length; i++) {

				writer.println(keywords[i] + "," + size[i]);
				writer.flush();
			}

			writer.close();
		}
	}

	/**
	 * Saves the input keywords and the resulting LLH and word Frequencies to a
	 * CSV (or any other text) file format. This methods creates three columns
	 * taking three arrays as input. it's a String[] double[] double[] method
	 * but can be easily modified to accept other types.
	 * 
	 * @param keywords
	 * @param LLH
	 * @param freq
	 * @param outputFile
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public static void saveResultsToCSVThreeColumns(String[] keywords, double[] LLH, double[] freq, String outputFile)
			throws FileNotFoundException, UnsupportedEncodingException {

		if (outputFile.length() < 1) {
			System.out.println("Output file not valid!");// more validation
															// needed
			System.exit(0);
		} else {

			PrintWriter writer;

			writer = new PrintWriter(outputFile, "UTF-8");
			writer.println("Word" + "," + "LLH" + "," + "Frequency");
			writer.flush();

			for (int i = 0; i < keywords.length; i++) {

				writer.println(keywords[i] + "," + LLH[i] + "," + freq[i]);
				writer.flush();
			}

			writer.close();
		}
	}
}

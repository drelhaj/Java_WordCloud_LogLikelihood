/*
 * Copyright 2016 (C) Dr Mahmoud El-Haj
 * 
 * Created on : Feb-2016
 * Author     : drelhaj (https://github.com/drelhaj/)
 */

package com.stats;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;

/**
 * Main CLI application to run the code (you don't actually need to use this if
 * you know your way with Java) check the {@link TestJavaWordClouds} class for
 * more details.
 * 
 * @author elhaj
 *
 */
public class MainCLI {

	public static void main(String[] args) throws IOException {
		Charset.defaultCharset();
		System.setProperty("file.encoding", "UTF-8");

		// Welcome and help
		System.out.println("Welcome to Java Word Clouds Maker");
		if (args[0].equalsIgnoreCase("help")) {
			System.out.println(
					"To run LLH_Cloud: \"SrcCorpusFile\" \"RefCorpusFile\" \"KeywordsFile\" \"userOutputFile\" \"outputMethod\"\n"
							+ "Example: src.txt ref.txt keywords.txt output.csv 1\n"
							+ "InputFile could be any text file; KeywordFile the terms you want to create a wordCloud for;\n"
							+ "Keywords their LLH score and frequencies output in a user output.csv file (optional);\n"
							+ "OutputMethod: 1 save LLH, 2 save Frequencies, 3 save both");
			System.exit(0);
		}

		// check number of arguments
		if (args.length > 5 || args.length < 3) {
			System.out.println("Wrong number of arguments! Type help!");
			System.exit(0);
		}

		// make sure src file exists before calling the LLH_Cloud readFile
		// method
		if (!new File(args[0]).exists()) {
			System.out.println("Src file doesn't exist!!");
			System.exit(0);
		}

		// make sure ref file exists before calling the LLH_Cloud readFile
		// method
		if (!new File(args[1]).exists()) {
			System.out.println("Ref file doesn't exist!!");
			System.exit(0);
		}

		// make sure keywords file exists before calling the LLH_Cloud readFile
		// method
		if (!new File(args[2]).exists()) {
			System.out.println("Keywords file doesn't exist!!");
			System.exit(0);
		}

		// Make sure output file is valid (further validation could be helpful
		// here e.g. Linux vs Windows naming the use of special characters and
		// reserved names)
		if (args.length > 3 && args[3].trim().length() < 1) {
			System.out.println("Output file not valid!");
			System.exit(0);
		}

		// Validate outputMethod type (1 saves Words and LLH to CSV , \n 2 saves
		// Words and their frequencies to CSV, \n 3 save both values
		if (!ValidateIntegers.isParsable(args[4])) {
			System.out.println("Invalid wordCloud type!");
			System.exit(0);
		} else {
			if (Integer.parseInt(args[4]) < 1 || Integer.parseInt(args[4]) > 3) {
				System.out.println(
						" 1 saves Words and LLH to CSV , \n 2 saves Words and their frequencies to CSV, \n 3 save both values");
				System.exit(0);
			}
		}

		String src = args[0];// user source file (corpus)
		String ref = args[1];// user reference file (corpus)
		String keywordsFile = args[2];

		// read user's keywords file into array (alternatively provide an array
		// of words)
		String[] keywords = FileToArrayReader.readLines(keywordsFile);
		// String[] keywords = {"Hello","Word","Cloud"};
		String[] cloudKeywords = new String[keywords.length];// initialise the
																// size with the
																// number of
																// keywords
																// (makes since
																// right?)
		double[] cloudKeywordLLH = new double[keywords.length];// create a cloud
																// using words
																// LLH Values
		double[] cloudKeywordFreq = new double[keywords.length];// create a
																// cloud using
																// words
																// frequencies

		for (int i = 0; i < keywords.length; i++) {
			// get keyword's frequency and corpus size (number of words) for
			// both src and ref corpora
			WordStats refStats = WordFrequencyCounter.countWordFreq(keywords[i], Paths.get(ref));
			WordStats srcStats = WordFrequencyCounter.countWordFreq(keywords[i], Paths.get(src));

			// calculate log likelihood for each keyword
			double logLL = LlhCalculator.logLikeliHood(srcStats.wordCount, refStats.wordCount, srcStats.corpusSize,
					refStats.corpusSize);

			System.out.println("LLH of: " + keywords[i] + " = " + logLL);
			cloudKeywords[i] = keywords[i];// keyword itself
			cloudKeywordLLH[i] = logLL;// keyword log likelihood value
			cloudKeywordFreq[i] = srcStats.wordCount;// keyword frequency
		}

		// create a word cloud using words LLH values (words size using LLH
		// values)
		WordCloudMaker.main(cloudKeywords, cloudKeywordLLH, "Log Likelihood Word Cloud");// frame
																							// title
																							// is
																							// optional

		// create a word cloud using words frequencies (words size using
		// frequency values)
		WordCloudMaker.main(cloudKeywords, cloudKeywordFreq, "Words Frequency Word Cloud");

		// save LLH results to CSV file
		if (Integer.parseInt(args[4]) == 1)
			ResultsCsvSaver.saveResultsToCSVTwoColumns(cloudKeywords, cloudKeywordLLH, args[3]);

		// save Frequencies0 results to CSV file
		if (Integer.parseInt(args[4]) == 2)
			ResultsCsvSaver.saveResultsToCSVTwoColumns(cloudKeywords, cloudKeywordFreq, args[3]);

		// you can also save both values (LLH and Frequencies) to the same CSV
		// file
		if (Integer.parseInt(args[4]) == 3)
			ResultsCsvSaver.saveResultsToCSVThreeColumns(cloudKeywords, cloudKeywordLLH, cloudKeywordFreq, args[3]);
	}

}

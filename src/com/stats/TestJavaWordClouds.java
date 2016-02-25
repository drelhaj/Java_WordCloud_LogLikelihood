/*
 * Copyright 2016 (C) Dr Mahmoud El-Haj
 * 
 * Created on : Feb-2016
 * Author     : drelhaj (https://github.com/drelhaj/)
 */

package com.stats;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;

/**
 * Test class to make two word clouds using a soruce, a reference and a keywords
 * text files.
 * 
 * @author elhaj
 *
 */
public class TestJavaWordClouds {
	/**
	 * Test method to create two word clouds using a source, reference corpora
	 * and a keywords text file.
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Charset.defaultCharset();
		System.setProperty("file.encoding", "UTF-8");

		String src = "test/src.txt";// user source file (corpus)
		String ref = "test/ref.txt";// user reference file (corpus)
		String keywordsFile = "test/keywords.txt";

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
		ResultsCsvSaver.saveResultsToCSVTwoColumns(cloudKeywords, cloudKeywordLLH, "test/LLH.csv");

		// save Frequencies0 results to CSV file
		ResultsCsvSaver.saveResultsToCSVTwoColumns(cloudKeywords, cloudKeywordFreq, "test/Frequencies.csv");

		// you can also save both values (LLH and Frequencies) to the same CSV
		// file
		ResultsCsvSaver.saveResultsToCSVThreeColumns(cloudKeywords, cloudKeywordLLH, cloudKeywordFreq, "test/All.csv");
	}

}

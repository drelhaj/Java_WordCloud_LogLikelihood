package com.stats;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class Main {

	public static void main(String[] args) throws IOException {
		Charset.defaultCharset();
		System.setProperty("file.encoding", "UTF-8");

		// Welcome and help
		System.out.println("Welcome to LLH Word Cloud");
		if (args[0].equalsIgnoreCase("help")) {
			System.out.println(
					"To run LLH_Cloud: \"SrcCorpusFile\" \"RefCorpusFile\" \"KeywordsFile\" \"userOutputFile\" \"WordCloudType\"\n"
							+ "Example: src.txt ref.txt keywords.txt output.csv 1\n"
							+ "InputFile could be any text file; KeywordFile the terms you want to create a wordCloud for;\n"
							+ "WordCloudType 1 LLH, 0 Freq (using LLH or Word Freq for words size in the word cloud;\n"
							+ "Keywords their LLH score and frequencies output in a user output.csv file (optional)");
			System.exit(0);
		}

		// check number of arguments
		if (args.length > 5 || args.length < 4) {
			System.out.println("Wrong number of arguments! Type help!");
			System.exit(0);
		}

		// make sure src file exists before calling the LLH_Cloud readFile method
		if (!new File(args[0]).exists()) {
			System.out.println("Src file doesn't exist!!");
			System.exit(0);
		}

		// make sure ref file exists before calling the LLH_Cloud readFile method
		if (!new File(args[1]).exists()) {
			System.out.println("Ref file doesn't exist!!");
			System.exit(0);
		}

		// make sure keywords file exists before calling the LLH_Cloud readFile method
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

		// Validate wordcloud type (1 creates a word cloud using LLH values, \n 0 create word cloud using word frequencies
		if (!ValidateIntegers.isParsable(args[3])) {
			System.out.println("Invalid wordCloud type!");
			System.exit(0);
		} else {
			if (Integer.parseInt(args[3]) < 0 || Integer.parseInt(args[3]) > 1) {
				System.out.println("WordCloud Type should be 1 for LLH \n or 2 for Frequencies");
				System.exit(0);
			}
		}

		String src = args[0];
		String ref = args[1];
		String keywordsFile = args[2];
		int type = Integer.parseInt(args[3]);
		// check number of arguments as the output file is optional 
		// the method can be called with the output file length = 0
		if (args.length == 4) {
			WordCloudCreator.LLH_WordCloud(src, ref, keywordsFile, "", type);

		} else {
			String userOutputFile = args[4];
			WordCloudCreator.LLH_WordCloud(src, ref, keywordsFile, userOutputFile, type);

		}
	}



}

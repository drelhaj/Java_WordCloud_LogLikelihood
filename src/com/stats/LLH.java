package com.stats;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LLH {
	private final int wordCount;
	private final int corpusSize;
	private static PrintWriter writer;
	
	public LLH(int wordCount, int corpusSize) {

		this.wordCount = wordCount;
		this.corpusSize = corpusSize;

	}

	public int getWordCount() {
		return wordCount;
	}

	public int getCorpusSizse() {
		return corpusSize;
	}

	public static void main(String[] args) throws IOException {


		String txtFile = "llhCloud/data.csv";
		writer = new PrintWriter(txtFile, "UTF-8");
		
		writer.println("text" + "," + "size");
		writer.flush();
			
		String ref = "test/ref.txt";
		String src = "test/src.txt";


String[] keywords = readLines("test/keywords.txt");	

for (String keyword: keywords) {
	
		LLH llhRef = countWordFreq(keyword, Paths.get(ref));
		LLH llhSrc = countWordFreq(keyword, Paths.get(src));
		
		double logLL = logLikeliHood(llhSrc.wordCount, llhRef.wordCount, llhSrc.corpusSize, llhRef.corpusSize);
		System.out.println("LLH of: " + keyword + " = " + logLL);	
		writer.println(keyword + "," + logLL);
		writer.flush();
}

writer.close();
	}

	public static LLH countWordFreq(String word, Path path) throws IOException {

		String text;
		int wordFreq = 0;
		int wordCount = 0;

		// create a file channel and buffer. Here I use Non-blocking I/O
		// libraries needed when dealing with intensive input/output.
		// Conventional read file methods (e.g. StringBuffer, InputStream,
		// Scanner) may overload the system when dealing with large files as the
		// buffer gets full quickly reducing efficiency and taking long time to
		// process data, which may also lead to a java heap space error.
		// NOTE: setting the buffer to larger number (e.g. 10000) will fix the
		// problem of in complete lines (those lines with matches at the
		// beginning or ending of the buffer text)
		// but this may affect performance with large files (i.e. >10,000,000
		// words)
		FileChannel fileChannel = FileChannel.open(path);
		ByteBuffer buffer = ByteBuffer.allocate(3000);

		int noOfBytesRead = fileChannel.read(buffer);

		while (noOfBytesRead != -1) {

			// flip buffer between reading and writing.
			buffer.flip();

			// loop through buffer contents
			while (buffer.hasRemaining()) {

				// extract text from the buffer
				CharBuffer line = Charset.defaultCharset().decode(buffer);
				text = line.toString();

				text = text.replace("\n", " ").replace("\r", " ");// remove line
																	// breaks so
																	// the
																	// output
																	// can align
																	// on one
																	// line.
				text = text.replaceAll("\\s+", " "); // remove leading, trailing
														// and in between extra
														// spaces.

				wordCount += getCorpusSize(text);

				Pattern pattern = Pattern.compile("\\b(" + word.toLowerCase() + ")\\b");
				Matcher matcher = pattern.matcher(text.toLowerCase().trim());

				while (matcher.find()) {
					++wordFreq;
				}

			}

			buffer.clear();
			noOfBytesRead = fileChannel.read(buffer);
			text = "";// empty text

		}
		return new LLH(wordFreq, wordCount);

	}

	// iterates over text to extract words through detecting boundaries in text
	// to get corpus size in words.
	public static int getCorpusSize(String text) {
		List<String> words = new ArrayList<String>();
		BreakIterator breakIterator = BreakIterator.getWordInstance();
		breakIterator.setText(text);
		int lastIndex = breakIterator.first();
		while (BreakIterator.DONE != lastIndex) {
			int firstIndex = lastIndex;
			lastIndex = breakIterator.next();
			if (lastIndex != BreakIterator.DONE && Character.isLetterOrDigit(text.charAt(firstIndex))) {
				words.add(text.substring(firstIndex, lastIndex));
			}
		}

		return words.size();
	}

	/**
	 * 
	 * @param word1Freq
	 *            frequency of the word in your corpus
	 * @param word2Freq
	 *            frequency of the word in the reference corpus
	 * @param corpus1Size
	 *            size of your corpus in words
	 * @param corpus2Size
	 *            size of the reference corpus in words
	 * @return
	 */
	public static double logLikeliHood(int word1Freq, int word2Freq, int corpus1Size, int corpus2Size) {
		double llh = 0;
		double e1p1 = (corpus1Size * (word1Freq + word2Freq));
		double e1p2 = (corpus1Size + corpus2Size);
		double ExpectedValue1 = e1p1 / e1p2;
		double e2p1 = (corpus2Size * (word1Freq + word2Freq));
		double e2p2 = (corpus1Size + corpus2Size);
		double ExpectedValue2 = e2p1 / e2p2;

		if (ExpectedValue1 == 0 || ExpectedValue2 == 0 || Double.isNaN(ExpectedValue1) || Double.isNaN(ExpectedValue1)
				|| Double.isNaN(Math.log(word1Freq / ExpectedValue1))
				|| Double.isNaN(Math.log(word2Freq / ExpectedValue2))
				|| Double.isInfinite(Math.log(word1Freq / ExpectedValue1))
				|| Double.isInfinite(Math.log(word2Freq / ExpectedValue2))) {
			llh = 0.0;
		} else {
			double llhP1 = (word1Freq * Math.log(word1Freq / ExpectedValue1));
			double llhP2 = (word2Freq * Math.log(word2Freq / ExpectedValue2));
			llh = 2 * (llhP1 + llhP2);
		}
		return llh;
	}
	
	
	//read text file into array
    public static String[] readLines(String filename) throws IOException {
        FileReader fileReader = new FileReader(filename);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<String> lines = new ArrayList<String>();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }
        bufferedReader.close();
        return lines.toArray(new String[lines.size()]);
    }
}

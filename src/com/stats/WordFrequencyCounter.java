package com.stats;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordFrequencyCounter {

	
	public static WordCloudCreator countWordFreq(String word, Path path) throws IOException {

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

				text = text.replaceAll("\\s+", " "); // remove leading, trailing
														// and in between extra
														// spaces.

				wordCount += CorpusSize.getCorpusSize(text);

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
		return new WordCloudCreator(wordFreq, wordCount);

	}
	
}

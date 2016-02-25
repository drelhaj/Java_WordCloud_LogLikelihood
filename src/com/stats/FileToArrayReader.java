package com.stats;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * Reading a text file into an array of strings.
 * @author elhaj
 *
 */
public class FileToArrayReader {

	
	/**
	 * read text file into Array (using line breaks)
	 * @param filename
	 * @return String array
	 * @throws IOException
	 */
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

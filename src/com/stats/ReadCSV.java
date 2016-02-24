package com.stats;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class ReadCSV {

	
	public ReadCSV() throws IOException{
		System.err.println("CSV File Doesn't Exist");
	}
	/**
	 * Read a csv file and store values in a 2D array
	 * 
	 * @param csvFile
	 * @return
	 * @throws IOException
	 */
	public static String[][] readCSV(String csvFile) throws IOException {

		@SuppressWarnings("resource")
		BufferedReader CSVFile = new BufferedReader(new FileReader(csvFile));

		LinkedList<String[]> rows = new LinkedList<String[]>();
		String dataRow = CSVFile.readLine();
		while ((dataRow = CSVFile.readLine()) != null) {
			rows.addLast(dataRow.split(","));
		}

		String[][] csvMatrix = rows.toArray(new String[rows.size()][]);

		return csvMatrix;

	}
}

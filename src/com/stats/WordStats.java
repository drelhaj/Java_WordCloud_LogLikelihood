/*
 * Copyright 2016 (C) Dr Mahmoud El-Haj
 * 
 * Created on : Feb-2016
 * Author     : drelhaj (https://github.com/drelhaj/)
 */

package com.stats;

public class WordStats {

	protected int wordCount;
	protected int corpusSize;
/**
 * gets word count and corpus size (number of words). Didn't add sets methods as I didn't 
 * need them, please feel free to add them if needed.
 * @param wordCount
 * @param corpusSize
 */
	public WordStats(int wordCount, int corpusSize) {

		this.wordCount = wordCount;
		this.corpusSize = corpusSize;
	}

	public int getWordCount() {
		return wordCount;
	}

	public int getCorpusSizse() {
		return corpusSize;
	}

}

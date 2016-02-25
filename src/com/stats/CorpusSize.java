/*
 * Copyright 2016 (C) Dr Mahmoud El-Haj
 * 
 * Created on : Feb-2016
 * Author     : drelhaj (https://github.com/drelhaj/)
 */

package com.stats;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

/**
 * count number of words in a text file and return the value as the corpus size
 * in words
 * 
 * @author elhaj
 *
 */
public class CorpusSize {

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

}

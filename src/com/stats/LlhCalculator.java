package com.stats;

public class LlhCalculator {

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

}

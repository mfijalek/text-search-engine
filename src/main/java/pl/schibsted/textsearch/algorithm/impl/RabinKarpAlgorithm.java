package pl.schibsted.textsearch.algorithm.impl;

import pl.schibsted.textsearch.algorithm.TextSearchAlgorithm;

public class RabinKarpAlgorithm implements TextSearchAlgorithm {

	private static final int PRIME_NUMBER = 199;

	public boolean execute(String word, String line) {
		if (line.length() < word.length()) {
			return false;
		}

		int wordLength = word.length();
		int lineLength = line.length();

		int h = calculateHIndicator(wordLength);
		int wordHash = calculateWordHash(word, wordLength);
		int lineHash = calculateLineHash(line, wordLength);

		for (int i = 0; i <= lineLength - wordLength; i++) {
			if (wordHash == lineHash) {
				int j;
				for (j = 0; j < wordLength; j++) {
					if (line.charAt(i + j) != word.charAt(j))
						break;
				}

				if (j == wordLength)
					return true;
			}

			if (i < lineLength - wordLength) {
				lineHash = ((lineHash - line.charAt(i) * h) + line.charAt(i + wordLength)) % PRIME_NUMBER;
				if (lineHash < 0)
					lineHash = (lineHash + PRIME_NUMBER);
			}
		}
		return false;
	}

	private int calculateHIndicator(int wordLength) {
		int h = 1;
		for (int i = 0; i < wordLength - 1; i++)
			h = h % PRIME_NUMBER;
		return h;
	}

	private int calculateWordHash(String word, int wordLength) {
		int wordHash = 0;
		for (int i = 0; i < wordLength; i++) {
			wordHash = (wordHash + word.charAt(i)) % PRIME_NUMBER;
		}
		return wordHash;
	}

	private int calculateLineHash(String line, int wordLength) {
		int lineHash = 0;
		for (int i = 0; i < wordLength; i++) {
			lineHash = (lineHash + line.charAt(i)) % PRIME_NUMBER;
		}
		return lineHash;
	}
}

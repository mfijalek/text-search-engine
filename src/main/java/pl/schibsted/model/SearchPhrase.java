package pl.schibsted.model;

import java.util.Collections;
import java.util.Set;

public class SearchPhrase {

	private static final String SERACH_WORDS_SEPARATOR = " ";
	private static final String QUIT = ":quit";

	private Set<String> uniqueWords;
	private String phrase;

	public SearchPhrase(String phrase) {
		this.phrase = phrase;
		this.uniqueWords = Collections.unmodifiableSet(Set.of(phrase.split(SERACH_WORDS_SEPARATOR)));
	}

	public final boolean isTerminateStatement() {
		return QUIT.equals(phrase);
	}

	public final Set<String> getUniqueWords() {
		return uniqueWords;
	}

	public final String getPhrase() {
		return phrase;
	}
}

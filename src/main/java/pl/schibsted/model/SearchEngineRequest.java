package pl.schibsted.model;

import java.nio.file.Path;
import java.util.Collections;
import java.util.Set;

public class SearchEngineRequest {

	private final Set<String> searchWords;
	private final Set<Path> textFiles;

	public SearchEngineRequest(Set<String> searchWords, Set<Path> textFiles) {
		this.searchWords = Collections.unmodifiableSet(searchWords);
		this.textFiles = Collections.unmodifiableSet(textFiles);
	}

	public final Set<String> getSearchWords() {
		return searchWords;
	}

	public final Set<Path> getTextFiles() {
		return textFiles;
	}
}

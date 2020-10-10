package pl.schibsted.model;

import java.util.Collections;
import java.util.Set;

public class SearchEngineResponse {

	private final Set<TextFileSearchResponse> responses;

	public SearchEngineResponse(Set<TextFileSearchResponse> responses) {
		this.responses = Collections.unmodifiableSet(responses);
	}

	public final Set<TextFileSearchResponse> getResponses() {
		return responses;
	}

	@Override
	public String toString() {
		return "SearchEngineResponse{" +
			"responses=" + responses +
			'}';
	}
}

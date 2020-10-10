package pl.schibsted.model;

public class GradeStrategyRequest {

	private final TextFileSearchResponse response;
	private final SearchPhrase searchPhrase;

	public GradeStrategyRequest(TextFileSearchResponse response, SearchPhrase searchPhrase) {
		this.response = new TextFileSearchResponse(response.getFileName(), response.getCountOfMatches());
		this.searchPhrase = new SearchPhrase(searchPhrase.getPhrase());
	}

	public TextFileSearchResponse getResponse() {
		return new TextFileSearchResponse(response.getFileName(), response.getCountOfMatches());
	}

	public final SearchPhrase getSearchPhrase() {
		return new SearchPhrase(searchPhrase.getPhrase());
	}
}

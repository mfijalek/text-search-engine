package pl.schibsted.services;

import java.util.Set;
import java.util.stream.Collectors;

import pl.schibsted.model.GradeStrategyRequest;
import pl.schibsted.model.SearchEngineResponse;
import pl.schibsted.model.SearchFileReport;
import pl.schibsted.model.SearchPhrase;
import pl.schibsted.model.SearchReport;
import pl.schibsted.model.TextFileSearchResponse;
import pl.schibsted.textsearch.grade.GradeStrategy;

public class ReportService {

	private final GradeStrategy gradeStrategy;

	public ReportService(GradeStrategy gradeStrategy) {
		this.gradeStrategy = gradeStrategy;
	}

	public SearchReport prepareReport(SearchEngineResponse searchEngineResponse, SearchPhrase searchPhrase) {
		return new SearchReport(calculateGradesForFiles(searchEngineResponse, searchPhrase));
	}

	private Set<SearchFileReport> calculateGradesForFiles(SearchEngineResponse searchEngineResponse, SearchPhrase searchPhrase) {
		return searchEngineResponse.getResponses().stream()
			.map(response -> prepareReportForSingleFile(searchPhrase, response))
			.collect(Collectors.toSet());
	}

	private SearchFileReport prepareReportForSingleFile(SearchPhrase searchPhrase, TextFileSearchResponse response) {
		final GradeStrategyRequest request = new GradeStrategyRequest(response, searchPhrase);
		final int calculate = gradeStrategy.calculate(request);
		return new SearchFileReport(response.getFileName(), calculate);
	}


}

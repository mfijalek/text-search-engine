package pl.schibsted.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.schibsted.model.SearchEngineResponse;
import pl.schibsted.model.SearchFileReport;
import pl.schibsted.model.SearchPhrase;
import pl.schibsted.model.SearchReport;
import pl.schibsted.model.TextFileSearchResponse;
import pl.schibsted.textsearch.grade.GradeStrategy;

class ReportServiceTest {

	private static final int COUNT_OF_MATCHES = 1;
	private static final String FILENAME = "simple.txt";

	private GradeStrategy gradeStrategy;
	private ReportService reportService;

	@BeforeEach
	void beforeEach() {
		gradeStrategy = mock(GradeStrategy.class);
		reportService = new ReportService(gradeStrategy);
	}

	@Test
	void givenSearchEngineResponseExpectedSearchReportWIthGrades() {
		TextFileSearchResponse response = new TextFileSearchResponse(FILENAME, COUNT_OF_MATCHES);
		Set<TextFileSearchResponse> responses = Set.of(response);
		SearchEngineResponse searchEngineResponse = new SearchEngineResponse(responses);
		SearchPhrase searchPhrase = new SearchPhrase("one two");
		doReturn(100).when(gradeStrategy).calculate(any());

		final SearchReport searchReport = reportService.prepareReport(searchEngineResponse, searchPhrase);
		assertEquals(searchReport.getReports().size(), 1);
		final SearchFileReport report = searchReport.getReports().stream().findAny().orElse(null);
		assertEquals(report.getGrade(), 100);
	}

	@Test
	void givenEmptyRequestExpectedEmptyReport() {
		SearchEngineResponse searchEngineResponse = new SearchEngineResponse(new HashSet<>());
		SearchPhrase searchPhrase = new SearchPhrase("one two");
		final SearchReport searchReport = reportService.prepareReport(searchEngineResponse, searchPhrase);
		assertEquals(searchReport.getReports().size(), 0);
	}
}
package pl.schibsted.textsearch.grade.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.schibsted.model.GradeStrategyRequest;
import pl.schibsted.model.SearchPhrase;
import pl.schibsted.model.TextFileSearchResponse;

class PercentageGradeStrategyTest {

	private static String EXAMPLE_TEXT_FILE_NAME = "data.txt";
	private static int MAX_NUMBER_SEARCH_PHRASES_PRESENT = 5;
	private static int MEDIUM_NUMBER_SEARCH_PHRASES_PRESENT = 3;
	private static int NO_SEARCH_PHRASES_PRESENT = 0;
	private static String EXAMPLE_SEARCH_PHRASE = "one two three four five";

	private PercentageGradeStrategy percentageGradeStrategy;

	@BeforeEach
	void beforeEach() {
		percentageGradeStrategy = new PercentageGradeStrategy();
	}

	@Test
	void givenRequestWithCorrectDataExpectFilledReportWithMaxScore() {
		GradeStrategyRequest request = mockGradeStrategyRequest(MAX_NUMBER_SEARCH_PHRASES_PRESENT);
		final int calculate = percentageGradeStrategy.calculate(request);
		assertEquals(calculate, 100);
	}

	@Test
	void givenRequestWithCorrectDataExpectFilledReportWithMediumScore() {
		GradeStrategyRequest request = mockGradeStrategyRequest(MEDIUM_NUMBER_SEARCH_PHRASES_PRESENT);
		final int calculate = percentageGradeStrategy.calculate(request);
		assertEquals(calculate, 60);
	}

	@Test
	void givenRequestWithCorrectDataExpectFilledReportWithMinScore() {
		GradeStrategyRequest request = mockGradeStrategyRequest(NO_SEARCH_PHRASES_PRESENT);
		final int calculate = percentageGradeStrategy.calculate(request);
		assertEquals(calculate, 0);
	}

	private GradeStrategyRequest mockGradeStrategyRequest(int maxNumberSearchPhrasesPresent) {
		TextFileSearchResponse textFileSearchResponse = new TextFileSearchResponse(EXAMPLE_TEXT_FILE_NAME, maxNumberSearchPhrasesPresent);
		SearchPhrase searchPhrase = new SearchPhrase(EXAMPLE_SEARCH_PHRASE);
		return new GradeStrategyRequest(textFileSearchResponse, searchPhrase);
	}
}
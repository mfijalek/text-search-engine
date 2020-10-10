package pl.schibsted.textsearch.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.schibsted.model.SearchEngineRequest;
import pl.schibsted.model.SearchEngineResponse;
import pl.schibsted.model.TextFileSearchResponse;
import pl.schibsted.services.output.OutputService;
import pl.schibsted.textsearch.algorithm.TextSearchAlgorithm;

class SearchEngineServiceTest {

	public static final String SINGLE_LINE_TXT = "singleLine.txt";
	public static final int COUNT_OF_MATCHES = 1;
	private static final String EMPTY_DICTIONARY_PATH = "empty";
	private static final String CORRECT_DICTIONARY_PATH = "correct";
	private OutputService outputService;
	private TextSearchAlgorithm textSearchAlgorithm;

	private SearchEngineService searchEngineService;
	private File singleLineTextFile;

	@BeforeEach
	void beforeEach() {
		outputService = mock(OutputService.class);
		textSearchAlgorithm = mock(TextSearchAlgorithm.class);
		ClassLoader classLoader = getClass().getClassLoader();
		singleLineTextFile = new File(classLoader.getResource("correct/singleLine.txt").getFile());

		searchEngineService = new SearchEngineService(outputService, textSearchAlgorithm);
	}

	@Test
	void givenOneFileMatchingExpectedCorrectResponse() {
		Set<Path> paths = Set.of(Path.of("correct/singleLine.txt"));
		Set<String> words = Set.of("one", "two", "three");
		SearchEngineRequest request = new SearchEngineRequest(words, paths);
		TextFileSearchResponse textFileSearchResponse = new TextFileSearchResponse(SINGLE_LINE_TXT, COUNT_OF_MATCHES);
		SearchEngineService spy = spy(searchEngineService);
		doReturn(textFileSearchResponse).when(spy).search(any(), any());

		final SearchEngineResponse response = spy.search(request);
		assertNotNull(response);
		assertEquals(response.getResponses().size(), 1);

		final TextFileSearchResponse textFileSearch = response.getResponses().stream().findAny().orElse(null);
		assertEquals(textFileSearch.getFileName(), SINGLE_LINE_TXT);
		assertEquals(textFileSearch.getCountOfMatches(), COUNT_OF_MATCHES);
	}

	@Test
	void givenRequestWithTwoWordsExpectedResult1() throws IOException {
		mockTwoWordsSearch(true, false, 1);
	}

	@Test
	void givenRequestWithTwoWordsExpectedResult2() throws IOException {
		mockTwoWordsSearch(true, true, 2);
	}

	@Test
	void givenRequestWithTwoWordsExpectedResult0() throws IOException {
		mockTwoWordsSearch(false, false, 0);
	}

	private void mockTwoWordsSearch(boolean oneMatches, boolean twoMatches, int expectedResult) throws IOException {
		Mockito.doReturn(oneMatches).when(textSearchAlgorithm).execute(eq("one"), any());
		Mockito.doReturn(twoMatches).when(textSearchAlgorithm).execute(eq("two"), any());
		final Set<String> uniqueWords = Set.of("one", "two");
		int phrasesPresent = 0;
		try (InputStream inputStream = new FileInputStream(singleLineTextFile);
			 Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8)) {
			int result = searchEngineService.searchSingleLine(uniqueWords, phrasesPresent, scanner);
			Assertions.assertEquals(result, expectedResult);
		}
	}
}
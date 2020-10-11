package pl.schibsted.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.nio.file.Path;
import java.util.Scanner;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.schibsted.model.DirectoryValidationResult;
import pl.schibsted.model.SearchPhrase;
import pl.schibsted.services.output.OutputService;
import pl.schibsted.textsearch.engine.SearchEngineService;

class CommunicationServiceTest {

	public static final String EXAMPLE_PHRASE = "test";
	private DirectoryService directoryService;
	private SearchEngineService searchEngineService;
	private ReportService reportService;
	private OutputService outputService;

	private CommunicationService communicationService;

	@BeforeEach
	void setUp() {
		directoryService = mock(DirectoryService.class);
		searchEngineService = mock(SearchEngineService.class);
		reportService = mock(ReportService.class);
		outputService = mock(OutputService.class);

		this.communicationService = new CommunicationService(directoryService, searchEngineService, reportService, outputService);
	}

	@Test
	void givenScannerWithExamplePhraseExpectedSearchPhraseModelRepresentation() {
		try (Scanner scanner = new Scanner(EXAMPLE_PHRASE)) {
			final SearchPhrase searchPhrase = this.communicationService.inputSearchPhrase(scanner);
			Assertions.assertEquals(searchPhrase.getPhrase(), EXAMPLE_PHRASE);
		}
	}

	@Test
	void givenValidationWithErrorsExpectedTrue() {
		final DirectoryValidationResult errorOccur = new DirectoryValidationResult("Error occur");
		Mockito.doReturn(Set.of(errorOccur)).when(directoryService).validate();
		Assertions.assertTrue(this.communicationService.validateInputData());
		verify(this.outputService).printValidationErrors(any());
	}

	@Test
	void givenValidationWithoutErrorsExpectedFalse() {
		final DirectoryValidationResult errorOccur = new DirectoryValidationResult("Error occur");
		Mockito.doReturn(Set.of()).when(directoryService).validate();
		Assertions.assertFalse(this.communicationService.validateInputData());
	}

	@Test
	void givenSearchPhraseExpectedSearchExecutionResponse() {
		SearchPhrase searchPhrase = new SearchPhrase(EXAMPLE_PHRASE);
		Mockito.doReturn(Set.of(Path.of("correct/singleLine.txt"))).when(directoryService).getTextFiles();
		this.communicationService.executeSearch(searchPhrase);
		verify(this.searchEngineService).search(any());
	}

	@Test
	void givenSearchPhraseExpectedMethodsInvoked() {
		SearchPhrase searchPhrase = new SearchPhrase(EXAMPLE_PHRASE);
		Mockito.doReturn(Set.of(Path.of("correct/singleLine.txt"))).when(directoryService).getTextFiles();
		this.communicationService.executeSearchAndPrintReport(searchPhrase);
		verify(searchEngineService).search(any());
		verify(outputService).println(any());
	}
}
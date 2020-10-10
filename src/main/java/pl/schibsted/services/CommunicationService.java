package pl.schibsted.services;

import java.util.Scanner;
import java.util.Set;

import pl.schibsted.model.DirectoryValidationResult;
import pl.schibsted.model.SearchEngineRequest;
import pl.schibsted.model.SearchEngineResponse;
import pl.schibsted.model.SearchPhrase;
import pl.schibsted.model.SearchReport;
import pl.schibsted.services.output.OutputService;
import pl.schibsted.textsearch.engine.SearchEngineService;

public class CommunicationService {

	private final DirectoryService directoryService;
	private final SearchEngineService searchEngineService;
	private final ReportService reportService;
	private final OutputService outputService;

	public CommunicationService(DirectoryService directoryService, SearchEngineService searchEngineService, ReportService reportService, OutputService outputService) {
		this.directoryService = directoryService;
		this.searchEngineService = searchEngineService;
		this.reportService = reportService;
		this.outputService = outputService;
	}

	public void execute() {
		try (Scanner scanner = new Scanner(System.in)) {
			if (validateInputData()) return;
			while (true) {
				outputService.printDirectorySummary(directoryService.getDirectory().getPath(), directoryService.getTotalTextFiles());
				final SearchPhrase searchPhrase = inputSearchPhrase(scanner);
				if (searchPhrase.isTerminateStatement()) {
					break;
				}
				outputService.printSearchProcessSummary(searchPhrase.getPhrase());
				executeSearchAndPrintReport(searchPhrase);
			}
		}
	}

	private void executeSearchAndPrintReport(SearchPhrase searchPhrase) {
		final SearchEngineResponse searchEngineResponse = executeSearch(searchPhrase);
		final SearchReport searchReport = reportService.prepareReport(searchEngineResponse, searchPhrase);
		outputService.println(searchReport);
	}

	private SearchEngineResponse executeSearch(SearchPhrase searchPhrase) {
		final SearchEngineRequest searchRequest = new SearchEngineRequest(searchPhrase.getUniqueWords(), directoryService.getTextFiles());
		return searchEngineService.search(searchRequest);
	}

	private boolean validateInputData() {
		final Set<DirectoryValidationResult> validationResults = directoryService.validate();
		if (validationResults.size() > 0) {
			outputService.printValidationErrors(validationResults);
			return true;
		}
		return false;
	}

	public SearchPhrase inputSearchPhrase(Scanner scanner) {
		outputService.printInputPrefix();
		return new SearchPhrase(scanner.nextLine());
	}
}

package pl.schibsted;

import pl.schibsted.services.output.OutputService;
import pl.schibsted.textsearch.grade.GradeStrategy;
import pl.schibsted.textsearch.grade.impl.PercentageGradeStrategy;
import pl.schibsted.model.Directory;
import pl.schibsted.services.CommunicationService;
import pl.schibsted.services.output.impl.ConsoleOutputService;
import pl.schibsted.services.DirectoryService;
import pl.schibsted.services.ReportService;
import pl.schibsted.textsearch.engine.SearchEngineService;
import pl.schibsted.textsearch.algorithm.TextSearchAlgorithm;
import pl.schibsted.textsearch.algorithm.impl.RabinKarpAlgorithm;

public class TextSearchEngineApplication {

	public static void main(String... args) {
		final String dictionaryPath = args.length > 1 ? args[1] : "";
		TextSearchAlgorithm textSearchAlgorithm = new RabinKarpAlgorithm();
		GradeStrategy gradeStrategy = new PercentageGradeStrategy();
		OutputService outputService = new ConsoleOutputService();

		Directory directory = new Directory(dictionaryPath);
		DirectoryService directoryService = new DirectoryService(directory);
		SearchEngineService searchEngineService = new SearchEngineService(outputService, textSearchAlgorithm);
		ReportService reportService = new ReportService(gradeStrategy);
		CommunicationService communicationService = new CommunicationService(directoryService, searchEngineService, reportService, outputService);
		communicationService.execute();
	}

}

package pl.schibsted.textsearch.engine;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import pl.schibsted.model.SearchEngineRequest;
import pl.schibsted.model.SearchEngineResponse;
import pl.schibsted.model.TextFileSearchResponse;
import pl.schibsted.services.output.OutputService;
import pl.schibsted.textsearch.algorithm.TextSearchAlgorithm;

public class SearchEngineService {

	private final OutputService outputService;
	private final TextSearchAlgorithm textSearchAlgorithm;

	public SearchEngineService(OutputService outputService, TextSearchAlgorithm textSearchAlgorithm) {
		this.outputService = outputService;
		this.textSearchAlgorithm = textSearchAlgorithm;
	}

	public SearchEngineResponse search(SearchEngineRequest request) {
		final Set<TextFileSearchResponse> responses = request.getTextFiles().stream()
			.map(textFile -> search(textFile, request.getSearchWords()))
			.collect(Collectors.toSet());
		return new SearchEngineResponse(responses);
	}

	TextFileSearchResponse search(Path pathToFile, Set<String> searchPhrases) {
		int phrasesPresent = 0;
		try (InputStream inputStream = new FileInputStream(pathToFile.toFile());
			 Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8)) {
			while (scanner.hasNextLine() && searchPhrases.size() > 0) {
				phrasesPresent = searchSingleLine(searchPhrases, phrasesPresent, scanner);
			}
		} catch (IOException e) {
			outputService.printSearchExecutionError();
		}

		return new TextFileSearchResponse(pathToFile.getFileName().toString(), phrasesPresent);
	}

	int searchSingleLine(Set<String> uniqueWords, int phrasesPresent, Scanner scanner) {
		String line = scanner.nextLine();
		List<String> words = new ArrayList<>(uniqueWords);
		final Iterator<String> it = words.listIterator();
		while (it.hasNext()) {
			if (textSearchAlgorithm.execute(it.next(), line)) {
				phrasesPresent++;
				it.remove();
			}
		}
		return phrasesPresent;
	}
}

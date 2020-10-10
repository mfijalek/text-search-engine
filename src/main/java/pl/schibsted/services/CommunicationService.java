package pl.schibsted.services;

import java.util.Scanner;
import java.util.Set;

import pl.schibsted.model.DirectoryValidationResult;

public class CommunicationService {

	private static final String QUIT = ":quit";

	private final DirectoryService directoryService;

	public CommunicationService(DirectoryService directoryService) {
		this.directoryService = directoryService;
	}

	private static boolean finishStatement(String searchPhrase) {
		return QUIT.equals(searchPhrase);
	}

	public void execute() {
		try (Scanner scanner = new Scanner(System.in)) {
			final Set<DirectoryValidationResult> validationResults = directoryService.validate();
			if (validationResults.size() > 0) {
				System.out.println("Program stopped because of input data problems: ");
				System.out.println(validationResults);
				return;
			}
			while (true) {
				final String searchPhrase = retrieveSearchPhrase(scanner);
				if (finishStatement(searchPhrase)) {
					break;
				}
				System.out.println("Execute text search of [" + searchPhrase + "]");
				// TODO implement text search
			}
		}
	}

	public String retrieveSearchPhrase(Scanner scanner) {
		System.out.print("search > ");
		return scanner.nextLine();
	}
}

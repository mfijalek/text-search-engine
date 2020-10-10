package pl.schibsted.services.output.impl;

import java.util.Set;

import pl.schibsted.model.DirectoryValidationResult;
import pl.schibsted.services.output.OutputService;

public class ConsoleOutputService implements OutputService {

	public void printValidationErrors(Set<DirectoryValidationResult> validationResults) {
		println("Program stopped because of input data problems: ");
		println(validationResults);
	}

	public void printSearchExecutionError() {
		println("Ann error occurred during file access. Try again.");
	}

	public void printInputPrefix() {
		print("search > ");
	}

	public void printSearchProcessSummary(String searchPhrase) {
		println("Execute text search of [" + searchPhrase + "]");
	}

	public void printDirectorySummary(String path, int textFilesNumber) {
		println(textFilesNumber + " files read in directory " + path);
	}

	public void println(Object output) {
		System.out.println(output);
	}

	public void print(Object output) {
		System.out.print(output);
	}
}

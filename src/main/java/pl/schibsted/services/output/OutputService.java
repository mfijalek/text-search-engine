package pl.schibsted.services.output;

import java.util.Set;

import pl.schibsted.model.DirectoryValidationResult;

public interface OutputService {

	void printValidationErrors(Set<DirectoryValidationResult> validationResults);

	void printSearchExecutionError();

	void printInputPrefix();

	void printSearchProcessSummary(String searchPhrase);

	void printDirectorySummary(String path, int textFilesNumber);

	void println(Object output);
}


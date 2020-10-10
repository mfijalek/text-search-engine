package pl.schibsted.validators.directory.impl;

import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import pl.schibsted.model.Directory;
import pl.schibsted.model.DirectoryValidationResult;
import pl.schibsted.services.DirectoryService;
import pl.schibsted.validators.directory.DirectoryValidator;

public class AnyTextFilePresentValidator implements DirectoryValidator {

	public static final String ERROR_MESSAGE = "Directory does not contain any text file.";

	private DirectoryService directoryService;

	public AnyTextFilePresentValidator(DirectoryService directoryService) {
		this.directoryService = directoryService;
	}

	@Override
	public Optional<DirectoryValidationResult> validate(Directory directory) {
		final Set<String> children = directoryService.getTextFiles().stream()
			.map(Path::getFileName)
			.map(Path::toString)
			.collect(Collectors.toSet());
		if (children.isEmpty()) {
			return Optional.of(new DirectoryValidationResult(ERROR_MESSAGE));
		}
		return Optional.empty();
	}
}

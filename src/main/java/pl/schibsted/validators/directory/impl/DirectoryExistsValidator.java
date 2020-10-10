package pl.schibsted.validators.directory.impl;

import java.io.File;
import java.util.Optional;

import pl.schibsted.model.Directory;
import pl.schibsted.model.DirectoryValidationResult;
import pl.schibsted.validators.directory.DirectoryValidator;

public class DirectoryExistsValidator implements DirectoryValidator {

	public static final String ERROR_MESSAGE = "Dictionary of given path does not exists.";

	@Override
	public Optional<DirectoryValidationResult> validate(Directory directory) {
		File file = getFile(directory);
		if (!file.isDirectory()) {
			return Optional.of(new DirectoryValidationResult(ERROR_MESSAGE));
		}
		return Optional.empty();
	}

	File getFile(Directory directory) {
		return new File(directory.getPath());
	}

}

package pl.schibsted.validators.directory.impl;

import java.io.File;
import java.util.Optional;

import pl.schibsted.model.Directory;
import pl.schibsted.model.DirectoryValidationResult;
import pl.schibsted.validators.directory.DirectoryValidator;

public class DirectoryExistsValidator implements DirectoryValidator {
	@Override
	public Optional<DirectoryValidationResult> validate(Directory directory) {
		File file = new File(directory.getPath());
		if (!file.isDirectory()) {
			return Optional.of(new DirectoryValidationResult("Dictionary of given path does not exists."));
		}
		return Optional.empty();
	}

}

package pl.schibsted.validators.directory.impl;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Optional;

import pl.schibsted.model.Directory;
import pl.schibsted.model.DirectoryValidationResult;
import pl.schibsted.validators.directory.DirectoryValidator;

public class AnyTextFilePresentValidator implements DirectoryValidator {

	public static final FilenameFilter FILTER = (dir, name) -> name.endsWith(".txt");

	@Override
	public Optional<DirectoryValidationResult> validate(Directory directory) {
		File file = new File(directory.getPath());
		final String[] children = file.list(FILTER);
		if (children == null || children.length == 0) {
			return Optional.of(new DirectoryValidationResult("Directory does not contain any text file."));
		}
		return Optional.empty();
	}
}

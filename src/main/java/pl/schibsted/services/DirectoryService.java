package pl.schibsted.services;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import pl.schibsted.model.Directory;
import pl.schibsted.model.DirectoryValidationResult;
import pl.schibsted.validators.directory.DirectoryValidator;
import pl.schibsted.validators.directory.impl.AnyTextFilePresentValidator;
import pl.schibsted.validators.directory.impl.DirectoryExistsValidator;

public class DirectoryService {

	public static final FilenameFilter TEXT_FILES_FILENAME_FILTER = (dir, name) -> name.endsWith(".txt");

	private final Directory directory;
	private final Set<DirectoryValidator> validators = Set.of(new DirectoryExistsValidator(), new AnyTextFilePresentValidator(this));

	public DirectoryService(Directory directory) {
		this.directory = directory;
	}

	public Set<Path> getTextFiles() {
		final File[] textFiles = new File(directory.getPath()).listFiles(DirectoryService.TEXT_FILES_FILENAME_FILTER);
		if (textFiles != null) {
			return Arrays.stream(textFiles)
				.map(File::toPath)
				.collect(Collectors.toSet());
		}
		return new HashSet<>();
	}

	public Directory getDirectory() {
		return directory;
	}

	public int getTotalTextFiles() {
		return getTextFiles().size();
	}

	public Set<DirectoryValidationResult> validate() {
		return validators.stream()
			.map(validator -> validator.validate(directory))
			.filter(Optional::isPresent)
			.map(Optional::get)
			.collect(Collectors.toSet());
	}
}

package pl.schibsted.services;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import pl.schibsted.model.Directory;
import pl.schibsted.model.DirectoryValidationResult;
import pl.schibsted.validators.directory.DirectoryValidator;
import pl.schibsted.validators.directory.impl.AnyTextFilePresentValidator;
import pl.schibsted.validators.directory.impl.DirectoryExistsValidator;

public class DirectoryService {

	private final Directory directory;
	private final Set<DirectoryValidator> validators = Set.of(new DirectoryExistsValidator(), new AnyTextFilePresentValidator());

	public DirectoryService(Directory directory) {
		this.directory = directory;
	}

	public Set<DirectoryValidationResult> validate() {
		return validators.stream()
			.map(validator -> validator.validate(directory))
			.filter(Optional::isPresent)
			.map(Optional::get)
			.collect(Collectors.toSet());
	}
}

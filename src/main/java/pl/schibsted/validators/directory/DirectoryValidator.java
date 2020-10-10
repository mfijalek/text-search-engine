package pl.schibsted.validators.directory;

import java.util.Optional;

import pl.schibsted.model.Directory;
import pl.schibsted.model.DirectoryValidationResult;

public interface DirectoryValidator {

	Optional<DirectoryValidationResult> validate(Directory directory);

}

package pl.schibsted.validators.directory.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.schibsted.model.Directory;
import pl.schibsted.model.DirectoryValidationResult;
import pl.schibsted.services.DirectoryService;

class AnyTextFilePresentValidatorTest {

	private static final String EMPTY_DICTIONARY_PATH = "empty";
	private static final String CORRECT_DICTIONARY_PATH = "correct";

	private AnyTextFilePresentValidator directoryValidator;
	private DirectoryService directoryService;

	@BeforeEach
	void beforeEach() {
		directoryService = mock(DirectoryService.class);
		directoryValidator = new AnyTextFilePresentValidator(directoryService);
	}

	@Test
	void givenDirectoryWithOneTextFileExpectNoValidationErrors() {
		Directory directory = new Directory(CORRECT_DICTIONARY_PATH);
		when(directoryService.getTextFiles()).thenReturn(Set.of(Path.of("correct/data.txt")));
		final Optional<DirectoryValidationResult> result = directoryValidator.validate(directory);
		assertEquals(result, Optional.empty());
	}

	@Test
	void givenEmptyDirectoryFileExpectOneValidationError() {
		Directory directory = new Directory(EMPTY_DICTIONARY_PATH);
		when(directoryService.getTextFiles()).thenReturn(new HashSet<>());
		final Optional<DirectoryValidationResult> result = directoryValidator.validate(directory);
		assertTrue(result.isPresent());
		final DirectoryValidationResult directoryValidationResult = result.get();
		assertEquals(directoryValidationResult.getErrorMessage(), AnyTextFilePresentValidator.ERROR_MESSAGE);
	}

}
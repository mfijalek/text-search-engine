package pl.schibsted.validators.directory.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

import java.io.File;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.schibsted.model.Directory;
import pl.schibsted.model.DirectoryValidationResult;

class DirectoryExistsValidatorTest {
	private static final String EMPTY_DICTIONARY_PATH = "empty";
	private static final String CORRECT_DICTIONARY_PATH = "correct";

	private DirectoryExistsValidator directoryValidator;
	private File correctDirectory;

	@BeforeEach
	void beforeEach() {
		directoryValidator = new DirectoryExistsValidator();
		ClassLoader classLoader = getClass().getClassLoader();
		correctDirectory = new File(classLoader.getResource("correct").getFile());
	}

	@Test
	void givenExistingDirectoryExpectNoValidationErrors() {
		Directory directory = new Directory(CORRECT_DICTIONARY_PATH);
		DirectoryExistsValidator spy = spy(directoryValidator);
		doReturn(correctDirectory).when(spy).getFile(directory);
		final Optional<DirectoryValidationResult> result = spy.validate(directory);
		assertEquals(result, Optional.empty());
	}

	@Test
	void givenEmptyDirectoryExpectOneValidationError() {
		Directory directory = new Directory(EMPTY_DICTIONARY_PATH);
		final Optional<DirectoryValidationResult> result = directoryValidator.validate(directory);
		assertTrue(result.isPresent());
		final DirectoryValidationResult directoryValidationResult = result.get();
		assertEquals(directoryValidationResult.getErrorMessage(), DirectoryExistsValidator.ERROR_MESSAGE);
	}

}
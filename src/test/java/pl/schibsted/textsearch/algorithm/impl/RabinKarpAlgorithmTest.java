package pl.schibsted.textsearch.algorithm.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RabinKarpAlgorithmTest {

	private static final String CORRECT_PATTERN = "kaka";
	private static final String INCORRECT_PATTERN = "onek";
	private static final String PREFIX_PATTERN = "on";
	private static final String POSTFIX_PATTERN = "d";
	private static final String CORRECT_TEXT_LINE = "one word two two one two kaka d";

	private RabinKarpAlgorithm rabinKarpAlgorithm;

	@BeforeEach
	void beforeEach() {
		rabinKarpAlgorithm = new RabinKarpAlgorithm();
	}

	@Test
	void givenPatternWhichIsPresentExpectTrue() {
		assertTrue(rabinKarpAlgorithm.execute(CORRECT_PATTERN, CORRECT_TEXT_LINE));
	}

	@Test
	void givenCorrectPrefixPatternWhichIsPresentExpectTrue() {
		assertTrue(rabinKarpAlgorithm.execute(PREFIX_PATTERN, CORRECT_TEXT_LINE));
	}

	@Test
	void givenCorrectPostfixPatternWhichIsPresentExpectTrue() {
		assertTrue(rabinKarpAlgorithm.execute(POSTFIX_PATTERN, CORRECT_TEXT_LINE));
	}

	@Test
	void givenPatternWhichIsNotPresentExpectFalse() {
		assertFalse(rabinKarpAlgorithm.execute(INCORRECT_PATTERN, CORRECT_TEXT_LINE));
	}

	@Test
	void givenPatternTheSameAsLineExpectTrue() {
		assertTrue(rabinKarpAlgorithm.execute(CORRECT_TEXT_LINE, CORRECT_TEXT_LINE));
	}

	@Test
	void givenEmptyLineExpectFalse() {
		assertFalse(rabinKarpAlgorithm.execute(CORRECT_PATTERN, ""));
	}

	@Test
	void givenEmptyPatternEmptyLineExpectTrue() {
		assertTrue(rabinKarpAlgorithm.execute("", ""));
	}
}
package pl.schibsted.model;

public class DirectoryValidationResult {

	private final String errorMessage;

	public DirectoryValidationResult(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public final String getErrorMessage() {
		return errorMessage;
	}

	@Override
	public String toString() {
		return errorMessage;
	}
}

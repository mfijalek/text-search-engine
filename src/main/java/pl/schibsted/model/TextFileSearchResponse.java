package pl.schibsted.model;

public class TextFileSearchResponse {

	private final String fileName;
	private final int countOfMatches;

	public TextFileSearchResponse(String fileName, int countOfMatches) {
		this.fileName = fileName;
		this.countOfMatches = countOfMatches;
	}

	public final String getFileName() {
		return fileName;
	}

	public final int getCountOfMatches() {
		return countOfMatches;
	}

	@Override
	public String toString() {
		return "TextFileSearchResponse{" +
			"fileName='" + fileName + '\'' +
			", countOfMatches=" + countOfMatches +
			'}';
	}
}

package pl.schibsted.model;

public class SearchFileReport {

	private final String fileName;
	private final int grade;

	public SearchFileReport(String fileName, int grade) {
		this.fileName = fileName;
		this.grade = grade;
	}

	public final int getGrade() {
		return grade;
	}

	@Override
	public String toString() {
		return fileName + " : " + grade + "%";
	}
}

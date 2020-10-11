package pl.schibsted.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

public class SearchReport {

	private final Set<SearchFileReport> reports;

	public SearchReport(Set<SearchFileReport> reports) {
		this.reports = Collections.unmodifiableSet(reports);
	}

	public final Set<SearchFileReport> getReports() {
		return reports;
	}

	@Override
	public String toString() {
		return reports.stream()
			.sorted(Comparator.comparing(SearchFileReport::getGrade, Comparator.reverseOrder()))
			.limit(10)
			.map(SearchFileReport::toString)
			.collect(Collectors.joining("\n"));
	}
}

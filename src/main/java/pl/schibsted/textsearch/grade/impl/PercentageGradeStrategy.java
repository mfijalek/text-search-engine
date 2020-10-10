package pl.schibsted.textsearch.grade.impl;

import pl.schibsted.textsearch.grade.GradeStrategy;
import pl.schibsted.model.GradeStrategyRequest;
import pl.schibsted.model.SearchPhrase;

public class PercentageGradeStrategy implements GradeStrategy {

	@Override
	public int calculate(GradeStrategyRequest request) {
		final SearchPhrase searchPhrase = request.getSearchPhrase();
		return (int) (((double) request.getResponse().getCountOfMatches() / searchPhrase.getUniqueWords().size()) * 100);
	}

}

package pl.schibsted.textsearch.grade;

import pl.schibsted.model.GradeStrategyRequest;

public interface GradeStrategy {

	int calculate(GradeStrategyRequest request);

}

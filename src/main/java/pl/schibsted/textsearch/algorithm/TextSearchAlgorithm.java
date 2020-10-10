package pl.schibsted.textsearch.algorithm;

public interface TextSearchAlgorithm {

	boolean execute(String word, String lineContent);
}

package gov.hhs.aspr.ms.util.stats.bhy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Pair;

import gov.hhs.aspr.ms.util.errors.ContractException;

/**
 * A static utility class for executing the Benjamini-Hochberg or
 * Benjamini-Yeukutieli algorithms
 */

public final class BenjaminiHochbergYeukutieli {

	public static enum Mode {
		BH, // Benjamini-Hochberg
		BY,// Benjamini-Yeukutieli
	}

	private BenjaminiHochbergYeukutieli() {

	}

	/**
	 * Executes the Benjamini-Hochberg or Benjamini-Yeukutieli over a labeled set of
	 * p-values with a given significance. Returns the labels that fail the test.
	 * 
	 * @throws ContractException
	 *                           <ul>
	 *                           <li>{@linkplain BHYError#NULL_MAP} if the
	 *                           labeledPValues map is null</li>
	 *                           <li>{@linkplain BHYError#NULL_MODE} if the mode is
	 *                           null</li>
	 *                           <li>{@linkplain BHYError#NULL_LABEL} if the
	 *                           labeledPValues map contains a null label</li>
	 *                           <li>{@linkplain BHYError#NULL_P_VALUE} if the
	 *                           labeledPValues map contains a null p-value</li>
	 *                           <li>{@linkplain BHYError#EMPTY_LABELS} if the
	 *                           labeledPValues map is empty</li>
	 *                           <li>{@linkplain BHYError#NEGATIVE_SIGNIFICANCE} if
	 *                           the significance is negative</li>
	 *                           <li>{@linkplain BHYError#EXCESS_SIGNIFICANCE} if
	 *                           the significance exceeds 1</li>
	 *                           <li>{@linkplain BHYError#NEGATIVE_P_VALUE} if a
	 *                           p-value is negative</li>
	 *                           <li>{@linkplain BHYError#EXCESS_P_VALUE} if a
	 *                           p-value exceeds 1</li>
	 *                           </ul>
	 */
	public static List<String> getFailedLabels(Map<String, Double> labeledPValues, double significance, Mode mode) {

		if (labeledPValues == null) {
			throw new ContractException(BHYError.NULL_MAP);
		}

		if (labeledPValues.isEmpty()) {
			throw new ContractException(BHYError.EMPTY_LABELS);
		}

		if (significance < 0) {
			throw new ContractException(BHYError.NEGATIVE_SIGNIFICANCE);
		}

		if (significance > 1) {
			throw new ContractException(BHYError.EXCESS_SIGNIFICANCE);
		}

		if (mode == null) {
			throw new ContractException(BHYError.NULL_MODE);
		}
		/* Sort p values into a list of pairs */
		List<Pair<String, Double>> sortedPValues = new ArrayList<>();
		for (String label : labeledPValues.keySet()) {
			if (label == null) {
				throw new ContractException(BHYError.NULL_LABEL);
			}
			Double pValue = labeledPValues.get(label);
			if (pValue == null) {
				throw new ContractException(BHYError.NULL_P_VALUE);
			}
			if (pValue < 0) {
				throw new ContractException(BHYError.NEGATIVE_P_VALUE);
			}
			if (pValue > 1) {
				throw new ContractException(BHYError.EXCESS_P_VALUE);
			}
			Pair<String, Double> pair = new Pair<>(label, pValue);
			sortedPValues.add(pair);
		}
		Collections.sort(sortedPValues, (a, b) -> {
			return Double.compare(a.getSecond(), b.getSecond());
		});

		List<String> result = new ArrayList<>();

		Double C = 0.0;

		switch (mode) {
		case BH:
			C = 1.0;
			break;
		case BY:
			for (int n = 1; n <= labeledPValues.size(); n++) {
				C += 1.0 / n;
			}
			break;
		default:
			throw new RuntimeException("unhandled mode " + mode);
		}

		double baseThreshold = significance / C / labeledPValues.size();

		for (int i = 0; i < sortedPValues.size(); i++) {
			Pair<String, Double> pair = sortedPValues.get(i);

			Double pValue = pair.getSecond();
			if (pValue < (i + 1) * baseThreshold) {
				result.add(pair.getFirst());
			}
		}
		return result;
	}

}

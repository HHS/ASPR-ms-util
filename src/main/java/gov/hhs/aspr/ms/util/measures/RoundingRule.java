package gov.hhs.aspr.ms.util.measures;

import org.apache.commons.math3.util.FastMath;

import gov.hhs.aspr.ms.util.errors.ContractException;

public enum RoundingRule {
	UP, // rounds up to the next whole number
	DOWN, // rounds down to the next whole number
	TOWARD_ZERO, // rounds to the next whole number towards zero
	AWAY_FROM_ZERO, // rounds to the next whole number away from zero
	NEAREST;// rounds to the closest whole number with halves rounding down

	/**
	 * Returns the whole number rounded value based on the rule.
	 * 
	 * <pre>
	 *	UP: rounds up to the next whole number
	 *  DOWN: rounds down to the next whole number
	 *  TOWARD_ZERO: rounds to the next whole number towards zero
	 *  AWAY_FROM_ZERO: rounds to the next whole number away from zero
	 *  NEAREST: rounds to the closest whole number with halves rounding down
	 * </pre>
	 */
	public static double round(double value, RoundingRule roundingRule) {
		if (roundingRule == null) {
			throw new ContractException(MeasuresError.NULL_ROUNDING_RULE);
		}
		double result;
		switch (roundingRule) {
		case AWAY_FROM_ZERO:
			if (value < 0) {
				result = FastMath.floor(value);
			} else {
				result = FastMath.ceil(value);
			}
			break;
		case DOWN:
			result = FastMath.floor(value);
			break;
		case UP:
			result = FastMath.ceil(value);
			break;
		case TOWARD_ZERO:
			if (value < 0) {
				result = FastMath.ceil(value);
			} else {
				result = FastMath.floor(value);
			}
			break;
		case NEAREST:
			result = FastMath.round(value);
			break;
		default:
			throw new RuntimeException("unhandled rounding rule " + roundingRule);
		}

		// We are guarding against returning negative zero. Although equality checks
		// that use == will pass when comparing positive and negative zero, casting the
		// double values to Double objects and performing
		// equals() will not pass.
		if (result == 0) {
			result = 0;
		}

		return result;

	}

	/**
	 * Returns true if and only if the double value is a whole number that can be cast to an int.
	 */
	public static boolean isIntValue(double value) {
		return (value <= Integer.MAX_VALUE && value >= Integer.MIN_VALUE)
				&& (value == FastMath.floor(value) || value == FastMath.ceil(value));
	}

	/**
	 * Returns true if and only if the double value is a whole number that can be cast to a long.
	 */
	public static boolean isLongValue(double value) {
		return (value <= Long.MAX_VALUE && value >= Long.MIN_VALUE)
				&& (value == FastMath.floor(value) || value == FastMath.ceil(value));
	}

}

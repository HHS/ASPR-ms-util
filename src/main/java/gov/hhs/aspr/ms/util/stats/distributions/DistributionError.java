package gov.hhs.aspr.ms.util.stats.distributions;

import gov.hhs.aspr.ms.util.errors.ContractError;

public enum DistributionError implements ContractError {
	UNSUPPORTED_DISTRIBUTION_CONVERSION("The sample distribution does not support conversion to serialization strings"),
	IMPROPER_ARGUMENT_COUNT("A distribution string has the wrong number of arguments"),//
	INVALID_INTEGER_VALUE("A distribution value cannot be parsed to an int value"),//
	INVALID_DOUBLE_VALUE("A distribution value cannot be parsed to a double value"),//
	MISSING_DISTRIBUTION_ARGUMENT("A distribution string lacks one of its key values"), //
	UNKNOWN_DISTRIBUTION_TYPE_NAME("The type field of a distribution string does not match a known distribution"), //
	DUPLICATE_DISTRIBUTION_VALUE_ASSIGNMENT("Value assignment keys must be unique"), //
	INVALID_DISTRIBUTION_VALUE_ASSIGNMENT(
			"Value assignments are name-value pairs must be of the form a=b separated by pipe characters"), //
	NULL_SAMPLE_DISTRIBUTION_STRING("Null sample distribution string"), //
	LOWER_EXCEEDS_UPPER("Lower bound exceeds upper bound"), //
	NON_POSITIVE_PARAMETER("Parameter must be positive"), //
	NULL_RNG("null random number generator"), //
	NULL_SAMPLE_DISTRIBUTION_DATA("null sample distribution data"), //
	PARAMETER_EXCEEDS_1("Probability exceeds 1"), //
	PARAMETER_LESS_THAN_0("Probability less than 0"),//
	;

	private final String description;

	private DistributionError(final String description) {
		this.description = description;
	}

	@Override
	public String getDescription() {
		return description;
	}
}

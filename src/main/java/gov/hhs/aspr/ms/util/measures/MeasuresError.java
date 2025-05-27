package gov.hhs.aspr.ms.util.measures;

import gov.hhs.aspr.ms.util.errors.ContractError;
import gov.hhs.aspr.ms.util.errors.ContractException;

/**
 * An enumeration supporting {@link ContractException} that acts as a general
 * description of the exception.
 */
public enum MeasuresError implements ContractError {
	NULL_UNIT_TYPE_NAME("Null unit type name"), //
	BLANK_UNIT_TYPE_NAME("Blank unit type name"), //
	NULL_UNIT("Null unit"), //
	NULL_UNIT_NAME("Null unit name"), //
	BLANK_UNIT_NAME("Blank unit name"), //
	NULL_UNIT_TYPE("Null unit type"), //
	NON_POSITIVE_SCALAR_VALUE("Non-positive unit value"), //
	NULL_COMPOSITE("Null composed unit"), //
	INCOMPATIBLE_UNIT_TYPES("Unit type power values do not match"), //
	NON_POSITIVE_ROOT("The root is not a positive integer"), //
	POWER_IS_NOT_ROOT_COMPATIBLE("A unit type's power value is not a multiple of the root"), //
	NULL_QUANTITY("Null quantity"),//
	NULL_ROUNDING_RULE("Null rounding rule"),//
	VALUE_CANNOT_BE_CAST_TO_INT("Value cannot be cast to int"),//
	VALUE_CANNOT_BE_CAST_TO_LONG("Value cannot be cast to long"),//
	INVALID_TOLERANCE("Tolerance values must be in the interval [0,1)")

	// NULL_CONSTANT_DESCRIPTION("Null constant description"),
	// NULL_CONSTANT_LABEL("Null constant label"),
	;

	private final String description;

	private MeasuresError(final String description) {
		this.description = description;
	}

	@Override
	public String getDescription() {
		return description;
	}
}

package gov.hhs.aspr.ms.util.measures;

import gov.hhs.aspr.ms.util.errors.ContractError;
import gov.hhs.aspr.ms.util.errors.ContractException;

/**
 * An enumeration supporting {@link ContractException} that acts as a general
 * description of the exception.
 */
public enum MeasuresError implements ContractError {
	NULL_MEASURE_NAME("Null measure name"),
	BLANK_MEASURE_NAME("Blank measure name"),
	NULL_UNIT("Null unit"),
	NULL_UNIT_NAME("Null unit name"),
	BLANK_UNIT_NAME("Blank unit name"),
	NULL_MEASURE("Null measure"),//
	NON_POSITIVE_SCALAR_VALUE("Non-positive unit value"),
	NULL_COMPOSITE("Null composite"),
	INCOMPATIBLE_MEASURES("Measure power values do not match"),//
	NON_POSITIVE_ROOT("The root is not a positive integer"),
	POWER_IS_NOT_ROOT_COMPATIBLE("A measure's power value is not a multiple of the root"),
	NULL_QUANTITY("Null quantity"),
	
//	NULL_CONSTANT_DESCRIPTION("Null constant description"),
//	NULL_CONSTANT_LABEL("Null constant label"),
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

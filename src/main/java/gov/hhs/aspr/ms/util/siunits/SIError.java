package gov.hhs.aspr.ms.util.siunits;

import gov.hhs.aspr.ms.util.errors.ContractError;
import gov.hhs.aspr.ms.util.errors.ContractException;

/**
 * An enumeration supporting {@link ContractException} that acts as a general
 * description of the exception.
 */
public enum SIError implements ContractError {
	DIMENSIONAL_MISMATCH("Dimension power values for the seven dimensions do not match between two SIQuantities"),//
	NON_POSITIVE_ROOT("The root is not a positive integer"),
	DIMENSION_IS_NOT_ROOT_COMPATIBLE("A dimension's power value is not a multiple of the root"),
	NULL_SI_BASE_UNIT("Null SI base unit"),
	NULL_SI_QUANTITY("Null SI Quantity"),
	;
	private final String description;

	private SIError(final String description) {
		this.description = description;
	}

	@Override
	public String getDescription() {
		return description;
	}
}

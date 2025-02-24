package gov.hhs.aspr.ms.util.stats.bhy;

import gov.hhs.aspr.ms.util.errors.ContractError;
import gov.hhs.aspr.ms.util.errors.ContractException;

/**
 * An enumeration supporting {@link ContractException} that acts as a general
 * description of the exception.
 */
public enum BHYError implements ContractError {
	NULL_MAP("if the labeledPValues map is null"),
	NULL_MODE("if the mode is null"),
	NULL_LABEL("if the labeledPValues map contains a null label"),
	NULL_P_VALUE("if the labeledPValues map contains a null p value"),
	EMPTY_LABELS("if the labeledPValues map is empty"),
	NEGATIVE_SIGNIFICANCE("if the significance is negative"),
	EXCESS_SIGNIFICANCE("if the significance exceeds 1"),
	NEGATIVE_P_VALUE("if a p-value is negative"),
	EXCESS_P_VALUE("if a p-value exceeds 1"),
	;

	private final String description;

	private BHYError(final String description) {
		this.description = description;
	}

	@Override
	public String getDescription() {
		return description;
	}
}

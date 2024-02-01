package util.indexer;

import util.errors.ContractError;
import util.errors.ContractException;

/**
 * An enumeration supporting {@link ContractException} that acts as a general
 * description of the exception.
 */
public enum IndexerError implements ContractError {
	NEGATIVE_DIMENSION_SIZE("Negative dimension size"),
	EXCEEDS_MAX_ARRAY_SIZE("The size exceeds max int"),
	;
	
	

	private final String description;

	private IndexerError(final String description) {
		this.description = description;
	}

	@Override
	public String getDescription() {
		return description;
	}
}

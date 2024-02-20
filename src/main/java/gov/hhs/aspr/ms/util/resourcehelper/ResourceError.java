package gov.hhs.aspr.ms.util.resourcehelper;

import gov.hhs.aspr.ms.util.errors.ContractError;

public enum ResourceError implements ContractError {
    UNKNOWN_FILE("Provided file does not exist");

    private final String description;

    private ResourceError(final String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }
}

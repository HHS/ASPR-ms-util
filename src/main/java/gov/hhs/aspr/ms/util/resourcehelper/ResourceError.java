package gov.hhs.aspr.ms.util.resourcehelper;

import gov.hhs.aspr.ms.util.errors.ContractError;

public enum ResourceError implements ContractError {
    UNKNOWN_FILE("Provided file does not exist"),
    FILE_PATH_IS_DIRECTORY("The provided file path points to a directory and not a file"),
    DIRECTORY_PATH_IS_FILE("The provided directory path points to a file and not a directory");

    private final String description;

    private ResourceError(final String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }
}

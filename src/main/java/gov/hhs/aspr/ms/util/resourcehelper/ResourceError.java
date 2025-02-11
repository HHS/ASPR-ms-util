package gov.hhs.aspr.ms.util.resourcehelper;

import gov.hhs.aspr.ms.util.errors.ContractError;

public enum ResourceError implements ContractError {
    UNKNOWN_FILE("Provided file does not exist"),
    FILE_PATH_IS_DIRECTORY("The provided file path points to a directory and not a file"),
    DIRECTORY_PATH_IS_FILE("The provided directory path points to a file and not a directory"),
    NULL_CLASS_REF("The given class ref is null"),
    NULL_DIRECTORY_PATH("The given directory path is null"),
    NULL_DIRECTORY_STRING("The given directory string is null"),
    NULL_FILE_PATH("The given file path is null"),
    NULL_FILE_STRING("The given file string is null"),
    ;

    private final String description;

    private ResourceError(final String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }
}

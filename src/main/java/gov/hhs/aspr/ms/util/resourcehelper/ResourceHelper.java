package gov.hhs.aspr.ms.util.resourcehelper;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

import gov.hhs.aspr.ms.util.errors.ContractException;

/**
 * ResourceHelper is a class designed to solve the issue that sometimes arises
 * when running UnitTests and using files from the resources directory within
 * the src/test folder.
 * <p>
 * The issue is that the relative path to the resource folder is dependant on
 * what folder you are executing the test from. This can vary from IDE to IDE
 * and even from where you run the maven command if using maven to run the unit
 * tests.
 * <p>
 * This solves the issues by obtaining an absolute reference to the resource
 * directory by using the class loader and an empty resource.
 * <p>
 * In addition to the above, this class also provides convience methods to
 * validate file paths and directory paths, and create directories and files.
 */
public class ResourceHelper {

    /**
     * Given a class ref, uses the class loader from the classref and an empty
     * resource name to obtain a URI and then creates and returns a Path from that
     * URI. This path will be an absolute path and not a relative path. Because it
     * uses the classloader, it no longer matters from where this method gets called
     * with respect to the java command used to call it.
     * <p>
     * This solves the problem of unit tests that use files from the
     * src/test/resources sometimes failing because of the directory from which the
     * test was executed.
     * 
     * @throws RuntimeException if the url provided by the classloader cannot be
     *                              converted to a valid URI. Note that the
     *                              RuntimeException wraps the thrown
     *                              {@link URISyntaxException}
     */
    public static Path getResourceDir(Class<?> classRef) {
        return Path.of(getURI(classRef.getClassLoader().getResource("")));
    }

    /**
     * Given a path, creates the directory. This internally calls
     * dirPath.toFile().mkdirs().
     */
    public static Path createDirectory(Path dirPath) {
        if (dirPath.toFile().exists()) {
            return dirPath;
        }

        dirPath.toFile().mkdirs();

        return dirPath;
    }

    /**
     * Given a string that is a valid path, creates the directory.
     * <p>
     * calls {@link ResourceHelper#createDirectory(Path)}
     */
    public static Path createDirectory(String directory) {
        Path dirPath = Path.of(directory);

        return createDirectory(dirPath);
    }

    /**
     * Given a base directory path and a sub directory, resolves the sub directory
     * against the base path and calls {@link ResourceHelper#createDirectory(Path)}
     * <p>
     * returns the resolved path
     */
    public static Path createDirectory(Path baseDirPath, String subDir) {
        Path dirPath = baseDirPath.resolve(subDir);

        return createDirectory(dirPath);
    }

    /**
     * Given a base directory and a sub directory, resolves the sub directory
     * against the base path and calls {@link ResourceHelper#createDirectory(Path)}
     * <p>
     * returns the resolved path
     */
    public static Path createDirectory(String baseDir, String subDir) {
        Path dirPath = Path.of(baseDir, subDir);

        return createDirectory(dirPath);
    }

    /**
     * Given a directory path and a file name, creates a file with the given name in
     * the given directory.
     * 
     * @throws RuntimeException if the file cannot be created. Note that the
     *                              RuntimeException wraps the thrown
     *                              {@link IOException}
     */
    public static void createFile(Path directory, String fileName) {

        File file = directory.resolve(fileName).toFile();

        if (file.exists()) {
            return;
        }

        _createFile(file);
    }

    /**
     * Given a directory path and a file name, creates a file with the given name in
     * the given directory.
     * <p>
     * Deletes the file if it exists before creating the file.
     * 
     * @throws RuntimeException if the file cannot be created. Note that the
     *                              RuntimeException wraps the thrown
     *                              {@link IOException}
     */
    public static void createNewFile(Path directory, String fileName) {

        File file = directory.resolve(fileName).toFile();

        if (file.exists()) {
            file.delete();
        }

        _createFile(file);
    }

    /**
     * Given a file path, validates that the file exists.
     * <p>
     * calls {@link ResourceHelper#validateFilePath(Path)}
     * 
     * @throws ContractException {@link ResourceError#FILE_PATH_IS_DIRECTORY} if the
     *                               file path refers to a directory
     */
    public static Path validateFile(String file) {
        Path filePath = Path.of(file);

        validateFile(filePath);

        return filePath;
    }

    /**
     * Given a file path, validates that the file exists.
     * 
     * @throws ContractException {@link ResourceError#FILE_PATH_IS_DIRECTORY} if the
     *                               file path refers to a directory
     */
    public static Path validateFile(Path filePath) {
        File file = filePath.toFile();

        if (file.isDirectory()) {
            throw new ContractException(ResourceError.FILE_PATH_IS_DIRECTORY);
        }

        if (!file.exists()) {
            throw new ContractException(ResourceError.UNKNOWN_FILE);
        }

        return filePath;
    }

    /**
     * Given a file path, validates that the file exists.
     * <p>
     * calls {@link ResourceHelper#validateFilePath(Path)}
     * 
     * @throws ContractException {@link ResourceError#FILE_PATH_IS_DIRECTORY} if the
     *                               file path refers to a directory
     */
    public static Path validateFilePath(String file) {
        Path filePath = Path.of(file);

        validateFilePath(filePath);

        return filePath;
    }

    /**
     * Given a file path, validates that the file exists.
     * 
     * @throws ContractException {@link ResourceError#FILE_PATH_IS_DIRECTORY} if the
     *                               file path refers to a directory
     */
    public static Path validateFilePath(Path filePath) {
        File file = filePath.toFile();

        if (file.isDirectory()) {
            throw new ContractException(ResourceError.FILE_PATH_IS_DIRECTORY);
        }

        if (!file.exists()) {
            validateDirectoryPath(filePath.getParent());
        }

        return filePath;
    }

    /**
     * Given a directory path, validates that the directory exists.
     * <p>
     * calls {@link ResourceHelper#validateDirectoryPath(Path)}
     * 
     * @throws ContractException {@link ResourceError#DIRECTORY_PATH_IS_FILE} if the
     *                               directory path refers to a file
     * @throws RuntimeException  if the directory does not exist, and the directory
     *                               cannot be created
     */
    public static Path validateDirectoryPath(String directory) {
        Path maybePath = Path.of(directory);

        validateDirectoryPath(maybePath);

        return maybePath;
    }

    /**
     * Given a directory path, validates that the directory exists.
     * 
     * @throws ContractException {@link ResourceError#DIRECTORY_PATH_IS_FILE} if the
     *                               directory path refers to a file
     * @throws RuntimeException  if the directory does not exist, and the directory
     *                               cannot be created
     */
    public static Path validateDirectoryPath(Path directoryPath) {
        File maybeFile = directoryPath.toFile();

        if (maybeFile.isFile()) {
            throw new ContractException(ResourceError.DIRECTORY_PATH_IS_FILE);
        }

        if (!maybeFile.exists()) {
            createDirectory(directoryPath);
        }

        return directoryPath;
    }

    /**
     * Given a url, converts it to a URI
     */
    protected static URI getURI(URL url) {
        try {
            return url.toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Given a file, attempts to create the file
     * 
     * package access for testing
     */
    static void _createFile(File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ResourceHelper() {
    }

}

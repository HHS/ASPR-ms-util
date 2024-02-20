package gov.hhs.aspr.ms.util.resourcehelper;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import gov.hhs.aspr.ms.util.errors.ContractException;

public class ResourceHelper {

    private ResourceHelper() {
    }

    public static Path getResourceDir(Class<?> classRef) {
        return Path.of(getURI(classRef.getClassLoader().getResource("")));
    }

    protected static URI getURI(URL url) {
        try {
            return url.toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static Path makeOutputDir(Path dirPath) {
        dirPath.toFile().mkdirs();

        return dirPath;
    }

    public static Path makeOutputDir(Path basepath, String subDir) {
        Path dirPath = basepath.resolve(subDir);

        return makeOutputDir(dirPath);
    }

    public static void createOutputFile(Path filePath, String fileName) {

        File isAfile = filePath.resolve(fileName).toFile();

        if (isAfile.exists()) {
            isAfile.delete();
        }

        createFile(isAfile);
    }

    public static Path validatePath(String path, boolean isOutput) {
        Path maybePath = Path.of(path);
        File maybeFile = maybePath.toFile();

        boolean isDirectory = maybeFile.isDirectory();
        boolean isFile = maybeFile.isFile();

        // if the given string corresponds to a file that exists, return path
        if (isFile) {
            return maybePath;
        }

        // if file does not exist, ensure the path is not a directory and that the
        // parent directory of the outputFile exists.
        if (isOutput && !isDirectory) {
            Path parentPath = maybePath.getParent();

            if (Files.exists(parentPath)) {
                return maybePath;
            }
        }

        // otherwise throw an exception
        throw new ContractException(ResourceError.UNKNOWN_FILE, path);
    }

    protected static void createFile(File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

package gov.hhs.aspr.ms.util.resourcehelper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;
import gov.hhs.aspr.ms.util.errors.ContractException;

public class AT_ResourceHelper {

    private class IOExceptionFile extends File {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        public IOExceptionFile(String pathname) {
            super(pathname);
        }

        public IOExceptionFile(File file) {
            super(file, pathSeparator);
        }

        @Override
        public boolean createNewFile() throws IOException {

            throw new IOException();
        }

    }

    @Test
    @UnitTestMethod(target = ResourceHelper.class, name = "getResourceDir", args = { Class.class })
    public void testGetResourceDir() throws MalformedURLException {
        Path path = ResourceHelper.getResourceDir(this.getClass());

        assertNotNull(path);
        assertTrue(path.toFile().exists());
        assertTrue(path.resolve("resourceHelper.json").toFile().exists());

        // preconditions
        // bad url syntax
        URL url = new URL("file:${my.properties}");

        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            ResourceHelper.getURI(url);
        });

        assertTrue(runtimeException.getCause() instanceof URISyntaxException);
    }

    @Test
    @UnitTestMethod(target = ResourceHelper.class, name = "createDirectory", args = { Path.class })
    public void testCreateDirectory() {
        Path path = ResourceHelper.getResourceDir(this.getClass()).resolve("additional-folder");

        Path newPath = ResourceHelper.createDirectory(path);

        assertTrue(newPath.toFile().exists());
    }

    @Test
    @UnitTestMethod(target = ResourceHelper.class, name = "createDirectory", args = { String.class })
    public void testCreateDirectory_String() {
        Path path = ResourceHelper.getResourceDir(this.getClass()).resolve("additional-folder");

        Path newPath = ResourceHelper.createDirectory(path.toString());

        assertTrue(newPath.toFile().exists());
    }

    @Test
    @UnitTestMethod(target = ResourceHelper.class, name = "createDirectory", args = { Path.class, String.class })
    public void testCreateDirectory_Path_String() {
        Path path = ResourceHelper.getResourceDir(this.getClass());

        Path newPath = ResourceHelper.createDirectory(path, "additional-folder");

        assertTrue(newPath.toFile().exists());
    }

    @Test
    @UnitTestMethod(target = ResourceHelper.class, name = "createDirectory", args = { String.class, String.class })
    public void testCreateDirectory_String_String() {
        Path path = ResourceHelper.getResourceDir(this.getClass());

        Path newPath = ResourceHelper.createDirectory(path.toString(), "additional-folder");

        assertTrue(newPath.toFile().exists());
    }

    @Test
    @UnitTestMethod(target = ResourceHelper.class, name = "createFile", args = { Path.class, String.class })
    public void testCreateFile() throws IOException {
        Path path = ResourceHelper.getResourceDir(this.getClass());

        Path newPath = ResourceHelper.createDirectory(path);

        String fileName = "foo.txt";
        ResourceHelper.createFile(newPath, fileName);

        assertTrue(newPath.resolve(fileName).toFile().exists());

        // preconditions
        // the file cannot be created
        // force an IOException
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            IOExceptionFile testFile = new IOExceptionFile(newPath.resolve(fileName).toFile());
            ResourceHelper._createFile(testFile);
        });

        assertTrue(runtimeException.getCause() instanceof IOException);
    }

    @Test
    @UnitTestMethod(target = ResourceHelper.class, name = "createNewFile", args = { Path.class, String.class })
    public void testCreateNewFile() throws IOException {
        Path path = ResourceHelper.getResourceDir(this.getClass());

        Path newPath = ResourceHelper.createDirectory(path);

        String fileName = "foo.txt";
        ResourceHelper.createNewFile(newPath, fileName);

        assertTrue(newPath.resolve(fileName).toFile().exists());

        // preconditions
        // the file cannot be created
        // force an IOException
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            IOExceptionFile testFile = new IOExceptionFile(newPath.resolve(fileName).toFile());
            ResourceHelper._createFile(testFile);
        });

        assertTrue(runtimeException.getCause() instanceof IOException);
    }

    @Test
    @UnitTestMethod(target = ResourceHelper.class, name = "validateFile", args = { String.class })
    public void testValidateFile() {
        Path resourceDir = ResourceHelper.getResourceDir(getClass());
        Path dirPath = resourceDir;
        String fileName = "validateFile.json";

        ResourceHelper.createFile(dirPath, fileName);

        String filePath = dirPath.resolve(fileName).toAbsolutePath().toString();
        assertTrue(ResourceHelper.validateFile(filePath).toFile().exists());

        // preconditions
        // file path points to a directory
        ContractException contractException = assertThrows(ContractException.class, () -> {
            ResourceHelper.validateFile(resourceDir.toString());
        });

        assertEquals(ResourceError.FILE_PATH_IS_DIRECTORY, contractException.getErrorType());

        // file does not exist
        contractException = assertThrows(ContractException.class, () -> {
            ResourceHelper.validateFile(dirPath.resolve("unknwonfile.txt"));
        });

        assertEquals(ResourceError.UNKNOWN_FILE, contractException.getErrorType());
    }

    @Test
    @UnitTestMethod(target = ResourceHelper.class, name = "validateFile", args = { Path.class })
    public void testValidateFile_Path() {
        // covered by testValidateFilePath, which internally calls this method when the
        // file does not exist
    }

    @Test
    @UnitTestMethod(target = ResourceHelper.class, name = "validateFilePath", args = { String.class })
    public void testValidateFilePath() {
        Path resourceDir = ResourceHelper.getResourceDir(getClass());
        Path dirPath = resourceDir;
        String fileName = "validateFilePath.json";
        String subDirName = "temp";

        // delete any old test files
        dirPath.resolve(fileName).toFile().delete();

        // path is a file that does not exist but directory exists
        String filePath = dirPath.resolve(fileName).toAbsolutePath().toString();
        Path path = ResourceHelper.validateFilePath(filePath);
        assertFalse(path.toFile().exists());

        // create file
        ResourceHelper.createFile(dirPath, fileName);

        // path is a file that does exist
        path = ResourceHelper.validateFilePath(filePath);
        assertTrue(path.toFile().exists());

        // path is a file that does not exist and the directory does not exist
        dirPath = resourceDir.resolve(subDirName);
        filePath = dirPath.resolve(fileName).toAbsolutePath().toString();
        path = ResourceHelper.validateFilePath(filePath);
        // file does not exist
        assertFalse(path.toFile().exists());
        // directory exists because it was created
        assertTrue(dirPath.toFile().exists());

        // preconditions
        // File path points to a directory
        ContractException contractException = assertThrows(ContractException.class, () -> {
            ResourceHelper.validateFilePath(resourceDir);
        });

        assertEquals(ResourceError.FILE_PATH_IS_DIRECTORY, contractException.getErrorType());
    }

    @Test
    @UnitTestMethod(target = ResourceHelper.class, name = "validateFilePath", args = { Path.class })
    public void testValidateFilePath_Path() {
        Path resourceDir = ResourceHelper.getResourceDir(getClass());
        Path dirPath = resourceDir;
        String fileName = "validateFilePath.json";
        String subDirName = "temp";

        // delete any old test files
        dirPath.resolve(fileName).toFile().delete();

        // path is a file that does not exist but directory exists
        Path filePath = dirPath.resolve(fileName).toAbsolutePath();
        Path path = ResourceHelper.validateFilePath(filePath);
        assertFalse(path.toFile().exists());

        // create file
        ResourceHelper.createFile(dirPath, fileName);

        // path is a file that does exist
        path = ResourceHelper.validateFilePath(filePath);
        assertTrue(path.toFile().exists());

        // path is a file that does not exist and the directory does not exist
        dirPath = resourceDir.resolve(subDirName);
        filePath = dirPath.resolve(fileName).toAbsolutePath();
        path = ResourceHelper.validateFilePath(filePath);
        // file does not exist
        assertFalse(path.toFile().exists());
        // directory exists because it was created
        assertTrue(dirPath.toFile().exists());

        // preconditions
        // File path points to a directory
        ContractException contractException = assertThrows(ContractException.class, () -> {
            ResourceHelper.validateFilePath(resourceDir);
        });

        assertEquals(ResourceError.FILE_PATH_IS_DIRECTORY, contractException.getErrorType());
    }

    @Test
    @UnitTestMethod(target = ResourceHelper.class, name = "validateDirectoryPath", args = { String.class })
    public void testValidateDirectoryPath() {
        Path resourceDir = ResourceHelper.getResourceDir(getClass());
        Path dirPath = resourceDir;
        String subDirName = "temp";

        // delete any old test files
        dirPath.resolve(subDirName).toFile().delete();

        // path is a file that does not exist but directory exists
        String filePath = dirPath.resolve(subDirName).toAbsolutePath().toString();
        Path path = ResourceHelper.validateDirectoryPath(filePath);
        assertTrue(path.toFile().exists());

        // preconditions
        // directory path points to a file
        ContractException contractException = assertThrows(ContractException.class, () -> {
            String fileName = "validateFilePath.json";
            ResourceHelper.validateDirectoryPath(dirPath.resolve(fileName));
        });

        assertEquals(ResourceError.DIRECTORY_PATH_IS_FILE, contractException.getErrorType());
    }

    @Test
    @UnitTestMethod(target = ResourceHelper.class, name = "validateDirectoryPath", args = { Path.class })
    public void testValidateDirectoryPath_Path() {
        Path resourceDir = ResourceHelper.getResourceDir(getClass());
        Path dirPath = resourceDir;
        String subDirName = "temp";

        // delete any old test files
        dirPath.resolve(subDirName).toFile().delete();

        // path is a file that does not exist but directory exists
        Path filePath = dirPath.resolve(subDirName).toAbsolutePath();
        Path path = ResourceHelper.validateDirectoryPath(filePath);
        assertTrue(path.toFile().exists());

        // preconditions
        // directory path points to a file
        ContractException contractException = assertThrows(ContractException.class, () -> {
            String fileName = "validateFilePath.json";
            ResourceHelper.validateDirectoryPath(dirPath.resolve(fileName));
        });

        assertEquals(ResourceError.DIRECTORY_PATH_IS_FILE, contractException.getErrorType());
    }
}

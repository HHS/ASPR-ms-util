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

        // preconditions
        // classRef is null
        ContractException contractException = assertThrows(ContractException.class, () -> {
            ResourceHelper.getResourceDir(null);
        });

        assertEquals(ResourceError.NULL_CLASS_REF, contractException.getErrorType());
    }

    @Test
    @UnitTestMethod(target = ResourceHelper.class, name = "createDirectory", args = { Path.class })
    public void testCreateDirectory() {
        Path path = ResourceHelper.getResourceDir(this.getClass()).resolve("additional-folder");

        Path newPath = ResourceHelper.createDirectory(path);

        assertTrue(newPath.toFile().exists());

        // preconditions
        // directory path is null
        Path badPath = null;
        ContractException contractException = assertThrows(ContractException.class, () -> {
            ResourceHelper.createDirectory(badPath);
        });

        assertEquals(ResourceError.NULL_DIRECTORY_PATH, contractException.getErrorType());
    }

    @Test
    @UnitTestMethod(target = ResourceHelper.class, name = "createDirectory", args = { String.class })
    public void testCreateDirectory_String() {
        Path path = ResourceHelper.getResourceDir(this.getClass()).resolve("additional-folder");

        Path newPath = ResourceHelper.createDirectory(path.toString());

        assertTrue(newPath.toFile().exists());

        // preconditions
        // directory string is null
        String badPath = null;
        ContractException contractException = assertThrows(ContractException.class, () -> {
            ResourceHelper.createDirectory(badPath);
        });

        assertEquals(ResourceError.NULL_DIRECTORY_STRING, contractException.getErrorType());

        // directory string is empty
        contractException = assertThrows(ContractException.class, () -> {
            ResourceHelper.createDirectory("");
        });

        assertEquals(ResourceError.NULL_DIRECTORY_STRING, contractException.getErrorType());
    }

    @Test
    @UnitTestMethod(target = ResourceHelper.class, name = "createDirectory", args = { Path.class, String.class })
    public void testCreateDirectory_Path_String() {
        Path path = ResourceHelper.getResourceDir(this.getClass());

        Path newPath = ResourceHelper.createDirectory(path, "additional-folder");

        assertTrue(newPath.toFile().exists());

        // preconditions
        // sub directory path is null
        ContractException contractException = assertThrows(ContractException.class, () -> {
            ResourceHelper.createDirectory(path, null);
        });

        assertEquals(ResourceError.NULL_DIRECTORY_STRING, contractException.getErrorType());

        // sub directory string is empty
        contractException = assertThrows(ContractException.class, () -> {
            ResourceHelper.createDirectory(path, "");
        });

        assertEquals(ResourceError.NULL_DIRECTORY_STRING, contractException.getErrorType());

        // directory path is null
        Path badPath = null;
        contractException = assertThrows(ContractException.class, () -> {
            ResourceHelper.createDirectory(badPath, "test");
        });

        assertEquals(ResourceError.NULL_DIRECTORY_PATH, contractException.getErrorType());
    }

    @Test
    @UnitTestMethod(target = ResourceHelper.class, name = "createDirectory", args = { String.class, String.class })
    public void testCreateDirectory_String_String() {
        Path path = ResourceHelper.getResourceDir(this.getClass());

        Path newPath = ResourceHelper.createDirectory(path.toString(), "additional-folder");

        assertTrue(newPath.toFile().exists());

        // preconditions
        // sub directory path is null
        ContractException contractException = assertThrows(ContractException.class, () -> {
            ResourceHelper.createDirectory("test", null);
        });

        assertEquals(ResourceError.NULL_DIRECTORY_STRING, contractException.getErrorType());

        // sub directory string is empty
        contractException = assertThrows(ContractException.class, () -> {
            ResourceHelper.createDirectory("test", "");
        });

        assertEquals(ResourceError.NULL_DIRECTORY_STRING, contractException.getErrorType());

        // directory string is null
        contractException = assertThrows(ContractException.class, () -> {
            ResourceHelper.createDirectory("test", null);
        });

        assertEquals(ResourceError.NULL_DIRECTORY_STRING, contractException.getErrorType());

        // directory string is empty
        contractException = assertThrows(ContractException.class, () -> {
            ResourceHelper.createDirectory("", "test");
        });

        assertEquals(ResourceError.NULL_DIRECTORY_STRING, contractException.getErrorType());
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

        newPath.resolve(fileName).toFile().delete();

        // file name path is null
        ContractException contractException = assertThrows(ContractException.class, () -> {
            ResourceHelper.createFile(path, null);
        });

        assertEquals(ResourceError.NULL_FILE_STRING, contractException.getErrorType());

        // file name string is empty
        contractException = assertThrows(ContractException.class, () -> {
            ResourceHelper.createFile(path, "");
        });

        assertEquals(ResourceError.NULL_FILE_STRING, contractException.getErrorType());

        // directory path is null
        Path badPath = null;
        contractException = assertThrows(ContractException.class, () -> {
            ResourceHelper.createFile(badPath, "test");
        });

        assertEquals(ResourceError.NULL_DIRECTORY_PATH, contractException.getErrorType());
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

        newPath.resolve(fileName).toFile().delete();

        // file name path is null
        ContractException contractException = assertThrows(ContractException.class, () -> {
            ResourceHelper.createNewFile(path, null);
        });

        assertEquals(ResourceError.NULL_FILE_STRING, contractException.getErrorType());

        // file name string is empty
        contractException = assertThrows(ContractException.class, () -> {
            ResourceHelper.createNewFile(path, "");
        });

        assertEquals(ResourceError.NULL_FILE_STRING, contractException.getErrorType());

        // directory path is null
        Path badPath = null;
        contractException = assertThrows(ContractException.class, () -> {
            ResourceHelper.createNewFile(badPath, "test");
        });

        assertEquals(ResourceError.NULL_DIRECTORY_PATH, contractException.getErrorType());
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
            ResourceHelper.validateFile(dirPath.resolve("unknown_file.txt"));
        });

        assertEquals(ResourceError.UNKNOWN_FILE, contractException.getErrorType());

        dirPath.resolve(fileName).toFile().delete();

        // file name path is null
        String badPath = null;
        contractException = assertThrows(ContractException.class, () -> {
            ResourceHelper.validateFile(badPath);
        });

        assertEquals(ResourceError.NULL_FILE_STRING, contractException.getErrorType());

        // file name string is empty
        contractException = assertThrows(ContractException.class, () -> {
            ResourceHelper.validateFile("");
        });

        assertEquals(ResourceError.NULL_FILE_STRING, contractException.getErrorType());
    }

    @Test
    @UnitTestMethod(target = ResourceHelper.class, name = "validateFile", args = { Path.class })
    public void testValidateFile_Path() {
        Path resourceDir = ResourceHelper.getResourceDir(getClass());
        Path dirPath = resourceDir;
        String fileName = "validateFile.json";

        ResourceHelper.createFile(dirPath, fileName);

        Path filePath = dirPath.resolve(fileName).toAbsolutePath();
        assertTrue(ResourceHelper.validateFile(filePath).toFile().exists());

        // preconditions
        // file path points to a directory
        ContractException contractException = assertThrows(ContractException.class, () -> {
            ResourceHelper.validateFile(resourceDir.toString());
        });

        assertEquals(ResourceError.FILE_PATH_IS_DIRECTORY, contractException.getErrorType());

        // file does not exist
        contractException = assertThrows(ContractException.class, () -> {
            ResourceHelper.validateFile(dirPath.resolve("unknown_file.txt"));
        });

        assertEquals(ResourceError.UNKNOWN_FILE, contractException.getErrorType());

        dirPath.resolve(fileName).toFile().delete();

        // file name path is null
        Path badPath = null;
        contractException = assertThrows(ContractException.class, () -> {
            ResourceHelper.validateFile(badPath);
        });

        assertEquals(ResourceError.NULL_FILE_PATH, contractException.getErrorType());
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

        resourceDir.resolve(fileName).toFile().delete();
        dirPath.resolve(fileName).toFile().delete();

        // file name path is null
        String badPath = null;
        contractException = assertThrows(ContractException.class, () -> {
            ResourceHelper.validateFile(badPath);
        });

        assertEquals(ResourceError.NULL_FILE_STRING, contractException.getErrorType());

        // file name string is empty
        contractException = assertThrows(ContractException.class, () -> {
            ResourceHelper.validateFile("");
        });

        assertEquals(ResourceError.NULL_FILE_STRING, contractException.getErrorType());
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

        resourceDir.resolve(fileName).toFile().delete();
        dirPath.resolve(fileName).toFile().delete();

        // file name path is null
        Path badPath = null;
        contractException = assertThrows(ContractException.class, () -> {
            ResourceHelper.validateFile(badPath);
        });

        assertEquals(ResourceError.NULL_FILE_PATH, contractException.getErrorType());
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
        String fileName = "validateDirectoryPath.json";
        ContractException contractException = assertThrows(ContractException.class, () -> {
            ResourceHelper.createFile(dirPath, fileName);
            ResourceHelper.validateDirectoryPath(dirPath.resolve(fileName).toString());
        });

        assertEquals(ResourceError.DIRECTORY_PATH_IS_FILE, contractException.getErrorType());

        dirPath.resolve(fileName).toFile().delete();

        String badPath = null;
        contractException = assertThrows(ContractException.class, () -> {
            ResourceHelper.createDirectory(badPath);
        });

        assertEquals(ResourceError.NULL_DIRECTORY_STRING, contractException.getErrorType());

        // directory string is empty
        contractException = assertThrows(ContractException.class, () -> {
            ResourceHelper.createDirectory("");
        });

        assertEquals(ResourceError.NULL_DIRECTORY_STRING, contractException.getErrorType());
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
        String fileName = "validateDirectoryPath.json";
        ContractException contractException = assertThrows(ContractException.class, () -> {
            ResourceHelper.createFile(dirPath, fileName);
            ResourceHelper.validateDirectoryPath(dirPath.resolve(fileName));
        });

        assertEquals(ResourceError.DIRECTORY_PATH_IS_FILE, contractException.getErrorType());

        dirPath.resolve(fileName).toFile().delete();

        // directory path is null
        Path badPath = null;
        contractException = assertThrows(ContractException.class, () -> {
            ResourceHelper.createDirectory(badPath);
        });

        assertEquals(ResourceError.NULL_DIRECTORY_PATH, contractException.getErrorType());
    }
}

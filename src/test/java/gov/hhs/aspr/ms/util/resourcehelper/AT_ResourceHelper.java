package gov.hhs.aspr.ms.util.resourcehelper;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    @UnitTestMethod(target = ResourceHelper.class, name = "makeOutputDir", args = { Path.class })
    public void testMakeTestOutputDir() {
        Path path = ResourceHelper.getResourceDir(this.getClass()).resolve("additional-folder");

        Path newPath = ResourceHelper.makeOutputDir(path);

        assertNotNull(newPath);
        assertTrue(newPath.toFile().exists());
    }

    @Test
    @UnitTestMethod(target = ResourceHelper.class, name = "makeOutputDir", args = { Path.class, String.class })
    public void testMakeTestOutputDir_SubDir() {
        Path path = ResourceHelper.getResourceDir(this.getClass()).resolve("additional-folder");

        Path newPath = ResourceHelper.makeOutputDir(path, "subFolder");

        assertNotNull(newPath);
        assertTrue(newPath.toFile().exists());
    }

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
    @UnitTestMethod(target = ResourceHelper.class, name = "createOutputFile", args = { Path.class,
            String.class })
    public void testCreateTestOutputFile() throws IOException {
        Path path = ResourceHelper.getResourceDir(this.getClass());

        Path newPath = ResourceHelper.makeOutputDir(path);

        String fileName = "foo.txt";
        ResourceHelper.createOutputFile(newPath, fileName);

        assertTrue(newPath.resolve(fileName).toFile().exists());

        // test for delete/recreat file if it exists
        ResourceHelper.createOutputFile(newPath, fileName);

        // preconditions
        // force an IOException
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            IOExceptionFile testFile = new IOExceptionFile(newPath.resolve(fileName).toFile());
            ResourceHelper.createFile(testFile);
        });

        assertTrue(runtimeException.getCause() instanceof IOException);
    }

    @Test
    @UnitTestMethod(target = ResourceHelper.class, name = "validatePath", args = { String.class, boolean.class })
    public void testValidatePath() {
        Path resourceDir = ResourceHelper.getResourceDir(getClass());
        // path is a file that is not output and exists
        String path = resourceDir.resolve("resourceHelper.json").toAbsolutePath().toString();
        Path expectedPath = resourceDir.resolve("resourceHelper.json").toAbsolutePath();
        assertEquals(expectedPath, ResourceHelper.validatePath(path, false));

        // file is an output file that does not exist, but the directory the file
        // resides in exists
        ResourceHelper.makeOutputDir(resourceDir, "testOutput");
        path = resourceDir.resolve("testOutput").resolve("resourceHelper.json").toAbsolutePath().toString();
        expectedPath = resourceDir.resolve("testOutput").resolve("resourceHelper.json").toAbsolutePath();
        assertEquals(expectedPath, ResourceHelper.validatePath(path, true));

        // preconditions
        // File does not exist and it is not an outputFile
        ContractException contractException = assertThrows(ContractException.class, () -> {
            ResourceHelper.validatePath("testOutput", false);
        });

        assertEquals(ResourceError.UNKNOWN_FILE, contractException.getErrorType());

        // is output but parent directory does not exist
        contractException = assertThrows(ContractException.class, () -> {
            ResourceHelper.validatePath(resourceDir.resolve("nonExistantDir").resolve("file.json").toString(), true);
        });

        assertEquals(ResourceError.UNKNOWN_FILE, contractException.getErrorType());
    }
}

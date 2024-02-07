package gov.hhs.aspr.ms.util.readers;

import java.nio.file.Path;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.util.annotations.UnitTag;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_TextTableReader {

    @Test
    @UnitTestMethod(target = TextTableReader.class, name = "readToMap", args = { String.class, String.class, Path.class,
            Consumer.class }, tags = {UnitTag.INCOMPLETE})
    public void testRead_Map() {

    }

    @Test
    @UnitTestMethod(target = TextTableReader.class, name = "read", args = { String.class, String.class, Path.class,
            Consumer.class }, tags = {UnitTag.INCOMPLETE})
    public void testRead() {

    }
}

package gov.hhs.aspr.ms.util.readers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class TextTableReader {

    private TextTableReader() {
    }

    public static void readToMap(String delimeter, String expectedHeader, Path filePath,
            Consumer<Map<String, String>> consumer) {
        BufferedReader reader = getReader(filePath);

        // assume headers are correct
        String[] expectedHeaders = expectedHeader.split(delimeter);
        String[] headers = expectedHeaders;
        int expectedHeaderLength = expectedHeaders.length;
        int headerLength = expectedHeaderLength;

        String headerLine = expectedHeader;

        boolean headerLineRead = false;

        Map<String, String> valueMap = new LinkedHashMap<>();

        for (int i = 0; i < expectedHeaders.length; i++) {
            valueMap.put(expectedHeaders[i], "");
        }

        try {
            String line;

            long lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                String[] lineValues = line.split(delimeter);

                if (lineIsCommentLine(lineValues[0], delimeter)) {
                    lineNumber++;
                    continue;
                }

                if (!headerLineRead) {
                    if (!expectedHeader.equals(line)) {
                        // ensure that the actual headers and expected headers contain a union
                        checkHeadersContainExpectedHeaders(lineValues, expectedHeaders, filePath, reader);

                        headerLine = line;
                        headers = lineValues;
                        headerLength = lineValues.length;
                    }

                    headerLineRead = true;
                    lineNumber++;
                    continue;
                }

                // do not allow duplicate header lines
                checkDuplicateHeaderLine(line, headerLine, filePath, lineNumber, reader);

                // validate that number of values is equal to the number of headers
                validateLineLength(lineValues, headerLength, line, headerLine, filePath, lineNumber, reader);

                for (int i = 0; i < lineValues.length; i++) {
                    String header = headers[i];
                    if (valueMap.containsKey(header)) {
                        valueMap.put(header, lineValues[i]);
                    }
                }

                consumer.accept(Collections.unmodifiableMap(valueMap));
                lineNumber++;
            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void read(String delimeter, String expectedHeader, Path filePath, Consumer<String[]> consumer) {
        BufferedReader reader = getReader(filePath);

        String[] expectedHeaders = expectedHeader.split(delimeter, -1);
        int expectedHeaderLength = expectedHeaders.length;
        int headerLength = expectedHeaderLength;

        String headerLine = expectedHeader;

        boolean headerLineRead = false;
        boolean headerMatchesExpected = false;

        try {
            String line;
            // this lookup is only used if the actual header line doesn't match the expected
            // header line. So this is safe to do.
            int[] headerIndexLookup = new int[0];

            long lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                String[] lineValues = line.split(delimeter, -1);

                if (lineIsCommentLine(lineValues[0], delimeter)) {
                    lineNumber++;
                    continue;
                }

                if (!headerLineRead) {
                    headerMatchesExpected = expectedHeader.equals(line);
                    if (!headerMatchesExpected) {
                        // ensure that the actual headers and expected headers contain a union
                        checkHeadersContainExpectedHeaders(lineValues, expectedHeaders, filePath, reader);

                        headerIndexLookup = processHeaderLine(expectedHeaders, lineValues, delimeter);
                        headerLine = line;
                        headerLength = lineValues.length;
                    }

                    headerLineRead = true;
                    lineNumber++;
                    continue;
                }

                // do not allow duplicate header lines
                checkDuplicateHeaderLine(line, headerLine, filePath, lineNumber, reader);

                // validate that number of values is equal to the number of headers
                validateLineLength(lineValues, headerLength, line, headerLine, filePath, lineNumber, reader);

                String[] retValues = lineValues;

                if (!headerMatchesExpected) {
                    retValues = new String[expectedHeaderLength];

                    for (int i = 0; i < lineValues.length; i++) {
                        int index = headerIndexLookup[i];
                        if (index != -1) {
                            retValues[i] = lineValues[index];
                        }
                    }
                }

                consumer.accept(retValues);
                lineNumber++;
            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void checkHeadersContainExpectedHeaders(String[] headers, String[] expectedHeaders, Path filePath,
            BufferedReader reader) throws IOException {
        List<String> expectedHeadersList = Arrays.asList(expectedHeaders);
        List<String> headersList = Arrays.asList(headers);

        if (!headersList.containsAll(expectedHeadersList)) {
            reader.close();
            throw new RuntimeException(
                    "The actual headers do not contain the set of expected headers. Did you forget to put a header line? File: "
                            + filePath + " Expected Headers: " + expectedHeadersList.toString() + " Headers: "
                            + headersList.toString());
        }
    }

    private static void checkDuplicateHeaderLine(String line, String headerLine, Path filePath, long lineNumber,
            BufferedReader reader) throws IOException {
        if (line.equals(headerLine)) {
            reader.close();
            throw new RuntimeException("Duplicate header line. File: " + filePath + " Line: " + lineNumber);
        }
    }

    private static void validateLineLength(String[] lineValues, int headerLength, String line, String headerLine,
            Path filePath, long lineNumber, BufferedReader reader) throws IOException {
        if (lineValues.length != headerLength) {
            reader.close();
            throw new RuntimeException(
                    "mismatch between values on line and number of headers. Please ensure data integrity. File: "
                            + filePath + " Line Number: " + lineNumber + " Line contents: " + line + " headers: "
                            + headerLine + " ");
        }
    }

    private static BufferedReader getReader(Path filePath) {
        try {
            return new BufferedReader(new FileReader(filePath.toFile()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean lineIsCommentLine(String value, String delimeter) {
        if (value.strip().startsWith("//")) {
            return true;
        }

        return false;
    }

    private static int[] processHeaderLine(String[] expectedHeaders, String[] actualHeaders, String delimeter) {

        int[] headerIndexLookup = new int[actualHeaders.length];

        for (int i = 0; i < actualHeaders.length; i++) {
            headerIndexLookup[i] = -1;
        }

        Map<String, Integer> headerMapStrToInt = new HashMap<>();
        Map<Integer, String> headerMapIntToStr = new HashMap<>();

        for (int i = 0; i < expectedHeaders.length; i++) {
            headerMapStrToInt.put(expectedHeaders[i], i);
            headerMapIntToStr.put(i, expectedHeaders[i]);
        }

        for (int i = 0; i < actualHeaders.length; i++) {
            String expectedColHeader = headerMapIntToStr.get(i);
            String actualColHeader = actualHeaders[i];

            int newIndex = i;

            if (expectedColHeader == null || !expectedColHeader.equals(actualColHeader)) {
                if (!headerMapStrToInt.containsKey(actualColHeader)) {
                    continue;
                }
                newIndex = headerMapStrToInt.get(actualColHeader);
            }

            headerIndexLookup[i] = newIndex;
        }

        return headerIndexLookup;
    }
}

package gov.hhs.aspr.ms.util.indexer;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;


public class AT_IndexerError {

	/**
	 * Tests {@link NucleusError#getDescription()}
	 */
	@Test
	@UnitTestMethod(target = IndexerError.class, name = "getDescription", args = {})
	public void testGetDescription() {
		// show that each ErrorType has a non-null, non-empty description
		for (IndexerError nucleusError : IndexerError.values()) {
			assertNotNull(nucleusError.getDescription());
			assertTrue(nucleusError.getDescription().length() > 0);
		}

		// show that each description is unique (ignoring case as well)
		Set<String> descriptions = new LinkedHashSet<>();
		for (IndexerError nucleusError : IndexerError.values()) {
			boolean isUnique = descriptions.add(nucleusError.getDescription().toLowerCase());
			assertTrue(isUnique, nucleusError + " duplicates the description of another member");
		}
	}

}

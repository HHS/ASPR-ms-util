package gov.hhs.aspr.ms.util.stats.bhy;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_BHYError {
	@Test
	@UnitTestMethod(target = BHYError.class, name = "getDescription", args = {})
	public void test() {
		// show that each description is a unique, non-null and non-empty string
		Set<String> descriptions = new LinkedHashSet<>();
		for (BHYError bhyError : BHYError.values()) {
			String description = bhyError.getDescription();
			assertNotNull(description, "null description for " + bhyError);
			assertTrue(description.length() > 0, "empty string for " + bhyError);
			boolean unique = descriptions.add(description);
			assertTrue(unique, "description for " + bhyError + " is not unique");
		}
	}
}

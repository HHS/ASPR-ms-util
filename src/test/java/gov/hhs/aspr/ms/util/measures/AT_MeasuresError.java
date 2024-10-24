package gov.hhs.aspr.ms.util.measures;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_MeasuresError {

	@Test
	@UnitTestMethod(target = MeasuresError.class, name = "getDescription", args = {})
	public void test() {
		// show that each description is a unique, non-null and non-empty string
		Set<String> descriptions = new LinkedHashSet<>();
		for (MeasuresError measuresError : MeasuresError.values()) {
			String description = measuresError.getDescription();
			assertNotNull(description, "null description for " + measuresError);
			assertTrue(description.length() > 0, "empty string for " + measuresError);
			boolean unique = descriptions.add(description);
			assertTrue(unique, "description for " + measuresError + " is not unique");
		}
	}
}

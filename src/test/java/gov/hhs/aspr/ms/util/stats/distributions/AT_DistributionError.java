package gov.hhs.aspr.ms.util.stats.distributions;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_DistributionError {

	@Test
	@UnitTestMethod(target = DistributionError.class, name = "getDescription", args = {})
	public void test() {
		// show that each description is a unique, non-null and non-empty string
		Set<String> descriptions = new LinkedHashSet<>();
		for (DistributionError distributionError : DistributionError.values()) {
			String description = distributionError.getDescription();
			assertNotNull(description, "null description for " + distributionError);
			assertTrue(description.length() > 0, "empty string for " + distributionError);
			boolean unique = descriptions.add(description);
			assertTrue(unique, "description for " + distributionError + " is not unique");
		}
	}
}

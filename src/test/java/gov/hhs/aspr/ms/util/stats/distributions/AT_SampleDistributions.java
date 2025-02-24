package gov.hhs.aspr.ms.util.stats.distributions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.util.annotations.UnitTag;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_SampleDistributions {

	@Test
	@UnitTestMethod(target = SampleDistributions.class, name = "fromString", args = { String.class })
	public void testFromString() {

		testConversions("type::binomial|probability::0.5|trials::45");

		testConversions("type::beta|alpha::40.5|beta::545.0");

		testConversions("type::constant|value::10.0");

		testConversions("type::exponential|mean::15.0");

		testConversions("type::gamma|shape::2.4|scale::3.7");

		testConversions("type::lognormal|shape::2.4|scale::3.7");

		testConversions("type::normal|mean::5.6|stdev::1.2");

		testConversions("type::truncatednormal|mean::5.6|stdev::1.2");

		testConversions("type::uniform|lower::5.0|upper::14.0");
	}

	/*
	 * Shows that the input string can be converted to a SampleDistributionData and
	 * then back into a string. The two strings will be identical so long as there
	 * are no extra spaces and decimals are properly formatted.
	 */
	private void testConversions(String input) {
		SampleDistributionData sampleDistributionData = SampleDistributions.fromString(input);
		assertNotNull(sampleDistributionData);
		String output = SampleDistributions.toString(sampleDistributionData);
		assertNotNull(output);
		assertEquals(input, output);
	}

	@Test
	@UnitTestMethod(target = SampleDistributions.class, name = "toString", args = {
			SampleDistributionData.class }, tags = { UnitTag.LOCAL_PROXY })
	public void testToString() {
		// test covered by the testFromString() unit test method
	}

}

package gov.hhs.aspr.ms.util.stats.distributions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.math3.random.RandomGenerator;
import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;
import gov.hhs.aspr.ms.util.random.RandomGeneratorProvider;

public class AT_TruncatedNormalSampleDistributionData {

	@Test
	@UnitTestMethod(target = TruncatedNormalSampleDistributionData.Builder.class, name = "build", args = {})
	public void testBuild() {
		// nothing to test -- all errors are caught in the mutation methods
	}

	@Test
	@UnitTestMethod(target = TruncatedNormalSampleDistributionData.Builder.class, name = "setMean", args = {
			double.class })
	public void testSetMean() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(2502688629848284439L);

		assertEquals(0.0, TruncatedNormalSampleDistributionData.builder().build().getMean());

		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble() * 100 - 50;
			double standardDeviation = randomGenerator.nextDouble() * 10;
			TruncatedNormalSampleDistributionData trucactedNormalSampleDistributionData = TruncatedNormalSampleDistributionData
					.builder().setMean(mean).setStandardDeviation(standardDeviation).build();
			assertEquals(mean, trucactedNormalSampleDistributionData.getMean());
		}

	}

	@Test
	@UnitTestMethod(target = TruncatedNormalSampleDistributionData.Builder.class, name = "setStandardDeviation", args = {
			double.class })
	public void testSetStandardDeviation() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(1570219788202151800L);
		assertEquals(1.0, TruncatedNormalSampleDistributionData.builder().build().getStandardDeviation());

		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble() * 100 - 50;
			double standardDeviation = randomGenerator.nextDouble() * 10;
			TruncatedNormalSampleDistributionData trucactedNormalSampleDistributionData = TruncatedNormalSampleDistributionData
					.builder().setMean(mean).setStandardDeviation(standardDeviation).build();
			assertEquals(standardDeviation, trucactedNormalSampleDistributionData.getStandardDeviation());
		}
	}

	@Test
	@UnitTestMethod(target = TruncatedNormalSampleDistributionData.class, name = "builder", args = {})
	public void testBuilder() {
		assertNotNull(TruncatedNormalSampleDistributionData.builder());
	}

	@Test
	@UnitTestMethod(target = TruncatedNormalSampleDistributionData.class, name = "equals", args = { Object.class })
	public void testEquals() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(8002651869627697878L);

		// never equal null
		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble() * 100 - 50;
			double standardDeviation = randomGenerator.nextDouble() * 10;

			TruncatedNormalSampleDistributionData trucactedNormalSampleDistributionData = TruncatedNormalSampleDistributionData
					.builder().setMean(mean)//
					.setStandardDeviation(standardDeviation)//
					.build();
			assertFalse(trucactedNormalSampleDistributionData.equals(null));
		}

		// not equal to other things
		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble() * 100 - 50;
			double standardDeviation = randomGenerator.nextDouble() * 10;

			TruncatedNormalSampleDistributionData trucactedNormalSampleDistributionData = TruncatedNormalSampleDistributionData
					.builder().setMean(mean)//
					.setStandardDeviation(standardDeviation)//
					.build();
			assertFalse(trucactedNormalSampleDistributionData.equals(new Object()));
		}

		// reflexive
		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble() * 100 - 50;
			double standardDeviation = randomGenerator.nextDouble() * 10;
			TruncatedNormalSampleDistributionData trucactedNormalSampleDistributionData = TruncatedNormalSampleDistributionData
					.builder().setMean(mean)//
					.setStandardDeviation(standardDeviation)//
					.build();
			assertTrue(trucactedNormalSampleDistributionData.equals(trucactedNormalSampleDistributionData));
		}

		// symmetric, transitive, consistent
		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble() * 100 - 50;
			double standardDeviation = randomGenerator.nextDouble() * 10;

			TruncatedNormalSampleDistributionData normalSampleDistributionData1 = TruncatedNormalSampleDistributionData
					.builder().setMean(mean)//
					.setStandardDeviation(standardDeviation)//
					.build();

			TruncatedNormalSampleDistributionData normalSampleDistributionData2 = TruncatedNormalSampleDistributionData
					.builder().setMean(mean)//
					.setStandardDeviation(standardDeviation)//
					.build();
			assertTrue(normalSampleDistributionData1.equals(normalSampleDistributionData2));
			assertTrue(normalSampleDistributionData2.equals(normalSampleDistributionData1));
			for (int j = 0; j < 3; j++) {
				assertTrue(normalSampleDistributionData1.equals(normalSampleDistributionData2));
				assertTrue(normalSampleDistributionData2.equals(normalSampleDistributionData1));
			}

		}
	}

	@Test
	@UnitTestMethod(target = TruncatedNormalSampleDistributionData.class, name = "getCloneBuilder", args = {})
	public void testGetCloneBuilder() {
		TruncatedNormalSampleDistributionData original = TruncatedNormalSampleDistributionData.builder().setMean(6)
				.setStandardDeviation(7).build();

		TruncatedNormalSampleDistributionData duplicate = TruncatedNormalSampleDistributionData.builder().setMean(6)
				.setStandardDeviation(7).build();
		assertEquals(duplicate, original);

		// if no changes are made, the cloned version is equal to the duplicate
		TruncatedNormalSampleDistributionData cloned = original.getCloneBuilder().build();
		assertEquals(duplicate, cloned);

		// if the mean value is changed the original is unaffected
		original.getCloneBuilder().setMean(5).build();
		assertEquals(duplicate, original);

		// if the standardDeviation value is changed the original is unaffected
		original.getCloneBuilder().setStandardDeviation(10).build();
		assertEquals(duplicate, original);

	}

	@Test
	@UnitTestMethod(target = TruncatedNormalSampleDistributionData.class, name = "getMean", args = {})
	public void testGetMean() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(8707148797311985140L);

		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble() * 100 - 50;
			double standardDeviation = randomGenerator.nextDouble() * 10;
			TruncatedNormalSampleDistributionData trucactedNormalSampleDistributionData = TruncatedNormalSampleDistributionData
					.builder().setMean(mean).setStandardDeviation(standardDeviation).build();
			assertEquals(mean, trucactedNormalSampleDistributionData.getMean());
		}
	}

	@Test
	@UnitTestMethod(target = TruncatedNormalSampleDistributionData.class, name = "getStandardDeviation", args = {})
	public void testGetStandardDeviation() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(2226337999633679132L);

		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble() * 100 - 50;
			double standardDeviation = randomGenerator.nextDouble() * 10;
			TruncatedNormalSampleDistributionData trucactedNormalSampleDistributionData = TruncatedNormalSampleDistributionData
					.builder().setMean(mean).setStandardDeviation(standardDeviation).build();
			assertEquals(standardDeviation, trucactedNormalSampleDistributionData.getStandardDeviation());
		}
	}

	@Test
	@UnitTestMethod(target = TruncatedNormalSampleDistributionData.class, name = "hashCode", args = {})
	public void testHashCode() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(2678948420814252720L);

		// equal object have equal hash codes
		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble() * 100 - 50;
			double standardDeviation = randomGenerator.nextDouble() * 10;

			TruncatedNormalSampleDistributionData trucactedNormalSampleDistribution1 = TruncatedNormalSampleDistributionData
					.builder().setMean(mean)//
					.setStandardDeviation(standardDeviation)//
					.build();

			TruncatedNormalSampleDistributionData trucactedNormalSampleDistribution2 = TruncatedNormalSampleDistributionData
					.builder().setMean(mean)//
					.setStandardDeviation(standardDeviation)//
					.build();

			assertEquals(trucactedNormalSampleDistribution1, trucactedNormalSampleDistribution2);

			assertEquals(trucactedNormalSampleDistribution2.hashCode(), trucactedNormalSampleDistribution1.hashCode());

		}

		// hash codes are stable
		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble() * 100 - 50;
			double standardDeviation = randomGenerator.nextDouble() * 10;

			TruncatedNormalSampleDistributionData trucactedNormalSampleDistributionData = TruncatedNormalSampleDistributionData
					.builder().setMean(mean)//
					.setStandardDeviation(standardDeviation)//
					.build();

			int expectedHashCode = trucactedNormalSampleDistributionData.hashCode();

			for (int j = 0; j < 10; j++) {
				assertEquals(expectedHashCode, trucactedNormalSampleDistributionData.hashCode());
			}

		}
		// hash codes are reasonably distributed
		Set<Integer> hashCodes = new LinkedHashSet<>();
		for (int i = 0; i < 100; i++) {
			double mean = randomGenerator.nextDouble() * 100 - 50;
			double standardDeviation = randomGenerator.nextDouble() * 10;

			TruncatedNormalSampleDistributionData trucactedNormalSampleDistributionData = TruncatedNormalSampleDistributionData
					.builder().setMean(mean)//
					.setStandardDeviation(standardDeviation)//
					.build();

			hashCodes.add(trucactedNormalSampleDistributionData.hashCode());
		}

		assertEquals(100, hashCodes.size());
	}

	@Test
	@UnitTestMethod(target = TruncatedNormalSampleDistributionData.class, name = "toString", args = {})
	public void testToString() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(762503665576823288L);

		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble() * 100 - 50;
			double standardDeviation = randomGenerator.nextDouble() * 10;

			TruncatedNormalSampleDistributionData trucactedNormalSampleDistributionData = TruncatedNormalSampleDistributionData
					.builder().setMean(mean)//
					.setStandardDeviation(standardDeviation)//
					.build();

			String actual = trucactedNormalSampleDistributionData.toString();
			String expected = "TruncatedNormalSampleDistributionData [data=Data [mean=" + mean + ", standardDeviation="
					+ standardDeviation + "]]";
			assertEquals(expected, actual);
		}
	}

	private TruncatedNormalSampleDistributionData getRandomTruncatedNormalSampleDistributionData(long seed) {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(seed);
		return TruncatedNormalSampleDistributionData.builder().setMean(randomGenerator.nextDouble())
				.setStandardDeviation(randomGenerator.nextDouble()).build();
	}

	@Test
	@UnitTestMethod(target = TruncatedNormalSampleDistributionData.class, name = "getSampleDistribution", args = {
			RandomGenerator.class })
	public void testGetSampleDistribution() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(1977510801995866553L);
		/*
		 * If the SampleDistribution returned from getSampleDistribution will in turn
		 * return the data used to create it, we can assume that the generated sample
		 * distribution was corrected.
		 */
		for (int i = 0; i < 30; i++) {
			TruncatedNormalSampleDistributionData truncatedNormalSampleDistributionData1 = getRandomTruncatedNormalSampleDistributionData(
					randomGenerator.nextLong());
			assertNotNull(truncatedNormalSampleDistributionData1);
			TruncatedNormalSampleDistribution truncatedNormalSampleDistribution = truncatedNormalSampleDistributionData1
					.getSampleDistribution(randomGenerator);
			assertNotNull(truncatedNormalSampleDistribution);
			TruncatedNormalSampleDistributionData truncatedNormalSampleDistributionData2 = truncatedNormalSampleDistribution
					.getSampleDistributionData();
			assertNotNull(truncatedNormalSampleDistributionData2);
			assertEquals(truncatedNormalSampleDistributionData1, truncatedNormalSampleDistributionData2);
		}
	}

}

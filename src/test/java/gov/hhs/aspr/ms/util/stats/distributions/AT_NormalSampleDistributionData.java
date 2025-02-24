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

public class AT_NormalSampleDistributionData {

	@Test
	@UnitTestMethod(target = NormalSampleDistributionData.Builder.class, name = "build", args = {})
	public void testBuild() {
		// nothing to test -- all errors are caught in the mutation methods
	}

	@Test
	@UnitTestMethod(target = NormalSampleDistributionData.Builder.class, name = "setMean", args = { double.class })
	public void testSetMean() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(2502688629848284439L);

		assertEquals(0.0, NormalSampleDistributionData.builder().build().getMean());

		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble() * 100 - 50;
			double standardDeviation = randomGenerator.nextDouble() * 10;
			NormalSampleDistributionData normalSampleDistributionData = NormalSampleDistributionData.builder()
					.setMean(mean).setStandardDeviation(standardDeviation).build();
			assertEquals(mean, normalSampleDistributionData.getMean());
		}

	}

	@Test
	@UnitTestMethod(target = NormalSampleDistributionData.Builder.class, name = "setStandardDeviation", args = {
			double.class })
	public void testSetStandardDeviation() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(1570219788202151800L);
		assertEquals(1.0, NormalSampleDistributionData.builder().build().getStandardDeviation());

		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble() * 100 - 50;
			double standardDeviation = randomGenerator.nextDouble() * 10;
			NormalSampleDistributionData normalSampleDistributionData = NormalSampleDistributionData.builder()
					.setMean(mean).setStandardDeviation(standardDeviation).build();
			assertEquals(standardDeviation, normalSampleDistributionData.getStandardDeviation());
		}
	}

	@Test
	@UnitTestMethod(target = NormalSampleDistributionData.class, name = "builder", args = {})
	public void testBuilder() {
		assertNotNull(NormalSampleDistributionData.builder());
	}

	@Test
	@UnitTestMethod(target = NormalSampleDistributionData.class, name = "equals", args = { Object.class })
	public void testEquals() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(8002651869627697878L);

		// never equal null
		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble() * 100 - 50;
			double standardDeviation = randomGenerator.nextDouble() * 10;

			NormalSampleDistributionData normalSampleDistributionData = NormalSampleDistributionData.builder()
					.setMean(mean)//
					.setStandardDeviation(standardDeviation)//
					.build();
			assertFalse(normalSampleDistributionData.equals(null));
		}

		// not equal to other things
		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble() * 100 - 50;
			double standardDeviation = randomGenerator.nextDouble() * 10;

			NormalSampleDistributionData normalSampleDistributionData = NormalSampleDistributionData.builder()
					.setMean(mean)//
					.setStandardDeviation(standardDeviation)//
					.build();
			assertFalse(normalSampleDistributionData.equals(new Object()));
		}

		// reflexive
		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble() * 100 - 50;
			double standardDeviation = randomGenerator.nextDouble() * 10;
			NormalSampleDistributionData normalSampleDistributionData = NormalSampleDistributionData.builder()
					.setMean(mean)//
					.setStandardDeviation(standardDeviation)//
					.build();
			assertTrue(normalSampleDistributionData.equals(normalSampleDistributionData));
		}

		// symmetric, transitive, consistent
		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble() * 100 - 50;
			double standardDeviation = randomGenerator.nextDouble() * 10;

			NormalSampleDistributionData normalSampleDistributionData1 = NormalSampleDistributionData.builder()
					.setMean(mean)//
					.setStandardDeviation(standardDeviation)//
					.build();

			NormalSampleDistributionData normalSampleDistributionData2 = NormalSampleDistributionData.builder()
					.setMean(mean)//
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
	@UnitTestMethod(target = NormalSampleDistributionData.class, name = "getCloneBuilder", args = {})
	public void testGetCloneBuilder() {
		NormalSampleDistributionData original = NormalSampleDistributionData.builder().setMean(6)
				.setStandardDeviation(7).build();

		NormalSampleDistributionData duplicate = NormalSampleDistributionData.builder().setMean(6)
				.setStandardDeviation(7).build();
		assertEquals(duplicate, original);

		// if no changes are made, the cloned version is equal to the duplicate
		NormalSampleDistributionData cloned = original.getCloneBuilder().build();
		assertEquals(duplicate, cloned);

		// if the mean value is changed the original is unaffected
		original.getCloneBuilder().setMean(5).build();
		assertEquals(duplicate, original);

		// if the standardDeviation value is changed the original is unaffected
		original.getCloneBuilder().setStandardDeviation(10).build();
		assertEquals(duplicate, original);

	}

	@Test
	@UnitTestMethod(target = NormalSampleDistributionData.class, name = "getMean", args = {})
	public void testGetMean() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(8707148797311985140L);

		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble() * 100 - 50;
			double standardDeviation = randomGenerator.nextDouble() * 10;
			NormalSampleDistributionData normalSampleDistributionData = NormalSampleDistributionData.builder()
					.setMean(mean).setStandardDeviation(standardDeviation).build();
			assertEquals(mean, normalSampleDistributionData.getMean());
		}
	}

	@Test
	@UnitTestMethod(target = NormalSampleDistributionData.class, name = "getStandardDeviation", args = {})
	public void testGetStandardDeviation() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(2226337999633679132L);

		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble() * 100 - 50;
			double standardDeviation = randomGenerator.nextDouble() * 10;
			NormalSampleDistributionData normalSampleDistributionData = NormalSampleDistributionData.builder()
					.setMean(mean).setStandardDeviation(standardDeviation).build();
			assertEquals(standardDeviation, normalSampleDistributionData.getStandardDeviation());
		}
	}

	@Test
	@UnitTestMethod(target = NormalSampleDistributionData.class, name = "hashCode", args = {})
	public void testHashCode() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(2678948420814252720L);

		// equal object have equal hash codes
		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble() * 100 - 50;
			double standardDeviation = randomGenerator.nextDouble() * 10;

			NormalSampleDistributionData normalSampleDistributionData1 = NormalSampleDistributionData.builder()
					.setMean(mean)//
					.setStandardDeviation(standardDeviation)//
					.build();

			NormalSampleDistributionData normalSampleDistributionData2 = NormalSampleDistributionData.builder()
					.setMean(mean)//
					.setStandardDeviation(standardDeviation)//
					.build();

			assertEquals(normalSampleDistributionData1, normalSampleDistributionData2);

			assertEquals(normalSampleDistributionData1.hashCode(), normalSampleDistributionData2.hashCode());

		}

		// hash codes are stable
		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble() * 100 - 50;
			double standardDeviation = randomGenerator.nextDouble() * 10;

			NormalSampleDistributionData normalSampleDistributionData = NormalSampleDistributionData.builder()
					.setMean(mean)//
					.setStandardDeviation(standardDeviation)//
					.build();

			int expectedHashCode = normalSampleDistributionData.hashCode();

			for (int j = 0; j < 10; j++) {
				assertEquals(expectedHashCode, normalSampleDistributionData.hashCode());
			}

		}
		// hash codes are reasonably distributed
		Set<Integer> hashCodes = new LinkedHashSet<>();
		for (int i = 0; i < 100; i++) {
			double mean = randomGenerator.nextDouble() * 100 - 50;
			double standardDeviation = randomGenerator.nextDouble() * 10;

			NormalSampleDistributionData normalSampleDistributionData = NormalSampleDistributionData.builder()
					.setMean(mean)//
					.setStandardDeviation(standardDeviation)//
					.build();

			hashCodes.add(normalSampleDistributionData.hashCode());
		}

		assertEquals(100, hashCodes.size());
	}

	@Test
	@UnitTestMethod(target = NormalSampleDistributionData.class, name = "toString", args = {})
	public void testToString() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(762503665576823288L);

		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble() * 100 - 50;
			double standardDeviation = randomGenerator.nextDouble() * 10;

			NormalSampleDistributionData normalSampleDistributionData = NormalSampleDistributionData.builder()
					.setMean(mean)//
					.setStandardDeviation(standardDeviation)//
					.build();

			String actual = normalSampleDistributionData.toString();
			String expected = "NormalSampleDistributionData [data=Data [mean=" + mean + ", standardDeviation="
					+ standardDeviation + "]]";
			assertEquals(expected, actual);
		}
	}

	private NormalSampleDistributionData getRandomNormalSampleDistributionData(long seed) {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(seed);
		return NormalSampleDistributionData.builder().setMean(randomGenerator.nextDouble())
				.setStandardDeviation(randomGenerator.nextDouble()).build();
	}

	@Test
	@UnitTestMethod(target = NormalSampleDistributionData.class, name = "getSampleDistribution", args = {
			RandomGenerator.class })
	public void testGetSampleDistribution() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(2361031167068109986L);
		/*
		 * If the SampleDistribution returned from getSampleDistribution will in turn
		 * return the data used to create it, we can assume that the generated sample
		 * distribution was corrected.
		 */
		for (int i = 0; i < 30; i++) {
			NormalSampleDistributionData normalSampleDistributionData1 = getRandomNormalSampleDistributionData(
					randomGenerator.nextLong());
			assertNotNull(normalSampleDistributionData1);
			NormalSampleDistribution normalSampleDistribution = normalSampleDistributionData1
					.getSampleDistribution(randomGenerator);
			assertNotNull(normalSampleDistribution);
			NormalSampleDistributionData normalSampleDistributionData2 = normalSampleDistribution
					.getSampleDistributionData();
			assertNotNull(normalSampleDistributionData2);
			assertEquals(normalSampleDistributionData1, normalSampleDistributionData2);
		}
	}

}
